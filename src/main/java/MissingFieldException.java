/**
 * -----------------------------------------------------
 * Assignment 2 - Part 1
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
 * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * Custom exception thrown when a required field is missing in a book record.
 * This exception is used during syntax validation to identify records with incomplete data.
 */

public class MissingFieldException extends Exception {

    /**
     * Constructs a new MissingFieldException with a specified error message.
     *
     * @param message The error message indicating which field is missing.
     */
    public MissingFieldException(String message) {
        super(message);
    }
}