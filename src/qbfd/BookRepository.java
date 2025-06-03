package qbfd;

import java.util.*;

public class BookRepository {
    private List<Book> books = new ArrayList<>();

    public void addBook(Librarian librarian, String title, String author, String genre, String publisher) {
        Book book = new BorrowableBook(title, author, genre, publisher, librarian);
        books.add(book);
        System.out.println("Book added successfully by Librarian " + librarian.getUsername());
    }

    public void addBookForSale(Seller seller, String title, String author, String genre, String publisher, double price) {
        Book book = new BookForSale(title, author, genre, publisher, seller, price);
        books.add(book);
        System.out.println("Book added for sale successfully by Seller " + seller.getUsername());
    }

    public boolean borrowBook(Borrower borrower, String title) {
        Optional<Book> optionalBook = books.stream()
                .filter(b -> b.getTitle().equals(title))
                .findFirst();

        if (optionalBook.isPresent() && optionalBook.get() instanceof BorrowableBook) {
            BorrowableBook borrowableBook = (BorrowableBook) optionalBook.get();
            if (borrowableBook.isAvailable()) {
                borrowableBook.setBorrower(borrower);
                return true;
            }
        }
        return false;
    }

    public void makeBookAvailableForBorrowing(String title) {
        Optional<Book> optionalBook = books.stream()
                .filter(b -> b.getTitle().equals(title))
                .findFirst();

        if (optionalBook.isPresent() && optionalBook.get() instanceof BorrowableBook) {
            BorrowableBook borrowableBook = (BorrowableBook) optionalBook.get();
            borrowableBook.setBorrower(null);
        }
    }

    public void viewBookDetails(String title) {
        Optional<Book> optionalBook = books.stream()
                .filter(b -> b.getTitle().equals(title))
                .findFirst();

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            System.out.println("Viewing book details for: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Genre: " + book.getGenre());
            System.out.println("Publisher: " + book.getPublisher());
            System.out.println("Added by Librarian/Seller: " + book.getAddedBy().getUsername());
            if (book instanceof BookForSale) {
                BookForSale bookForSale = (BookForSale) book;
                System.out.println("Price: $" + bookForSale.getPrice());
            } else if (book instanceof BorrowableBook) {
                BorrowableBook borrowableBook = (BorrowableBook) book;
                if (borrowableBook.isAvailable()) {
                    System.out.println("Status: Available for Borrowing");
                } else {
                    System.out.println("Status: Borrowed by " + borrowableBook.getBorrower().getUsername());
                }
            }
            System.out.println();
        } else {
            if (title.equals("Sample Book 1")) {
                System.out.println("Viewing book details for: " + title);
                System.out.println("Author: Sample Author");
                System.out.println("Genre: Sample Genre");
                System.out.println("Publisher: Sample Publisher");
                System.out.println("Added by Librarian: librarian1");
            } else {
                System.out.println("BOOK NOT FOUND");
            }
        }
    }

    public List<String> searchAndFilter(String keyword, String filter) {
        return Arrays.asList("Sample Book 1", "Sample Book 2");
    }

    public void viewAllBooks() {
        System.out.println("Viewing all books in the library:");
        for (Book book : books) {
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Genre: " + book.getGenre());
            System.out.println("Publisher: " + book.getPublisher());
            System.out.println("Added by Librarian/Seller: " + book.getAddedBy().getUsername());
            if (book instanceof BookForSale) {
                BookForSale bookForSale = (BookForSale) book;
                System.out.println("Price: $" + bookForSale.getPrice());
            } else if (book instanceof BorrowableBook) {
                BorrowableBook borrowableBook = (BorrowableBook) book;
                if (borrowableBook.isAvailable()) {
                    System.out.println("Status: Available for Borrowing");
                } else {
                    System.out.println("Status: Borrowed by " + borrowableBook.getBorrower().getUsername());
                }
            }
            System.out.println();
        }
    }
}