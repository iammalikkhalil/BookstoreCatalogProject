/**
 * -----------------------------------------------------
 * Assignment 2 - Part 1
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
 * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * Custom exception thrown when a book record has too few fields.
 * This exception is used during syntax validation to identify incomplete records.
 */

public class TooFewFieldsException extends Exception {

    /**
     * Constructs a new TooFewFieldsException with a default error message.
     */
    public TooFewFieldsException() {
        super("Too few fields in record.");
    }
}