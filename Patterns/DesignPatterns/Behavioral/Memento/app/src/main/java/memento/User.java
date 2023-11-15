package memento;

import java.time.LocalDateTime;

public class User {
    private String login;
    private String password;
    private int id;

    public User(String login, String password) {
        this.login = login;
        this.password = password;

        this.id = (login + password).hashCode();
    }

    public boolean validate(String login, String password) {
        return (this.login.equals(login)) && (this.password.equals(password));
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        if ((this.login.equals(login)) && (this.password.equals(oldPassword))) {
            this.password = newPassword;

            return true;
        }

        return false;
    }

    public MementoUser createMemento() {
        return new MementoUser(login, password, id);
    }

    public void restoreMemento(MementoUser memento) {
        if (this.id == memento.id) {
            this.login = memento.login;
            this.password = memento.password;
        }
    }

    public class MementoUser {
        private String login;
        private String password;
        private int id;

        private String name = "Default";
        private LocalDateTime stamp;

        public MementoUser(String login, String password, int id) {
            this.login = login;
            this.password = password;
            this.id = id;

            this.stamp = LocalDateTime.now();
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public LocalDateTime getCreationTimestamp() {
            return stamp;
        }
    }
}
