package qbfd;

import java.util.Scanner;

public class Seller extends User {
    public Seller(String username, String password) {
        super(username, password);
    }

    @Override
    public void login() {
        System.out.println("Seller " + getUsername() + " logged in successfully!");
    }

    public void addBookForSale(BookRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book title: ");
        String bookTitle = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter publisher: ");
        String publisher = scanner.nextLine();
        double price;
        while (true) {
            try {
                System.out.print("Enter price: ");
                price = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price.");
            }
        }
        repository.addBookForSale(this, bookTitle, author, genre, publisher, price);
        System.out.println("Book added for sale successfully by Seller " + getUsername());
    }
}