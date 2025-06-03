package qbfd;

import java.util.*;

public class Reader extends User {
    public Reader(String username, String password) {
        super(username, password);
    }

    @Override
    public void login() {
        System.out.println("Reader " + getUsername() + " logged in successfully!");
    }

    public void searchBooks(BookRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        System.out.print("Enter filter (author/genre/publisher): ");
        String filter = scanner.nextLine();
        List<String> results = repository.searchAndFilter(keyword, filter);
        System.out.println("Search results: " + results);
    }

    public void viewBookDetails(BookRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the book title to view details: ");
        String bookTitle = scanner.nextLine();
        repository.viewBookDetails(bookTitle);
    }
}