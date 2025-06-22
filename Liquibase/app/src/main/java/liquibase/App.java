package liquibase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
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
import liquibase.command.core.CalculateChecksumCommandStep;
import liquibase.command.core.ChangelogSyncCommandStep;
import liquibase.command.core.DiffCommandStep;
import liquibase.command.core.DropAllCommandStep;
import liquibase.command.core.GenerateChangelogCommandStep;
import liquibase.command.core.RollbackCommandStep;
import liquibase.command.core.StatusCommandStep;
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

        prepareAndTestFlyway(testDAO, configReader);
    }

    private static TestDAO prepareDAO(ApplicationConfigReader configReader) throws FileNotFoundException, IOException {
        TestDAO testDAO = null;

        HiberConfiguration.addEntity(Test.class);
        HiberConfiguration.build(configReader);

        testDAO = new TestDAO(HiberConfiguration.getEntityManagerFactory());

        return testDAO;
    }

    private static void prepareAndTestFlyway(TestDAO testDAO, ApplicationConfigReader configReader)
            throws FileNotFoundException, SQLException, LiquibaseException {
        // Input variables
        final String changelogRootPath = System.getProperty("user.dir");
        final String changelogFolderPath = "/db/changelog/";
        final String changelogFilename = "db-changelog-master.json";
        final String changelogFilePath = changelogFolderPath + changelogFilename;
        final String url = HiberConfiguration.buildDatabaseUrl(configReader);

        System.setProperty("liquibase.duplicateFileMode", "WARN");

        Connection connection = DriverManager.getConnection(url, configReader.getDbUser(),
                configReader.getDbPassword());

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        // Define your search paths
        File projectRoot = new File(changelogRootPath + "/src/main/resources");
        File sharedChangelogs = new File(projectRoot, "/db/changelog");

        // Create a CompositeResourceAccessor with your desired search paths
        CompositeResourceAccessor resourceAccessor = new CompositeResourceAccessor(new ClassLoaderResourceAccessor(),
                new DirectoryResourceAccessor(projectRoot), new DirectoryResourceAccessor(sharedChangelogs));
        File changelogFile = new File(sharedChangelogs, changelogFilename);

        if (!changelogFile.exists()) {
            System.out.println("Bad changelog file");
            System.exit(0);
        }

        String changeLogPath = changelogFolderPath + changelogFilename;
        ChangeLogParameters changeLogParameters = new ChangeLogParameters(database);
        boolean shouldWarnOnMismatchedXsdVersion = false;

        DatabaseChangeLog changeLog = getDatabaseChangeLog(changeLogPath, resourceAccessor, changeLogParameters,
                shouldWarnOnMismatchedXsdVersion);

        testFlyway(configReader, testDAO, changeLogPath, changelogFolderPath, changelogFilename, resourceAccessor,
                database, changeLog, sharedChangelogs);
    }

    private static void testFlyway(ApplicationConfigReader configReader, TestDAO testDAO, String changeLogPath,
            String changelogFolderPath, String changelogFilename, CompositeResourceAccessor resourceAccessor,
            Database database, DatabaseChangeLog changeLog, File sharedChangelogs)
            throws SQLException, LiquibaseException, FileNotFoundException {

        try (Liquibase liquibase = new Liquibase(changeLogPath, resourceAccessor, database)) {

            // Drop all database

            System.out.println("Drop all");

            CommandScope dropAllCommand = new CommandScope(DropAllCommandStep.COMMAND_NAME);
            dropAllCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
            CommandResults dropResult = dropAllCommand.execute();

            System.out.println("Drop all result");

            String dropAllResultRepr = Arrays.toString(dropResult.getCommandScope().getCommand().getName());

            System.out.println(dropAllResultRepr + " Success");

            System.out.println("-------------------------------------------------");

            // Update database

            System.out.println("Update");

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
            CommandResults updateResult = updateCommand.execute();

            System.out.println("Update result");

            String updateResultRepr = Arrays.toString(updateResult.getCommandScope().getCommand().getName());

            System.out.println(updateResultRepr + " Success");

            System.out.println("-------------------------------------------------");

            // Rollback

            System.out.println("Rollback");

            CommandScope rollbackCommand = new CommandScope(RollbackCommandStep.COMMAND_NAME);
            rollbackCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
            rollbackCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_ARG, changeLog);
            rollbackCommand.addArgumentValue(RollbackCommandStep.TAG_ARG, "version_1.0.0");
            CommandResults rollbackResult = rollbackCommand.execute();

            System.out.println("Rollback result");

            String rollbackResultRepr = Arrays.toString(rollbackResult.getCommandScope().getCommand().getName());

            System.out.println(rollbackResultRepr + " Success");

            System.out.println("-------------------------------------------------");

            // Report status

            System.out.println("Report status");

            CommandScope reportStatusCommand = new CommandScope(StatusCommandStep.COMMAND_NAME);
            reportStatusCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
            reportStatusCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_ARG, changeLog);
            CommandResults reportStatusResult = reportStatusCommand.execute();

            System.out.println("Report status result");

            String reportStatusResultRepr = Arrays
                    .toString(reportStatusResult.getCommandScope().getCommand().getName());

            System.out.println(reportStatusResultRepr + " Success");

            System.out.println("-------------------------------------------------");

            // Diff

            System.out.println("Diff");

            CommandScope diffCommand = new CommandScope(DiffCommandStep.COMMAND_NAME);
            diffCommand.addArgumentValue("referenceUrl", HiberConfiguration.buildDatabaseUrl(configReader));
            diffCommand.addArgumentValue("url", HiberConfiguration.buildDatabaseUrl(configReader));
            CommandResults diffResult = diffCommand.execute();

            System.out.println("Diff result");

            String diffResultRepr = Arrays.toString(diffResult.getCommandScope().getCommand().getName());

            System.out.println(diffResultRepr + " Success");

            System.out.println("-------------------------------------------------");

            // Changelog sync

            System.out.println("Changelog sync");

            CommandScope changelogSyncCommand = new CommandScope(ChangelogSyncCommandStep.COMMAND_NAME);
            changelogSyncCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
            changelogSyncCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_ARG, changeLog);
            CommandResults changelogSyncResult = changelogSyncCommand.execute();

            System.out.println("Changelog sync result");

            String changelogSyncResultRepr = Arrays
                    .toString(changelogSyncResult.getCommandScope().getCommand().getName());

            System.out.println(changelogSyncResultRepr + " Success");

            System.out.println("-------------------------------------------------");

            // Generate changelog

            System.out.println("Generate changelog");

            CommandScope generateChangelogCommand = new CommandScope(GenerateChangelogCommandStep.COMMAND_NAME);
            generateChangelogCommand.addArgumentValue("referenceUrl",
                    HiberConfiguration.buildDatabaseUrl(configReader));
            generateChangelogCommand.addArgumentValue("url", HiberConfiguration.buildDatabaseUrl(configReader));
            CommandResults generateChangelogResult = generateChangelogCommand.execute();

            System.out.println("Generate changelog result");

            String generateChangelogResultRepr = Arrays
                    .toString(generateChangelogResult.getCommandScope().getCommand().getName());

            System.out.println(generateChangelogResultRepr + " Success");

            System.out.println("-------------------------------------------------");

            // Calculate checksum

            System.out.println("Calculate checksum");

            CommandScope calculateChecksumCommand = new CommandScope("calculateChecksum");
            calculateChecksumCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG,
                    (changelogFolderPath + changelogFilename).replace('\\', '/'));
            calculateChecksumCommand.addArgumentValue("url", HiberConfiguration.buildDatabaseUrl(configReader));
            calculateChecksumCommand.addArgumentValue("changesetIdentifier", "create-table-test");
            CommandResults calculateChecksumResult = calculateChecksumCommand.execute();

            System.out.println("Calculate checksum result");

            String calculateChecksumResultRepr = Arrays
                    .toString(calculateChecksumResult.getCommandScope().getCommand().getName());

            System.out.println(calculateChecksumResultRepr + " Success");

            System.out.println("-------------------------------------------------");

            // Check liquibase tables

            // Clear checksums

            // Force release locks

            // Future rollback sql

            // Generate documentation

            // Is safe to run update

            // Is up to date fast check

            // Mark next change set ran

            // Tag

            // Validate

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
