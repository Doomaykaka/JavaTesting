package liquibase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import liquibase.configuration.HiberConfiguration;
import liquibase.models.Test;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.configuration.ApplicationConfigReader;
import liquibase.dao.TestDAO;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;

public class App {
    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException, LiquibaseException {
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

    private static void testFlyway(TestDAO testDAO, ApplicationConfigReader configReader)
            throws SQLException, LiquibaseException {
        final String changelogFilePath = System.getProperty("user.dir") + "/db/changelog/db-changelog-master.xml";
        final String url = HiberConfiguration.buildDatabaseUrl(configReader);

//        List<Test> tests = testDAO.getAll();
//
//        for (Test obj : tests) {
//            System.out.println(obj);
//        }   

        Connection connection = DriverManager.getConnection(url, configReader.getDbUser(),
                configReader.getDbPassword());

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Liquibase liquibase = new Liquibase(changelogFilePath, new ClassLoaderResourceAccessor(), database);

        liquibase.dropAll();
        
        CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
        updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
        updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changelogFilePath);        
        updateCommand.execute();
    }
}
