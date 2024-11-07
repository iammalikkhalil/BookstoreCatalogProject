
/**
 * -----------------------------------------------------
 * Assignment 2 - Part 1
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
 * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * The SyntaxValidator class is responsible for validating the syntax of 
 * book records from a CSV file. It checks for issues like missing fields, 
 * too many or too few fields, and unknown genres, using custom exceptions 
 * to signal specific syntax errors.
 */

import java.util.Arrays;

public class SyntaxValidator {

    /**
     * Validates the syntax of a book record by checking for the correct number
     * of fields, presence of required fields, and a valid genre.
     *
     * @param line The CSV line representing a book record.
     * @return A Book object if the record passes all syntax validations.
     * @throws TooManyFieldsException If the record has more fields than expected.
     * @throws TooFewFieldsException  If the record has fewer fields than expected.
     * @throws MissingFieldException  If required fields are missing from the
     *                                record.
     * @throws UnknownGenreException  If the genre of the record is not recognized.
     * @throws NumberFormatException  If numeric fields (price, year) are
     *                                incorrectly formatted.
     */
    public static Book validateRecord(String line) throws Exception {
        String[] fields = line.split(",", -1);

        if (fields.length > 6)
            throw new TooManyFieldsException();
        if (fields.length < 6)
            throw new TooFewFieldsException();

        String title = fields[0].trim();
        String authors = fields[1].trim();
        double price;
        String isbn = fields[3].trim();
        String genre = fields[4].trim();
        int year;

        if (title.isEmpty() || authors.isEmpty() || isbn.isEmpty() || genre.isEmpty()) {
            throw new MissingFieldException("missing fields in record");
        }

        price = Double.parseDouble(fields[2].trim());
        year = Integer.parseInt(fields[5].trim());

        if (!isValidGenre(genre))
            throw new UnknownGenreException();

        return new Book(title, authors, price, isbn, genre, year);
    }

    /**
     * Checks if the specified genre is valid by comparing it against a list of
     * known genres.
     *
     * @param genre The genre code to validate.
     * @return True if the genre is valid, false otherwise.
     */
    private static boolean isValidGenre(String genre) {
        String[] validGenres = { "CCB", "HCB", "MTV", "MRB", "NEB", "OTR", "SSM", "TPA" };
        return Arrays.asList(validGenres).contains(genre);
    }
}