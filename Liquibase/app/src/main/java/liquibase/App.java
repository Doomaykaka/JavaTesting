package liquibase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import liquibase.configuration.HiberConfiguration;
import liquibase.models.Test;
import liquibase.parser.ChangeLogParser;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.parser.core.xml.XMLChangeLogSAXParser;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StringUtil;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.command.CommandResults;
import liquibase.command.CommandScope;
import liquibase.command.core.DropAllCommandStep;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.ChangeExecListenerCommandStep;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.command.core.helpers.ShowSummaryArgument;
import liquibase.configuration.ApplicationConfigReader;
import liquibase.dao.TestDAO;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.logging.mdc.MdcKey;

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
            throws SQLException, LiquibaseException, FileNotFoundException {
        final String changelogRootPath = System.getProperty("user.dir");
        final String changelogFolderPath = "/db/changelog/";
        final String changelogFilename = "db-changelog-master.xml";
        final String changelogFilePath = changelogFolderPath + changelogFilename;
        final String url = HiberConfiguration.buildDatabaseUrl(configReader);

        Connection connection = DriverManager.getConnection(url, configReader.getDbUser(),
                configReader.getDbPassword());

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        // Define your search paths
        File projectRoot = new File(changelogRootPath + "/src/main/resources");
        File sharedChangelogs = new File(projectRoot, "/db/changelog");

        // Create a CompositeResourceAccessor with your desired search paths
        CompositeResourceAccessor resourceAccessor = new CompositeResourceAccessor(new ClassLoaderResourceAccessor(), // For
                                                                                                                      // resources
                                                                                                                      // on
                                                                                                                      // the
                                                                                                                      // classpath
                new DirectoryResourceAccessor(projectRoot), new DirectoryResourceAccessor(sharedChangelogs));

        File changelogFile = new File(changelogFilePath);

        if (!changelogFile.exists()) {
            System.out.println("Bad changelog file");
        }

        String changeLogPath = changelogFolderPath + changelogFilename;
        ChangeLogParameters changeLogParameters = new ChangeLogParameters(database);
        boolean shouldWarnOnMismatchedXsdVersion = false;
        DatabaseChangeLog changeLog = getDatabaseChangeLog(changeLogPath, resourceAccessor, changeLogParameters,
                shouldWarnOnMismatchedXsdVersion);

        try (Liquibase liquibase = new Liquibase(changeLogPath, resourceAccessor, database)) {
            // Drop all database

            CommandScope dropAllCommand = new CommandScope(DropAllCommandStep.COMMAND_NAME);
            dropAllCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
            CommandResults dropResult = dropAllCommand.execute();

            System.out.println("Drop all result");

            System.out.println(dropResult.getCommandScope().getCompleteConfigPrefix());

            // Update database

            CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG,
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(database.getConnection()));
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_ARG, changeLog);
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG,
                    (changelogFolderPath + changelogFilename).replace('\\', '/'));
            updateCommand.addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, null);
            updateCommand.addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, null);
            updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, null);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY_OUTPUT, null);
            updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS,
                    new ChangeLogParameters(database));
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY, null);
            updateCommand.execute();

            // Get all tests

            List<Test> tests = testDAO.getAll();

            for (Test obj : tests) {
                System.out.println(obj);
            }
        }
    }

    private static DatabaseChangeLog getDatabaseChangeLog(String changeLogFile, ResourceAccessor resourceAccessor,
            ChangeLogParameters changeLogParameters, boolean shouldWarnOnMismatchedXsdVersion)
            throws LiquibaseException {
        DatabaseChangeLog databaseChangeLog = null;

        ChangeLogParser parser = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor);
        if (parser instanceof XMLChangeLogSAXParser) {
            ((XMLChangeLogSAXParser) parser).setShouldWarnOnMismatchedXsdVersion(shouldWarnOnMismatchedXsdVersion);
        }
        databaseChangeLog = parser.parse(changeLogFile, changeLogParameters, resourceAccessor);
        Scope.getCurrentScope().getLog(Liquibase.class).info("Parsed changelog file '" + changeLogFile + "'");
        if (StringUtil.isNotEmpty(databaseChangeLog.getLogicalFilePath())) {
            Scope.getCurrentScope().addMdcValue(MdcKey.CHANGELOG_FILE, databaseChangeLog.getLogicalFilePath());
        } else {
            Scope.getCurrentScope().addMdcValue(MdcKey.CHANGELOG_FILE, changeLogFile);
        }

        return databaseChangeLog;
    }
}
