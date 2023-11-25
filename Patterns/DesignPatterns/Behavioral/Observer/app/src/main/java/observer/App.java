/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package observer;

public class App {
    public static void main(String[] args) {
        CatholicChurch catholicChurch = new CatholicChurch();

        new Parishioner("Мартин Лютер", catholicChurch);
        new Parishioner("Жан Кальвин", catholicChurch);

        catholicChurch.setNewsChurch("Инквизиция была ошибкой... месса Mea Culpa 12 марта 2000 года");
    }
}