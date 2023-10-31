package repository;

public interface UserRepository {
    User getUser(String name);
    void addUser(User user);
    void updateUser(User user);
    void removeUser(User user);
}
