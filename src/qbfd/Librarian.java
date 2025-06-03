package qbfd;

public class Librarian extends User {
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