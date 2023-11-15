/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package memento;

public class App {
    public static void main(String[] args) {
        User user = new User("user1", "123");
        User.MementoUser memento = user.createMemento();
        memento.setName("mem1");
        
        System.out.println(memento.getName() + " " + memento.getCreationTimestamp());
    
        user.changePassword("user1", "123", "456");
        System.out.println("Validate before restore: " + user.validate("user1", "123"));
        
        user.restoreMemento(memento);
        System.out.println("Validate after restore: " + user.validate("user1", "123"));
    }
}
