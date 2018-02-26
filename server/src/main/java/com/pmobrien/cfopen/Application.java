package com.pmobrien.cfopen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.pmobrien.cfopen.crossfitdotcom.CrossFitDotComRequester;
import com.pmobrien.cfopen.filters.RequestLoggerFilter;
import com.pmobrien.cfopen.mappers.DefaultObjectMapper;
import com.pmobrien.cfopen.mappers.UncaughtExceptionMapper;
import com.pmobrien.cfopen.neo.accessors.AthleteAccessor;
import com.pmobrien.cfopen.neo.accessors.TeamAccessor;
import com.pmobrien.cfopen.neo.pojo.Athlete;
import com.pmobrien.cfopen.neo.pojo.Team;
import com.pmobrien.cfopen.webservices.impl.AthletesWebService;
import com.pmobrien.cfopen.webservices.impl.TeamsWebService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
  
  private static final String PROPERTIES_FILE = "propertiesFile";
  private static final String WEBAPP_RESOURCE_PATH = "/com/pmobrien/vultus/liftoff/webapp";
  private static final String INDEX_HTML_PATH = String.format("%s/index.html", WEBAPP_RESOURCE_PATH);
  
  private static ApplicationProperties properties;

  public static void main(String[] args) {
    try {
      new Application().run(new Server());
    } catch(IOException ex) {
      ex.printStackTrace(System.out);
    } catch(Throwable t) {
      t.printStackTrace(System.out);
    }
  }
  
  public Application() throws IOException {
    properties = readApplicationProperties();
    
    List<Team> teams = new TeamAccessor().getAllTeams();
    if(teams.size() < properties.getData().getTeams()) {
      for(int i = teams.size(); i < properties.getData().getTeams(); ++i) {
        new TeamAccessor().createOrUpdateTeam(
            new Team()
                .setOrdinal(i)
                .setName(String.format("Team %s", i))
        );
      }
    }
    
    int counter = 0;
    teams = new TeamAccessor().getAllTeams();
    
    for(Athlete athlete : new CrossFitDotComRequester().getAllAthletes()) {
      athlete.setTeam(teams.get(counter++ % 4));
      
      new AthleteAccessor().createOrUpdateAthlete(athlete);
    }
  }
  
  private static ApplicationProperties readApplicationProperties() throws IOException {
    if(Strings.isNullOrEmpty(System.getProperty(PROPERTIES_FILE))) {
      throw new RuntimeException(String.format("%s is required", PROPERTIES_FILE));
    }
    
    File props = new File(System.getProperty(PROPERTIES_FILE));
    
    if(!props.exists()) {
      throw new RuntimeException(String.format("%s %s does not exist!", PROPERTIES_FILE, props.getPath()));
    }
    
    return new ObjectMapper().readValue(props, ApplicationProperties.class);
  }
  
  public static ApplicationProperties getProperties() {
    return properties;
  }
  
  private static int httpPort() {
    return properties.getConfiguration().getHttp().getPort();
  }
  
  private static boolean useHttps() {
    return properties.getConfiguration().getHttps().isEnabled();
  }
  
  private static int httpsPort() {
    return properties.getConfiguration().getHttps().getPort();
  }
  
  private void run(Server server) {
    try {
      server.setHandler(configureHandlers());
      server.addConnector(configureConnector(server));
      
      server.start();
      server.join();
    } catch(Exception ex) {
      ex.printStackTrace(System.out);
      
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
      if(Strings.isNullOrEmpty(properties.getConfiguration().getHttps().getKeyStorePath())) {
        throw new RuntimeException(
            String.format(
                "'%s' property must be set to use https.",
                properties.getConfiguration().getHttps().getKeyStorePath()
            )
        );
      }

      if(Strings.isNullOrEmpty(properties.getConfiguration().getHttps().getKeyStorePassword())) {
        throw new RuntimeException(
            String.format(
                "'%s' property must be set to use https.",
                properties.getConfiguration().getHttps().getKeyStorePassword()
            )
        );
      }

      SslContextFactory sslContextFactory = new SslContextFactory();
      sslContextFactory.setKeyStoreType("PKCS12");
      sslContextFactory.setKeyStorePath(properties.getConfiguration().getHttps().getKeyStorePath());
      sslContextFactory.setKeyStorePassword(properties.getConfiguration().getHttps().getKeyStorePassword());
      sslContextFactory.setKeyManagerPassword(properties.getConfiguration().getHttps().getKeyStorePassword());
      
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
                    .register(AthletesWebService.class)
                    .register(TeamsWebService.class)
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
}
