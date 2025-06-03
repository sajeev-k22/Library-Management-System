package qbfd;

public class Moderator extends User {
    private String fullName;
    private int age;
    private int moderationExperience;

    public Moderator(String username, String password, String fullName, int age, int moderationExperience) {
        super(username, password);
        this.fullName = fullName;
        this.age = age;
        this.moderationExperience = moderationExperience;
    }

    @Override
    public void login() {
        System.out.println("Moderator " + getUsername() + " logged in successfully!");
    }

    public void moderateForum() {
        System.out.println("Moderator " + getUsername() + " moderating the forum.");
    }

    public void viewModeratorProfile() {
        System.out.println("Moderator Profile - Full Name: " + fullName + ", Age: " + age +
                ", Moderation Experience: " + moderationExperience + " years");
    }
}