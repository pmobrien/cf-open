package com.pmobrien.cfopen;

import com.google.common.base.Strings;
import com.pmobrien.cfopen.filters.RequestLoggerFilter;
import com.pmobrien.cfopen.mappers.DefaultObjectMapper;
import com.pmobrien.cfopen.mappers.UncaughtExceptionMapper;
import com.pmobrien.cfopen.neo.NeoConnector;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import org.eclipse.jetty.server.ForwardedRequestCustomizer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Application {
  
  private static final String WEBAPP_RESOURCE_PATH = "/com/pmobrien/vultus/liftoff/webapp";
  private static final String INDEX_HTML_PATH = String.format("%s/index.html", WEBAPP_RESOURCE_PATH);

  public static void main(String[] args) {
    try {
      if(!Strings.isNullOrEmpty(System.getProperty(Properties.PROP_FILE))) {
        java.util.Properties properties = new java.util.Properties();
        properties.load(Files.newInputStream(new File(System.getProperty(Properties.PROP_FILE)).toPath()));
        
        System.getProperties().putAll(properties);
      }
      
      if(Strings.isNullOrEmpty(System.getProperty(NeoConnector.NEO_STORE))) {
        System.setProperty(
            NeoConnector.NEO_STORE,
            Paths.get(Paths.get("").toAbsolutePath().toString(), "target", "neo-store").toString()
        );
      }
      
      new Application().run(new Server());
    } catch(IOException ex) {
      ex.printStackTrace(System.out);
    } catch(Throwable t) {
      t.printStackTrace(System.out);
    }
  }
  
  private static int httpPort() {
    return Integer.parseInt(
        Optional.ofNullable(System.getProperty(Properties.HTTP_PORT)).orElse(Properties.DEFAULT_HTTP_PORT)
    );
  }
  
  private static boolean useHttps() {
    return !Strings.isNullOrEmpty(System.getProperty(Properties.HTTPS_PORT));
  }
  
  private static int httpsPort() {
    return Integer.parseInt(
        Optional.ofNullable(System.getProperty(Properties.HTTPS_PORT)).orElse(Properties.DEFAULT_HTTPS_PORT)
    );
  }
  
  private void run(Server server) {
    try {
      server.setHandler(configureHandlers());
      server.addConnector(configureConnector(server));
      
      server.start();
      server.join();
    } catch(Exception ex) {
      throw new RuntimeException(ex);
    } finally {
      server.destroy();
    }
  }
  
  private ServerConnector configureConnector(Server server) {
    HttpConfiguration config = new HttpConfiguration();
    config.addCustomizer(new SecureRequestCustomizer());
    config.addCustomizer(new ForwardedRequestCustomizer());
    
    HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(config);
    ServerConnector httpConnector = new ServerConnector(server, httpConnectionFactory);
    httpConnector.setPort(httpPort());
    server.addConnector(httpConnector);
    
    if(useHttps()) {
      if(Strings.isNullOrEmpty(System.getProperty(Properties.KEY_STORE_PATH))) {
        throw new RuntimeException(
            String.format("'%s' property must be set to use https.", Properties.KEY_STORE_PATH)
        );
      }

      if(Strings.isNullOrEmpty(System.getProperty(Properties.KEY_STORE_PASSWORD))) {
        throw new RuntimeException(
            String.format("'%s' property must be set to use https.", Properties.KEY_STORE_PASSWORD)
        );
      }

      SslContextFactory sslContextFactory = new SslContextFactory();
      sslContextFactory.setKeyStoreType("PKCS12");
      sslContextFactory.setKeyStorePath(System.getProperty(Properties.KEY_STORE_PATH));
      sslContextFactory.setKeyStorePassword(System.getProperty(Properties.KEY_STORE_PASSWORD));
      sslContextFactory.setKeyManagerPassword(System.getProperty(Properties.KEY_STORE_PASSWORD));
      
      ServerConnector connector = new ServerConnector(server, sslContextFactory, httpConnectionFactory);
      connector.setPort(httpsPort());

      return connector;
    } else {
      return new ServerConnector(server, httpConnectionFactory);
    }
  }
  
  private HandlerList configureHandlers() throws MalformedURLException, URISyntaxException {
    HandlerList handlers = new HandlerList();
    handlers.setHandlers(
        new Handler[] {
          configureApiHandler(),
          configureStaticHandler()
        }
    );
    
    return handlers;
  }
  
  private ServletContextHandler configureApiHandler() {
    ServletContextHandler handler = new ServletContextHandler();
    handler.setContextPath("/api");
    
    handler.addServlet(
        new ServletHolder(
            new ServletContainer(
                new ResourceConfig()
                    .register(DefaultObjectMapper.class)
                    .register(RequestLoggerFilter.class)
                    .register(UncaughtExceptionMapper.class)
            )
        ),
        "/*"
    );
    
    return handler;
  }
  
  private ServletContextHandler configureStaticHandler() throws MalformedURLException, URISyntaxException {
    ServletContextHandler handler = new ServletContextHandler();
    handler.setContextPath("/");
    
    handler.setBaseResource(
        Resource.newResource(
            URI.create(
                this.getClass().getResource(INDEX_HTML_PATH)
                    .toURI()
                    .toASCIIString()
                    .replaceFirst("/index.html$", "/")
            )
        )
    );
    
    handler.setWelcomeFiles(new String[] { "index.html" });
    handler.addServlet(DefaultServlet.class, "/");
    
    return handler;
  }
  
  public static class Properties {
    
    private static final String PROP_FILE = "prop-file";
  
    private static final String HTTP_PORT = "http-port";
    private static final String HTTPS_PORT = "https-port";
    private static final String DEFAULT_HTTP_PORT = "80";
    private static final String DEFAULT_HTTPS_PORT = "443";

    private static final String KEY_STORE_PATH = "key-store-path";
    private static final String KEY_STORE_PASSWORD = "key-store-password";
  }
}
