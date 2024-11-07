/**
 * -----------------------------------------------------
 * Assignment 2 - Part 1
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
  * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * Represents a book record with attributes for title, authors, price, ISBN, genre, and year.
 * This class provides methods to retrieve the appropriate genre file name and format 
 * the book record as a CSV line.
 */
import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String authors;
    private double price;
    private String isbn;
    private String genre;
    private int year;

    /**
     * Constructs a new Book object with the specified details.
     *
     * @param title   The title of the book.
     * @param authors The authors of the book.
     * @param price   The price of the book.
     * @param isbn    The ISBN of the book.
     * @param genre   The genre code of the book.
     * @param year    The year of publication.
     */
    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }

    /**
     * Returns the filename associated with the book's genre.
     *
     * @return The filename as a String, corresponding to the genre code.
     */
    public String getGenreFileName() {
        switch (genre) {
            case "CCB": return "Cartoons_Comics_Books.csv";
            case "HCB": return "Hobbies_Collectibles_Books.csv";
            case "MTV": return "Movies_TV.csv";
            case "MRB": return "Music_Radio_Books.csv";
            case "NEB": return "Nostalgia_Eclectic_Books.csv";
            case "OTR": return "Old_Time_Radio.csv";
            case "SSM": return "Sports_Sports_Memorabilia.csv";
            case "TPA": return "Trains_Planes_Automobiles.csv";
            default: return "unknown_genre.csv";
        }
    }

    /**
     * Returns the book's details formatted as a line in CSV format.
     *
     * @return A CSV-formatted String with the book's attributes.
     */
    public String toCsvLine() {
        return title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year;
    }
}