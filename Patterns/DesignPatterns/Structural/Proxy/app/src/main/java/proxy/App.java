/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package proxy;

public class App {
    public static void main(String[] args) {
        Data data = new DataImplementation();

        System.out.println("======Using class without security======");

        try {
            data.addData(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("======Using class with security proxy======");

        data = new DataProxy(data);

        data.addData(null);
    }
}
