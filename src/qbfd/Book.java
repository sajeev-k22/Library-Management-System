package qbfd;

public class Book {
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