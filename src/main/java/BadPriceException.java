/**
 * -----------------------------------------------------
 * Assignment 2 - Part 2
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
  * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * Exception thrown when an invalid price is detected in a book record.
 * This exception is used in the semantic validation of book records.
 */
public class BadPriceException extends Exception {

    /**
     * Constructs a new BadPriceException with a default error message.
     */
    public BadPriceException() {
        super("Invalid price in record.");
    }
}
