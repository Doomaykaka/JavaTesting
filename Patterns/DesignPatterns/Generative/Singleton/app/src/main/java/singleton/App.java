/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package singleton;

public class App {
    public static void main(String[] args) {
        Database foo = Database.getInstance();
        foo.query("SELECT ...");

        Database bar = Database.getInstance();
        bar.query("UPDATE ...");
    }
}
