# Library Management System

## Overview
The **Library Management System** is a Java-based console application designed to manage library operations, including user authentication, book management, borrowing, selling, and discussion forums. It supports multiple user roles (Reader, Librarian, Seller, Borrower, PremiumUser, Admin, Moderator), each with specific functionalities. The program is organized in the `qbfd` package and demonstrates core Object-Oriented Programming (OOP) concepts. A test suite is included to verify the functionality of key components.

This README explains the OOP concepts used in the code, provides the project’s file structure (including the testing file), and includes instructions for compiling, running, and testing the application.

## File Structure
The project is organized with source files in the `qbfd` package under `src`, the test file under `test/qbfd`, and the README at the project root:

```
LibraryManagementSystem/
├── README.md
├── src/
│   └── qbfd/
│       ├── Admin.java
│       ├── Authenticatable.java
│       ├── Book.java
│       ├── BookForSale.java
│       ├── BookRepository.java
│       ├── BorrowableBook.java
│       ├── Borrower.java
│       ├── Librarian.java
│       ├── LibraryDiscussionForum.java
│       ├── LibraryManagementSystem.java
│       ├── Moderator.java
│       ├── PremiumUser.java
│       ├── Reader.java
│       ├── Seller.java
│       └── User.java
├── test/
│   └── qbfd/
│       └── LibraryManagementSystemTest.java
```

