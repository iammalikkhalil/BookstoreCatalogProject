/**
 * -----------------------------------------------------
 * Assignment 2 - Part 2
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
 * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * Exception thrown when an invalid ISBN-10 format is detected in a book record.
 * This exception is used in semantic validation of book records.
 */
public class BadIsbn10Exception extends Exception {

    /**
     * Constructs a new BadIsbn10Exception with a default error message.
     */
    public BadIsbn10Exception() {
        super("Invalid ISBN-10 in record.");
    }
}