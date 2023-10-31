package repository;

public class UserRepositoryImplementation implements UserRepository {

    @Override
    public User getUser(String name) {
        for(User user:UserDB.db) {
            if(user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        UserDB.db.add(user);
    }

    @Override
    public void updateUser(User user) {
        String userName = user.getName();
        
        for(User dbUser:UserDB.db) {
            if(dbUser.getName().equals(userName)) {
                dbUser.setAdmin(user.isAdmin());
                dbUser.setPassword(user.getPassword());
            }
        }
    }

    @Override
    public void removeUser(User user) {
        UserDB.db.remove(user);
    }
    
}
