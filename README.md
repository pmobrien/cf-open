# Vultus Liftoff Scoreboard

A small project to host a scoreboard for an in-house competition (mirroring the the [CrossFit Liftoff](https://games.crossfit.com/liftoff)). Participants did not want their weights displayed publicly, so this scoreboard provides a way to even out the scoring based on weight without exposing that statistic to other participants. An athlete's final score is their [Sinclair total](https://en.wikipedia.org/wiki/Sinclair_Coefficients) plus their metcon score.

Requires [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Maven](https://maven.apache.org/download.cgi).

To get up and running in one step:
```
mvn clean install && java -Dhttp-port=15000 -jar target/vultus-liftoff.jar
```

This will spin up an embedded Jetty server, hosting a simple page on `localhost:15000`. Data is stored in an embedded [Neo4j](https://neo4j.com/) database. The unit tests populate the table with a few entries that will show up when you open the page.


## Available system properties include:

- `http-port`: The port to host the web page on. Defaults to 80.
- `https-port`: The secure port to host the web page on. Defaults to 443. Requires `key-store-path` and `key-store-password` to be set. If this property is not set, the page will not be hosted on a secure port.
- `key-store-path`: The path to a certificate you want to use for https.
- `key-store-password`: The password for the certificate you want to use for https.
- `neo-store`: The path to the the Neo4j database. This can be pointed either at an **absolute** file system path for embedded mode or at an actual Neo4j server using a Bolt URI (e.g. `bolt://localhost7687`). Defaults to `target/neo-store` (note that this will be wiped out on a `mvn clean install`.

Rather than setting properties individually with `-D` parameters, properties can be added to a file in key/value pairs (e.g. `http-port=15000`), and the file can be loaded by specifying `-Dprop-file=<propfile_location>.properties`. The `<propfile_location>` can be relative.


### Jetty
Jetty is configured to run in embedded mode with very minimal configuration. JavaScript, HTML, and CSS files hosted by the Jetty server can be found in `src/main/resources/com/pmobrien/vultus/liftoff/webapp`.

### Neo4j
Neo4j configured to be able to run in either embedded mode or connected to an actual server via Bolt (see system properties above). The [Neo4j-OGM](https://github.com/neo4j/neo4j-ogm) library is used as a simple way to do CRUD operations on entities.

#### Data Model
For now, there is only a single entity being stored in the graph (`Athlete`). For this reason it probably doesn't make a lot of sense to use a graph database, but I already had infrastructure built out around Neo4j so at the time it made the most sense. However, this should give enough flexibility to easily refactor and reuse this project for future competitions, while maintaining past competition results.
