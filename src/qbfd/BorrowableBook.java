package qbfd;

public class BorrowableBook extends Book {
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