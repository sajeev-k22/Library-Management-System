package qbfd;

import java.util.Scanner;

public class Borrower extends User {
    public Borrower(String username, String password) {
        super(username, password);
    }

    @Override
    public void login() {
        System.out.println("Borrower " + getUsername() + " logged in successfully!");
    }

    public void borrowBook(BookRepository repository) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the book title to borrow: ");
        String bookTitle = scanner.nextLine();
        boolean success = repository.borrowBook(this, bookTitle);

        if (success) {
            System.out.println("Book borrowed successfully by Borrower " + getUsername());
        } else {
            System.out.println("Book not available for borrowing or not found.");
        }
    }

    public void makeSampleBook1Available(BookRepository repository) {
        repository.makeBookAvailableForBorrowing("Sample Book 1");
        System.out.println("Sample Book 1 made available for borrowing.");
    }
}