/**
 * -----------------------------------------------------
 * Assignment 2 - Part 2
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
  * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * Exception thrown when an invalid ISBN-13 format is detected in a book record.
 * This exception is used in the semantic validation of book records.
 */
public class BadIsbn13Exception extends Exception {

    /**
     * Constructs a new BadIsbn13Exception with a default error message.
     */
    public BadIsbn13Exception() {
        super("Invalid ISBN-13 in record.");
    }
}
