package qbfd;

public class PremiumUser extends User {
    public PremiumUser(String username, String password) {
        super(username, password);
    }

    @Override
    public void login() {
        System.out.println("Premium User " + getUsername() + " logged in successfully!");
    }

    public void accessPremiumContent() {
        System.out.println("Premium User " + getUsername() + " accessing premium content!");
    }
}