/**
 * -----------------------------------------------------
 * Assignment 2 - Part 1
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
 * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * Custom exception thrown when a book record has an unrecognized genre.
 * This exception is used during syntax validation to flag records with invalid genre codes.
 */

public class UnknownGenreException extends Exception {

    /**
     * Constructs a new UnknownGenreException with a default error message.
     */
    public UnknownGenreException() {
        super("Unknown genre in record.");
    }
}