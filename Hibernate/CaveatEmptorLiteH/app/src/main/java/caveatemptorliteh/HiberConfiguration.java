package caveatemptorliteh;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.sql.DataSource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.hibernate.cfg.AvailableSettings;

import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.postgresql.ds.PGSimpleDataSource;

public class HiberConfiguration {
    private static final String DB_DRIVER = "org.postgresql.jdbc.Driver";
    private static final String DB_ADDRESS = "127.0.0.1";
    private static final int DB_PORT = 5432;
    private static final String DB_NAME = "Test";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "";

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static List<Class> entities = new ArrayList<Class>() {
    };

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void shutdown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    public static void build() {
        entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(archiverPersistenceUnitInfo(), new HashMap<String, Object>() {
                    {
                        put(AvailableSettings.DRIVER, DB_DRIVER);
                        put(AvailableSettings.URL, "jdbc:postgresql://" + DB_ADDRESS + ":" + DB_PORT + "/" + DB_NAME);

                        put(AvailableSettings.DIALECT, PostgresPlusDialect.class);
                        put(AvailableSettings.SHOW_SQL, true);
                        put(AvailableSettings.QUERY_STARTUP_CHECKING, false);

                        put(AvailableSettings.USER, DB_USER);
                        put(AvailableSettings.PASS, DB_PASS);

                        put(AvailableSettings.GENERATE_STATISTICS, false);
                        put(AvailableSettings.USE_REFLECTION_OPTIMIZER, false);
                        put(AvailableSettings.USE_SECOND_LEVEL_CACHE, false);
                        put(AvailableSettings.USE_QUERY_CACHE, false);
                        put(AvailableSettings.USE_STRUCTURED_CACHE, false);
                        put(AvailableSettings.STATEMENT_BATCH_SIZE, 20);

                        put(AvailableSettings.HBM2DDL_AUTO, "create-drop");
                    }
                });

        entityManager = entityManagerFactory.createEntityManager();
    }

    private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return "CaveatEmptor";
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass().getClassLoader().getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return entityClassNames();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return hibernateProperties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }

    private static Properties hibernateProperties() {
        final Properties properties = new Properties();

        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.connection.driver_class", DB_DRIVER);
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        properties.put("hibernate.connection.datasource", dataSource());

        return properties;
    }

    private static DataSource dataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setUser(DB_USER);
        dataSource.setPassword(DB_PASS);

        dataSource.setServerNames(new String[] { DB_ADDRESS });
        dataSource.setPortNumbers(new int[] { DB_PORT });

        return dataSource;
    }

    private static List<String> entityClassNames() {
        return entities.stream().map(Class::getName).collect(Collectors.toList());
    }

    public static void addEntity(Class entityClass) {
        entities.add(entityClass);
    }
}
