package flyway.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class ApplicationConfigReader {
    private static final String PROPERTY_NAME_DB_TYPE = "db-type";
    private static final String PROPERTY_NAME_DB_DRIVER = "db-driver";
    private static final String PROPERTY_NAME_DB_ADDRESS = "db-address";
    private static final String PROPERTY_NAME_DB_PORT = "db-port";
    private static final String PROPERTY_NAME_DB_NAME = "db-name";
    private static final String PROPERTY_NAME_DB_USER = "db-user";
    private static final String PROPERTY_NAME_DB_PASS = "db-password";
    private static final String PROPERTY_NAME_DB_USING_TYPE = "db-using-type";

    private static final String CONFIG_FILENAME = "settings.conf";

    private static final String CONFIG_PARENT_FOLDER_NAME = "user.dir";

    private static final String SEARCH_EXCEPTION_PREFIX = "No config file: ";
    private static final String PARSE_EXCEPTION_PREFIX = "Param ";
    private static final String PARSE_EXCEPTION_POSTFIX = " not parsed";

    private String dbType;
    private String dbDriver;
    private String dbAddress;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;
    private String dbUsingType;

    private String[] templateExclusionPaths;

    private Path pathToConfig;

    private static ApplicationConfigReader lastConfig;

    public ApplicationConfigReader() throws IOException, FileNotFoundException {
        onReaderCreate(System.getProperty(CONFIG_PARENT_FOLDER_NAME), CONFIG_FILENAME);
    }

    public ApplicationConfigReader(String pathToFile, String filename) throws IOException, FileNotFoundException {
        onReaderCreate(pathToFile, filename);
    }

    private void onReaderCreate(String pathToFile, String filename) throws IOException, FileNotFoundException {
        pathToConfig = Path.of(Path.of(pathToFile, filename).toFile().getAbsolutePath());

        if (!pathToConfig.toFile().exists()) {
            throw new FileNotFoundException(SEARCH_EXCEPTION_PREFIX + pathToConfig.toString());
        }

        FileInputStream configFileInputStream = new FileInputStream(pathToConfig.toString());
        Properties prop = new Properties();
        prop.load(configFileInputStream);

        dbType = getProperty(prop, PROPERTY_NAME_DB_TYPE);
        dbDriver = getProperty(prop, PROPERTY_NAME_DB_DRIVER);
        dbAddress = getProperty(prop, PROPERTY_NAME_DB_ADDRESS);
        dbPort = getProperty(prop, PROPERTY_NAME_DB_PORT);
        dbName = getProperty(prop, PROPERTY_NAME_DB_NAME);
        dbUser = getProperty(prop, PROPERTY_NAME_DB_USER);
        dbPassword = getProperty(prop, PROPERTY_NAME_DB_PASS);
        dbUsingType = getProperty(prop, PROPERTY_NAME_DB_USING_TYPE);

        lastConfig = this;
    }

    private String getProperty(Properties prop, String propertyName) throws IOException {
        if (prop.getProperty(propertyName) == null) {
            throw new IOException(PARSE_EXCEPTION_PREFIX + propertyName + PARSE_EXCEPTION_POSTFIX);
        }

        return prop.getProperty(propertyName);
    }

    public String getDbType() {
        return dbType;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbAddress() {
        return dbAddress;
    }

    public String getDbPort() {
        return dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbUsingType() {
        return dbUsingType;
    }

    public String[] getTemplateExclusionPaths() {
        return templateExclusionPaths;
    }

    public static ApplicationConfigReader getLastConfig() {
        return lastConfig;
    }
}
