package qbfd;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryManagementSystemTest {
    private List<User> users;
    private BookRepository repository;
    private LibraryDiscussionForum discussionForum;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        repository = new BookRepository();
        discussionForum = new LibraryDiscussionForum();
        users.add(new Reader("reader1", "pass123"));
        users.add(new Librarian("librarian1", "librarianPass"));
        users.add(new Seller("seller1", "sellerPass"));
        users.add(new Borrower("borrower1", "borrowerPass"));
        users.add(new PremiumUser("premium1", "premiumPass"));
        users.add(new Admin("admin1", "adminPass", "John Doe", 30, 5, 40, "Central Zone"));
        users.add(new Moderator("moderator1", "moderatorPass", "Jane Smith", 28, 3));

        // Redirect System.out to capture output
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        // Restore System.out and System.in
        System.setOut(originalOut);
        System.setIn(originalIn);
        outContent.reset();
    }

    @Test
    void testReaderAuthenticationAndLogin() {
        User reader = findUser("reader1", "pass123");
        assertNotNull(reader, "Reader should be found");
        assertTrue(reader instanceof Reader, "User should be a Reader");
        reader.login();
        assertTrue(outContent.toString().contains("Reader reader1 logged in successfully!"));
    }

    @Test
    void testLibrarianAddBook() {
        Librarian librarian = (Librarian) findUser("librarian1", "librarianPass");
        assertNotNull(librarian, "Librarian should be found");

        // Mock Scanner input
        String input = "Test Book\nTest Author\nFiction\nTest Publisher\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        librarian.addBook(repository);
        String output = outContent.toString();
        assertTrue(output.contains("Book added successfully by Librarian librarian1"));
    }

    @Test
    void testSellerAddBookForSale() {
        Seller seller = (Seller) findUser("seller1", "sellerPass");
        assertNotNull(seller, "Seller should be found");

        // Mock Scanner input
        String input = "Sale Book\nSale Author\nNon-Fiction\nSale Publisher\n19.99\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        seller.addBookForSale(repository);
        String output = outContent.toString();
        assertTrue(output.contains("Book added for sale successfully by Seller seller1"));
    }

    @Test
    void testBorrowerBorrowBook() {
        Borrower borrower = (Borrower) findUser("borrower1", "borrowerPass");
        assertNotNull(borrower, "Borrower should be found");

        // Add a borrowable book
        Librarian librarian = (Librarian) findUser("librarian1", "librarianPass");
        repository.addBook(librarian, "Borrowable Book", "Author", "Genre", "Publisher");

        // Mock Scanner input
        String input = "Borrowable Book\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        borrower.borrowBook(repository);
        String output = outContent.toString();
        assertTrue(output.contains("Book borrowed successfully by Borrower borrower1"));
    }

    @Test
    void testBorrowerMakeSampleBookAvailable() {
        Borrower borrower = (Borrower) findUser("borrower1", "borrowerPass");
        assertNotNull(borrower, "Borrower should be found");

        borrower.makeSampleBook1Available(repository);
        String output = outContent.toString();
        assertTrue(output.contains("Sample Book 1 made available for borrowing."));
    }

    @Test
    void testReaderSearchBooks() {
        Reader reader = (Reader) findUser("reader1", "pass123");
        assertNotNull(reader, "Reader should be found");

        // Mock Scanner input
        String input = "keyword\nauthor\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        reader.searchBooks(repository);
        String output = outContent.toString();
        assertTrue(output.contains("Search results: [Sample Book 1, Sample Book 2]"));
    }

    @Test
    void testViewBookDetails() {
        // Test for a non-existent book with special case for "Sample Book 1"
        Reader reader = (Reader) findUser("reader1", "pass123");
        assertNotNull(reader, "Reader should be found");

        // Mock Scanner input
        String input = "Sample Book 1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        reader.viewBookDetails(repository);
        String output = outContent.toString();
        assertTrue(output.contains("Viewing book details for: Sample Book 1"));
        assertTrue(output.contains("Author: Sample Author"));
    }

    @Test
    void testPremiumUserAccessContent() {
        PremiumUser premiumUser = (PremiumUser) findUser("premium1", "premiumPass");
        assertNotNull(premiumUser, "PremiumUser should be found");

        premiumUser.accessPremiumContent();
        String output = outContent.toString();
        assertTrue(output.contains("Premium User premium1 accessing premium content!"));
    }

    @Test
    void testAdminProfile() {
        Admin admin = (Admin) findUser("admin1", "adminPass");
        assertNotNull(admin, "Admin should be found");

        admin.viewAdminProfile();
        String output = outContent.toString();
        assertTrue(output.contains("Admin Profile - Name: John Doe, Age: 30, Experience: 5 years, Available Hours: 40 hours, Zone: Central Zone"));
    }

    @Test
    void testModeratorProfile() {
        Moderator moderator = (Moderator) findUser("moderator1", "moderatorPass");
        assertNotNull(moderator, "Moderator should be found");

        moderator.viewModeratorProfile();
        String output = outContent.toString();
        assertTrue(output.contains("Moderator Profile - Full Name: Jane Smith, Age: 28, Moderation Experience: 3 years"));
    }

    @Test
    void testDiscussionForum() {
        Reader reader = (Reader) findUser("reader1", "pass123");
        assertNotNull(reader, "Reader should be found");

        discussionForum.startDiscussion(reader, "Book Club", "Discussing favorite books!");
        String output = outContent.toString();
        assertTrue(output.contains("reader1 started a discussion on 'Book Club': Discussing favorite books!"));
    }

    @Test
    void testInvalidAuthentication() {
        User user = findUser("invalidUser", "wrongPass");
        assertNull(user, "User with invalid credentials should not be found");
    }

    private User findUser(String username, String password) {
        for (User user : users) {
            if (user.authenticate(username, password)) {
                return user;
            }
        }
        return null;
    }
}