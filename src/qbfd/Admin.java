package qbfd;

public class Admin extends User {
    private String name;
    private int age;
    private int experience;
    private int availableHours;
    private String zone;

    public Admin(String username, String password, String name, int age, int experience, int availableHours, String zone) {
        super(username, password);
        this.name = name;
        this.age = age;
        this.experience = experience;
        this.availableHours = availableHours;
        this.zone = zone;
    }

    @Override
    public void login() {
        System.out.println("Admin " + getUsername() + " logged in successfully!");
    }

    public void manageLibrary() {
        System.out.println("Admin " + getUsername() + " managing library operations.");
    }

    public void viewAdminProfile() {
        System.out.println("Admin Profile - Name: " + name + ", Age: " + age + ", Experience: " + experience +
                " years, Available Hours: " + availableHours + " hours, Zone: " + zone);
    }
}