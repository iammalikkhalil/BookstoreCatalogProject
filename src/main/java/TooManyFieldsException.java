/**
 * -----------------------------------------------------
 * Assignment 2 - Part 1
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
 * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * Custom exception thrown when a book record has too many fields.
 * This exception is used during syntax validation to identify records with excess data.
 */

public class TooManyFieldsException extends Exception {

    /**
     * Constructs a new TooManyFieldsException with a default error message.
     */
    public TooManyFieldsException() {
        super("Too many fields in record.");
    }
}