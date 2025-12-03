package taco.cloud.support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.mockito.Mockito;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.context.DriverContext;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;

import taco.cloud.mocks.EmbeddedCassandraServerHelperMock;
import taco.cloud.mocks.IntegerCodecMock;

public class DataConfig {
    private static final String CASSANDRA_CONFIG_FILE_PATH = "/database/cassandra/cassandra.yaml";
    private final static String APP_PARENT_FOLDER_NAME = "user.dir";
    private final static String EMBEDDED_CASSANDRA_FOLDER_NAME = "target";
    private static final Long CASSANDRA_TIMEOUT = 10L;
    private static final Long CASSANDRA_WAIT_DELAY = 1000L;
    private static String host = "";
    private static String port = "";

    private DataConfig() {
    }

    public static void waitDelay() {
        try {
            Thread.sleep(CASSANDRA_WAIT_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean cassandraIsStarted() {
        // CqlSession session = EmbeddedCassandraServerHelperMock.getSession();
        CqlSession session = Mockito.mock(EmbeddedCassandraServerHelperMock.class).getSession(); // Work without
                                                                                                 // cassandra

        DriverContext driverContext = session.getContext();
        MutableCodecRegistry codecRegistry = (MutableCodecRegistry) driverContext.getCodecRegistry();
        codecRegistry.register(new IntegerCodecMock());

        ResultSet result = session.execute("SELECT now() FROM system.local");

        // Row row = result.one();
        Row row = Mockito.mock(Row.class); // Work without cassandra
        Mockito.when(row.getInt(0)).thenReturn(1); // Work without cassandra

        int time = row.getInt(0);

        return time != -1;
    }

    public static void printCassandraInfo() {
        if (host == null || port == null) {
            return;
        }

        System.out.println("Cassandra host: " + host);
        System.out.println("Cassandra port: " + port);
    }

    public static synchronized void startCassandraThread() {
        stopCassandraThread();

        Runnable cassandraThreadBody = () -> {
            try {
                URI configurationFileUri = DataConfig.class.getResource(CASSANDRA_CONFIG_FILE_PATH).toURI();
                File configuration = new File(configurationFileUri);

                // clearCassandraFolder();

                EmbeddedCassandraServerHelperMock.startEmbeddedCassandra(configuration, CASSANDRA_TIMEOUT);
            } catch (ConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        };

        Thread cassandraThread = new Thread(cassandraThreadBody);

        cassandraThread.start();

        waitDelay();

        host = EmbeddedCassandraServerHelperMock.getHost();
        port = Integer.toString(EmbeddedCassandraServerHelperMock.getNativeTransportPort());

        waitDelay();
    }

    public static synchronized void stopCassandraThread() {
        EmbeddedCassandraServerHelperMock.stopEmbeddedCassandra();
    }

    private static void clearCassandraFolder() throws FileNotFoundException {
        String appParentFolder = System.getProperty(APP_PARENT_FOLDER_NAME);

        Path pathToCassandra = Path
                .of(Path.of(appParentFolder, EMBEDDED_CASSANDRA_FOLDER_NAME).toFile().getAbsolutePath());

        deleteDirectory(pathToCassandra);

        if (pathToCassandra.toFile().exists()) {
            throw new FileNotFoundException(pathToCassandra.toString());
        }

    }

    private static void deleteDirectory(Path path) {
        try (Stream<Path> stream = Files.walk(path)) {
            stream.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
