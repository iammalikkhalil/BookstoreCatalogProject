import java.io.Serializable;

class Book implements Serializable {
    private String title;
    private String authors;
    private double price;
    private String isbn;
    private String genre;
    private int year;

    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }

    @Override
    public String toString() {
        return title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year;
    }

    // Getters and setters if needed
}
