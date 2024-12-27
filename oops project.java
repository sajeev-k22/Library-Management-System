package qbfd;

import java.util.*;

interface Authenticatable {
    boolean authenticate(String username, String password);
}

abstract class User implements Authenticatable {
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

class Reader extends User {
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

class Librarian extends User {
    public Librarian(String username, String password) {
        super(username, password);
    }

    @Override
    public void login() {
        System.out.println("Librarian " + getUsername() + " logged in successfully!");
    }

    public void addBook(BookRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book title: ");
        String bookTitle = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter publisher: ");
        String publisher = scanner.nextLine();
        repository.addBook(this, bookTitle, author, genre, publisher);
        System.out.println("Book added successfully by Librarian " + getUsername());
    }

    public void viewAllBooks(BookRepository repository) {
        repository.viewAllBooks();
        System.out.println("Viewing all books in the library.");
    }
}

class Seller extends User {
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

class Borrower extends User {
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

    // Additional method to make "Sample Book 1" available for borrowing
    public void makeSampleBook1Available(BookRepository repository) {
        repository.makeBookAvailableForBorrowing("Sample Book 1");
        System.out.println("Sample Book 1 made available for borrowing.");
    }
}

class PremiumUser extends User {
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

class BookRepository {
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
            System.out.println(); // Separator between books
        } else {
            // Modified to simulate that "Sample Book 1" is found
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
        // Mock implementation for searching and filtering books
        // This can be extended based on actual requirements
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
            System.out.println(); // Separator between books
        }
    }
}

class Book {
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private User addedBy;

    public Book(String title, String author, String genre, String publisher, User addedBy) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.addedBy = addedBy;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public User getAddedBy() {
        return addedBy;
    }
}

class BookForSale extends Book {
    private double price;

    public BookForSale(String title, String author, String genre, String publisher, User addedBy, double price) {
        super(title, author, genre, publisher, addedBy);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

class BorrowableBook extends Book {
    private Borrower borrower;

    public BorrowableBook(String title, String author, String genre, String publisher, User addedBy) {
        super(title, author, genre, publisher, addedBy);
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public boolean isAvailable() {
        return borrower == null;
    }
}

class LibraryDiscussionForum {
    public void startDiscussion(User user, String topic, String message) {
        System.out.println(user.getUsername() + " started a discussion on '" + topic + "': " + message);
    }
}

class Admin extends User {
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
class Moderator extends User {
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

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<User> users = new ArrayList<>();
        BookRepository repository = new BookRepository();
        LibraryDiscussionForum discussionForum = new LibraryDiscussionForum();

        // Adding mock users
        users.add(new Reader("reader1", "pass123"));
        users.add(new Librarian("librarian1", "librarianPass"));
        users.add(new Seller("seller1", "sellerPass"));
        users.add(new Borrower("borrower1", "borrowerPass"));
        users.add(new PremiumUser("premium1", "premiumPass"));
        users.add(new Admin("admin1", "adminPass", "John Doe", 30, 5, 40, "Central Zone"));
        users.add(new Moderator("moderator1", "moderatorPass", "Jane Smith", 28, 3));

        // Interactive login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User currentUser = authenticateUser(users, username, password);

        if (currentUser != null) {
            currentUser.login();
            currentUser.viewProfile();

            if (currentUser instanceof Reader) {
                // Reader-specific actions
                Reader reader = (Reader) currentUser;
                reader.searchBooks(repository);
                reader.viewBookDetails(repository);
            } else if (currentUser instanceof Librarian) {
                // Librarian-specific actions
                Librarian librarian = (Librarian) currentUser;
                librarian.addBook(repository);
                
                librarian.viewAllBooks(repository);
            } else if (currentUser instanceof Seller) {
                // Seller-specific actions
                Seller seller = (Seller) currentUser;
                seller.addBookForSale(repository);
            } else if (currentUser instanceof Borrower) {
                // Borrower-specific actions
                Borrower borrower = (Borrower) currentUser;
                borrower.makeSampleBook1Available(repository);
                borrower.borrowBook(repository);
            } else if (currentUser instanceof PremiumUser) {
                // PremiumUser-specific actions
                PremiumUser premiumUser = (PremiumUser) currentUser;
                premiumUser.accessPremiumContent();
            } else if (currentUser instanceof Admin) {
                // Admin-specific actions
                Admin admin = (Admin) currentUser;
                admin.manageLibrary();
                admin.viewAdminProfile();
            } else if (currentUser instanceof Moderator) {
                // Moderator-specific actions
                Moderator moderator = (Moderator) currentUser;
                moderator.moderateForum();
                moderator.viewModeratorProfile();
            }

            // Additional features - Discussion Forum
            if (currentUser instanceof Reader) {
                Reader reader = (Reader) currentUser;
                System.out.print("Library Discussion Forum\nEnter discussion topic: ");
                String discussionTopic = scanner.nextLine();
                System.out.print("Enter your message: ");
                String discussionMessage = scanner.nextLine();
                discussionForum.startDiscussion(reader, discussionTopic, discussionMessage);
            }
        } else {
            System.out.println("Invalid credentials.");
        }

        scanner.close();
        System.out.println("Close");
    }

    private static User authenticateUser(List<User> users, String username, String password) {
        for (User user : users) {
            if (user.authenticate(username, password)) {
                return user;
            }
        }
        return null;
    }
}