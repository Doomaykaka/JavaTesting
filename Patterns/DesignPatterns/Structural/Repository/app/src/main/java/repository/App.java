/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package repository;

public class App {
    public static void main(String[] args) {
        User usr1 = new User();
        usr1.setAdmin(false);
        usr1.setName("User1");
        usr1.setPassword("123");

        User usr2 = new User();
        usr2.setAdmin(true);
        usr2.setName("User2");
        usr2.setPassword("456");

        User usr3 = new User();
        usr3.setAdmin(false);
        usr3.setName("User3");
        usr3.setPassword("789");

        UserRepository repository = new UserRepositoryImplementation();

        repository.addUser(usr1);
        repository.addUser(usr2);
        repository.addUser(usr3);

        UserDB.getStatus();

        repository.removeUser(usr2);

        UserDB.getStatus();

        usr1.setPassword("111");

        repository.updateUser(usr1);

        UserDB.getStatus();

        User savedUser = repository.getUser("User1");

        System.out.println(
                "Getted user: " + savedUser.getName() + "," + savedUser.getPassword() + "," + savedUser.isAdmin());
    }
}