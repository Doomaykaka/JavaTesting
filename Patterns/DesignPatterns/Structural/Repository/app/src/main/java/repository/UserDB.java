package repository;

import java.util.ArrayList;
import java.util.List;

public class UserDB {
    public static List<User> db = new ArrayList<User>();

    public static void getStatus() {
        System.out.println("===DB status===");

        for (User user : db) {
            System.out.println(
                    "User: name=" + user.getName() + ",password=" + user.getPassword() + ",isAdmin=" + user.isAdmin());
        }

        System.out.println("===============");
    }
}
