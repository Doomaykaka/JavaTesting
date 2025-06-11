package flyway;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ServiceConfigurationError;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.api.output.InfoOutput;
import org.flywaydb.core.api.output.MigrateResult;

import flyway.configuration.ApplicationConfigReader;
import flyway.configuration.HiberConfiguration;
import flyway.dao.TestDAO;
import flyway.models.Test;

public class App {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ApplicationConfigReader configReader = new ApplicationConfigReader();

        TestDAO testDAO = prepareDAO(configReader);

        testFlyway(testDAO, configReader);
    }

    private static TestDAO prepareDAO(ApplicationConfigReader configReader) throws FileNotFoundException, IOException {
        TestDAO testDAO = null;

        HiberConfiguration.addEntity(Test.class);
        HiberConfiguration.build(configReader);

        testDAO = new TestDAO(HiberConfiguration.getEntityManagerFactory());

        return testDAO;
    }

    private static void testFlyway(TestDAO testDAO, ApplicationConfigReader configReader) {
        // Init

        FluentConfiguration flywayConfig = Flyway.configure();
        flywayConfig.dataSource(HiberConfiguration.buildDatabaseUrl(configReader), configReader.getDbUser(),
                configReader.getDbPassword());
        String location = "filesystem:" + System.getProperty("user.dir") + "/db/migration";
        flywayConfig.locations(location);

        Flyway flyway = new Flyway(flywayConfig);

        // Migrate

        MigrateResult migrations = flyway.migrate();
        migrations.getSuccessfulMigrations();

        // Info

        MigrationInfoService service = flyway.info();

        System.out.println("Migrations info:");

        for (InfoOutput info : service.getInfoResult().migrations) {
            System.out.println(info.getDescription() + " " + info.getVersion());
        }

        // Validate (check migration scripts data with schema)

        try {
            flyway.validate();
        } catch (FlywayException e) {
            e.printStackTrace();
        }

        // Baseline (mark current version as baseline, need remove history)

        // flyway.baseline();

        // Clean database

        // flyway.clean();

        // Repair database (afterMigrateError.sql in migrations folder to remove
        // history records, use sql DELETE IGNORE FROM flyway_schema_history WHERE
        // success=0;)

        // flyway.repair();

        // Undo last migration

        // flyway.undo();

        // Get all tests

        System.out.println("Tests:");

        for (Test test : testDAO.getAll()) {
            System.out.println(test);
        }
    }
}
