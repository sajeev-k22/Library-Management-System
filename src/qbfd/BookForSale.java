package qbfd;

public class BookForSale extends Book {
    private double price;

    public BookForSale(String title, String author, String genre, String publisher, User addedBy, double price) {
        super(title, author, genre, publisher, addedBy);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}