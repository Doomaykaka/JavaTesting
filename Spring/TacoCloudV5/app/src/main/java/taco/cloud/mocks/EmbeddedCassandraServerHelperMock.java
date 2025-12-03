package taco.cloud.mocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.yaml.snakeyaml.Yaml;

import com.datastax.oss.driver.api.core.CqlSession;

public class EmbeddedCassandraServerHelperMock extends EmbeddedCassandraServerHelper {
    private static boolean embeddedCassandraIsStarted = false;
    private static Map<String, Object> config = new HashMap<String, Object>();

    public static void startEmbeddedCassandra(File configuration, long timeout) throws IOException {
        embeddedCassandraIsStarted = true;

        if (configuration == null || !configuration.exists()) {
            throw new IOException("Bad configuration path");
        }

        try {
            readYamlConfiguration(configuration);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    private static void readYamlConfiguration(File configuration) throws Exception {
        Yaml configYaml = new Yaml();

        try (FileInputStream inputStream = new FileInputStream(configuration)) {
            config = (Map<String, Object>) configYaml.load(inputStream);
        } catch (Exception e) {
            throw new Exception("Bad configuration file");
        }
    }

    public static CqlSession getSession() {
        if (!embeddedCassandraIsStarted) {
            return null;
        }

        return new CqlSessionMock();
    }

    public static void stopEmbeddedCassandra() {
        embeddedCassandraIsStarted = false;
    }

    public static String getHost() {
        if (!embeddedCassandraIsStarted) {
            return null;
        }

        return (String) config.get("listen_address");
    }

    public static int getNativeTransportPort() {
        if (!embeddedCassandraIsStarted) {
            return -1;
        }

        return (int) config.get("native_transport_port");
    }
}