### File Descriptions
- **README.md**: This file, providing project overview, OOP explanations, and setup/testing instructions.
- **src/qbfd/**:
  - `Admin.java`: Manages library operations with extended profile attributes.
  - `Authenticatable.java`: Interface for user authentication.
  - `Book.java`: Base class for books with common attributes.
  - `BookForSale.java`: Extends `Book` for books available for purchase.
  - `BookRepository.java`: Manages the book collection and operations like adding or borrowing.
  - `BorrowableBook.java`: Extends `Book` for books that can be borrowed.
  - `Borrower.java`: Handles book borrowing and availability functions.
  - `Librarian.java`: Manages book addition and catalog viewing.
  - `LibraryDiscussionForum.java`: Supports discussion forum interactions.
  - `LibraryManagementSystem.java`: Main class with the program entry point.
  - `Moderator.java`: Handles forum moderation.
  - `PremiumUser.java`: Provides access to premium content.
  - `Reader.java`: Supports book searching and viewing details.
  - `Seller.java`: Manages adding books for sale.
  - `User.java`: Abstract base class for all user types, implementing `Authenticatable`.
- **test/qbfd/**:
  - `LibraryManagementSystemTest.java`: JUnit 5 test suite to verify functionality, including user authentication, book management, borrowing, and discussion forum interactions.

## OOP Concepts in the Code

### 1. Encapsulation
Encapsulation bundles data and methods within classes, using access modifiers to restrict access and ensure data integrity.

- **Private Fields**: The `User` class encapsulates `username` and `password` as private fields, accessible only through the constructor and `getUsername()` method, protecting sensitive data.
  ```java
  private String username;
  private String password;
  public String getUsername() {
      return username;
  }
  ```
- **Role-Specific Data**: Classes like `Admin` (with `name`, `age`, `experience`, `availableHours`, `zone`) and `Moderator` (with `fullName`, `age`, `moderationExperience`) encapsulate role-specific attributes, accessible via methods like `viewAdminProfile()` or `viewModeratorProfile()`.
- **BookRepository**: The `books` list is private, and operations like `addBook`, `borrowBook`, or `viewBookDetails` provide controlled access to the collection.
  ```java
  private List<Book> books = new ArrayList<>();
  ```

### 2. Inheritance
Inheritance promotes code reuse by allowing subclasses to inherit properties and methods from a parent class.

- **User Hierarchy**: The abstract `User` class is extended by `Reader`, `Librarian`, `Seller`, `Borrower`, `PremiumUser`, `Admin`, and `Moderator`, inheriting common attributes (`username`, `password`) and methods (`authenticate`, `viewProfile`).
  ```java
  public abstract class User implements Authenticatable {
      // Common fields and methods
  }
  public class Seller extends User {
      // Seller-specific functionality
  }
  ```
- **Book Hierarchy**: `Book` is extended by `BookForSale` and `BorrowableBook`, inheriting attributes like `title`, `author`, `genre`, and `publisher` while adding specific fields (`price` for `BookForSale`, `borrower` for `BorrowableBook`).
  ```java
  public class BookForSale extends Book {
      private double price;
      // Constructor and methods
  }
  ```

### 3. Polymorphism
Polymorphism allows objects of different classes to be treated as instances of a common superclass or interface, enabling flexible behavior.

- **Method Overriding**: The abstract `login` method in `User` is overridden in each subclass to provide role-specific login messages.
  ```java
  public abstract void login();
  // In Borrower.java
  @Override
  public void login() {
      System.out.println("Borrower " + getUsername() + " logged in successfully!");
  }
  ```
- **Interface Polymorphism**: The `Authenticatable` interface’s `authenticate` method is implemented by `User` and used uniformly across all subclasses in the `authenticateUser` method.
  ```java
  private static User authenticateUser(List<User> users, String username, String password) {
      for (User user : users) {
          if (user.authenticate(username, password)) {
              return user;
          }
      }
      return null;
  }
  ```
- **Runtime Polymorphism**: In `LibraryManagementSystem`, the `currentUser` variable (type `User`) can hold any subclass object, and methods like `login()` are called based on the actual object type at runtime.
  ```java
  if (currentUser instanceof Admin) {
      Admin admin = (Admin) currentUser;
      admin.manageLibrary();
  }
  ```

### 4. Abstraction
Abstraction hides implementation details and exposes only essential functionality through abstract classes and interfaces.

- **Abstract Class `User`**: Defines the abstract `login` method, requiring subclasses to implement it, while providing concrete methods like `authenticate`.
  ```java
  public abstract class User implements Authenticatable {
      public abstract void login();
  }
  ```
- **Interface `Authenticatable`**: Specifies the `authenticate` method contract, abstracting the authentication logic across all user types.
  ```java
  interface Authenticatable {
      boolean authenticate(String username, String password);
  }
  ```
- **BookRepository**: Abstracts book management operations, allowing users to add, borrow, or view books without interacting with the underlying `List<Book>` structure.

## Additional OOP Features
- **Composition**: `BookRepository` contains a `List<Book>`, and `Book` classes reference a `User` (`addedBy`) or `Borrower` (`borrower`), demonstrating composition.
- **Type Checking and Casting**: The `instanceof` operator and casting enable role-specific actions while treating objects as `User` types.
  ```java
  if (currentUser instanceof Reader) {
      Reader reader = (Reader) currentUser;
      reader.searchBooks(repository);
  }
  ```

## Setup Instructions
1. **Prerequisites**: Install Java Development Kit (JDK) version 8 or higher. For testing, include JUnit 5 in the classpath (e.g., via Maven/Gradle or JUnit JARs).
2. **Directory Setup**: Place all `.java` source files in `src/qbfd/`, the test file in `test/qbfd/`, and `README.md` in the root `LibraryManagementSystem/` directory.
3. **Compile**: Open a terminal in the `LibraryManagementSystem/` directory and compile the source files:
   ```bash
   javac src/qbfd/*.java
   ```
4. **Run**: Execute the main class:
   ```bash
   java -cp src qbfd.LibraryManagementSystem
   ```
5. **Usage**: Log in with credentials (e.g., `reader1/pass123`, `librarian1/librarianPass`) and follow prompts for role-specific actions.

## Testing
The project includes a JUnit 5 test suite to verify functionality:
- **Test File**: `LibraryManagementSystemTest.java` in `test/qbfd/`.
- **Test Coverage**:
  - User authentication and login for all roles.
  - Book addition by `Librarian` and `Seller`.
  - Book borrowing and availability by `Borrower`.
  - Book searching and details viewing by `Reader`.
  - Role-specific actions for `PremiumUser`, `Admin`, and `Moderator`.
  - Discussion forum functionality.
- **Running Tests**:
  1. Compile the source and test files (ensure JUnit 5 is in the classpath):
     ```bash
     javac -cp .:junit-jupiter-api-5.9.0.jar src/qbfd/*.java test/qbfd/*.java
     ```
  2. Run the tests using a JUnit runner:
     ```bash
     java -cp .:junit-jupiter-5.9.0.jar:src:test org.junit.platform.console.ConsoleLauncher --select-class qbfd.LibraryManagementSystemTest
     ```
- **Dependencies**: Download JUnit 5 JARs (e.g., `junit-jupiter-api-5.9.0.jar`, `junit-jupiter-engine-5.9.0.jar`) or use a build tool like Maven:
  ```xml
  <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>5.9.0</version>
      <scope>test</scope>
  </dependency>
  ```
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
👨‍💻 Author

- Sajeev Kaleeswaran
- Email: sajeevkaleeswaran@gmail.com
- GitHub: github.com/sajeev-k22

## Notes
- **Resource Management**: The code uses `Scanner` for input but may have resource leaks (unclosed `Scanner` instances). In production, use try-with-resources.
- **Mock Implementation**: The `searchAndFilter` method returns mock data (`"Sample Book 1", "Sample Book 2"`). Enhance this for real search functionality.
- **Extensibility**: The modular design supports adding new user roles or book types by extending `User` or `Book`.
- **Testing Limitations**: The test suite mocks `Scanner` input and captures `System.out` output. Additional tests can be added for edge cases (e.g., invalid inputs).

This project demonstrates how encapsulation, inheritance, polymorphism, and abstraction create a robust, maintainable, and testable library management system.
