package qbfd;

import java.util.*;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<User> users = new ArrayList<>();
        BookRepository repository = new BookRepository();
        LibraryDiscussionForum discussionForum = new LibraryDiscussionForum();

        users.add(new Reader("reader1", "pass123"));
        users.add(new Librarian("librarian1", "librarianPass"));
        users.add(new Seller("seller1", "sellerPass"));
        users.add(new Borrower("borrower1", "borrowerPass"));
        users.add(new PremiumUser("premium1", "premiumPass"));
        users.add(new Admin("admin1", "adminPass", "John Doe", 30, 5, 40, "Central Zone"));
        users.add(new Moderator("moderator1", "moderatorPass", "Jane Smith", 28, 3));

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User currentUser = authenticateUser(users, username, password);

        if (currentUser != null) {
            currentUser.login();
            currentUser.viewProfile();

            if (currentUser instanceof Reader) {
                Reader reader = (Reader) currentUser;
                reader.searchBooks(repository);
                reader.viewBookDetails(repository);
            } else if (currentUser instanceof Librarian) {
                Librarian librarian = (Librarian) currentUser;
                librarian.addBook(repository);
                librarian.viewAllBooks(repository);
            } else if (currentUser instanceof Seller) {
                Seller seller = (Seller) currentUser;
                seller.addBookForSale(repository);
            } else if (currentUser instanceof Borrower) {
                Borrower borrower = (Borrower) currentUser;
                borrower.makeSampleBook1Available(repository);
                borrower.borrowBook(repository);
            } else if (currentUser instanceof PremiumUser) {
                PremiumUser premiumUser = (PremiumUser) currentUser;
                premiumUser.accessPremiumContent();
            } else if (currentUser instanceof Admin) {
                Admin admin = (Admin) currentUser;
                admin.manageLibrary();
                admin.viewAdminProfile();
            } else if (currentUser instanceof Moderator) {
                Moderator moderator = (Moderator) currentUser;
                moderator.moderateForum();
                moderator.viewModeratorProfile();
            }

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