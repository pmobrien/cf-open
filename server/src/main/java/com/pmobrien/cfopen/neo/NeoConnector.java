package com.pmobrien.cfopen.neo;

import com.google.common.base.Suppliers;
import java.io.File;
import java.nio.file.Paths;
import java.util.function.Supplier;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.configuration.BoltConnector;
import org.neo4j.kernel.configuration.Connector.ConnectorType;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.testng.util.Strings;

public class NeoConnector {
  
  private static final String POJO_PACKAGE = "com.pmobrien.cfopen.neo.pojo";
  
  private static final NeoConnector INSTANCE = new NeoConnector();
  private static final Supplier<SessionFactory> SESSION_FACTORY = Suppliers.memoize(() -> initializeSessionFactory());
  
  private NeoConnector() {}
  
  public static NeoConnector getInstance() {
    return INSTANCE;
  }
  
  protected Session newSession() {
    return SESSION_FACTORY.get().openSession();
  }
  
  private static SessionFactory initializeSessionFactory() {
    BoltConnector bolt = new BoltConnector("0");
    
    return new SessionFactory(
        new EmbeddedDriver(
            new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File(uri()))
                .setConfig(bolt.type, ConnectorType.BOLT.name())
                .setConfig(bolt.enabled, "true")
                .setConfig(bolt.listen_address, "0.0.0.0:17666")
                .setConfig(bolt.advertised_address, "0.0.0.0:17666")
                .setConfig(bolt.encryption_level, BoltConnector.EncryptionLevel.DISABLED.name())
                .newGraphDatabase()
        ),
        POJO_PACKAGE
    );
  }

  private static String uri() {
    if(Strings.isNotNullAndNotEmpty(System.getProperty("neo-store"))) {
      return Paths.get(Paths.get("").toAbsolutePath().toString(), "target", "neo-store").toString();
    }
    
    return System.getProperty("neo-store");
  }
}
