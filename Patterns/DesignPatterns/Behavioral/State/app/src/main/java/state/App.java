/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package state;

public class App {
    public static void main(String[] args) {
        TVContext context = new TVContext();
        context.doAction("change channel");
        context.doAction("click power button");
        context.doAction("change channel");
    }
}
