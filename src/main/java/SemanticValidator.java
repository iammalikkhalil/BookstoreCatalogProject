
/**
 * -----------------------------------------------------
 * Assignment 2 - Part 2
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
 * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * The SemanticValidator class performs semantic validation on book records.
 * It ensures that each record meets criteria for price, year, and ISBN format.
 * Valid records are serialized to binary files, and invalid records are logged 
 * in a semantic error file.
 */

import java.io.*;
import java.util.*;

public class SemanticValidator {

    private static final String INPUT_DIR = "resources/output_files/";
    private static final String BINARY_DIR = "resources/binary_files/";
    private static final String SEMANTIC_ERROR_FILE = INPUT_DIR + "semantic_error_file.txt";

    /**
     * Validates and serializes book records from genre-specific CSV files.
     * Ensures directories exist, performs semantic validation, and logs errors.
     * Serializes valid records to binary files.
     */
    public static void validateAndSerializeFiles() {
        // Ensure directories exist
        checkAndCreateDirectory(BINARY_DIR);

        String[] genreFiles = {
                "Cartoons_Comics_Books.csv", "Hobbies_Collectibles_Books.csv",
                "Movies_TV.csv", "Music_Radio_Books.csv", "Nostalgia_Eclectic_Books.csv",
                "Old_Time_Radio.csv", "Sports_Sports_Memorabilia.csv", "Trains_Planes_Automobiles.csv"
        };

        for (String genreFile : genreFiles) {
            List<Book> validBooks = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_DIR + genreFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        Book book = validateSemanticRecord(line);
                        validBooks.add(book);
                    } catch (Exception e) {
                        logSemanticError(e.getMessage(), line, genreFile);
                    }
                }
                serializeBooks(validBooks, genreFile + ".ser");
            } catch (IOException e) {
                System.out.println("Error reading genre file: " + genreFile);
            }
        }
    }

    /**
     * Checks if the specified directory exists; if not, creates it.
     *
     * @param dirPath The path to the directory to check or create.
     */
    private static void checkAndCreateDirectory(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists() && dir.mkdirs()) {
            System.out.println("Created directory: " + dirPath);
        }
    }

    /**
     * Validates a single book record's semantic fields, including price, year,
     * and ISBN format. Throws specific exceptions if validation fails.
     *
     * @param line The CSV line representing a book record.
     * @return A Book object if the record passes all validations.
     * @throws Exception If a semantic validation error occurs.
     */
    private static Book validateSemanticRecord(String line) throws Exception {
        String[] fields = line.split(",", -1);

        String title = fields[0].trim();
        String authors = fields[1].trim();
        double price = Double.parseDouble(fields[2].trim());
        String isbn = fields[3].trim();
        String genre = fields[4].trim();
        int year = Integer.parseInt(fields[5].trim());

        if (price < 0)
            throw new BadPriceException();
        if (year < 1995 || year > 2010)
            throw new BadYearException();
        if (isbn.length() == 10 && !isValidISBN10(isbn))
            throw new BadIsbn10Exception();
        if (isbn.length() == 13 && !isValidISBN13(isbn))
            throw new BadIsbn13Exception();

        return new Book(title, authors, price, isbn, genre, year);
    }

    /**
     * Checks if the provided ISBN-10 is valid based on its checksum calculation.
     *
     * @param isbn The ISBN-10 string to validate.
     * @return True if the ISBN-10 is valid, false otherwise.
     */
    private static boolean isValidISBN10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (10 - i) * (isbn.charAt(i) - '0');
        }
        return sum % 11 == 0;
    }

    /**
     * Checks if the provided ISBN-13 is valid based on its checksum calculation.
     *
     * @param isbn The ISBN-13 string to validate.
     * @return True if the ISBN-13 is valid, false otherwise.
     */
    private static boolean isValidISBN13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : 3 * digit;
        }
        return sum % 10 == 0;
    }

    /**
     * Serializes a list of valid Book records to a binary file.
     *
     * @param books    The list of Book objects to serialize.
     * @param fileName The name of the binary file to store the serialized objects.
     */
    private static void serializeBooks(List<Book> books, String fileName) {
        String filePath = BINARY_DIR + fileName;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(books.toArray(new Book[0]));
            System.out.println("Serialized " + books.size() + " books to " + fileName);
        } catch (IOException e) {
            System.out.println("Error serializing to file: " + filePath);
        }
    }

    /**
     * Logs a semantic error to the semantic error file, recording the error
     * message,
     * the faulty record, and the file in which it was found.
     *
     * @param error    The error message for the semantic issue.
     * @param record   The record that caused the error.
     * @param fileName The name of the file where the error was found.
     */
    private static void logSemanticError(String error, String record, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SEMANTIC_ERROR_FILE, true))) {
            writer.write("semantic error in file: " + fileName);
            writer.newLine();
            writer.write("====================");
            writer.newLine();
            writer.write("Error: " + error);
            writer.newLine();
            writer.write("Record: " + record);
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error logging semantic error: " + e.getMessage());
        }
    }
}