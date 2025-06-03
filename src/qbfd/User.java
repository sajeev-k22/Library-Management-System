package qbfd;

public abstract class User implements Authenticatable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public abstract void login();

    public String getUsername() {
        return username;
    }

    public void viewProfile() {
        System.out.println("Viewing profile for user: " + username);
    }
}