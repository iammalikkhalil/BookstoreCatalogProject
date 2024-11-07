/**
 * -----------------------------------------------------
 * Assignment 2 - Parts 1, 2, and 3
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
 * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * The Main class serves as the entry point for the Book Catalog Processor program.
 * It sequentially executes Parts 1, 2, and 3:
 * - Part 1: Syntax validation and categorization of book records.
 * - Part 2: Semantic validation and serialization of valid book records.
 * - Part 3: Interactive navigation through serialized book records.
 * This class also clears previous output and binary files at the start of each run.
 */

import java.io.File;

public class Main {

    /**
     * Main method that initiates the processing of Parts 1, 2, and 3.
     * Clears the output and binary files directories before starting.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Book Catalog Processor - Parts 1, 2, and 3");
        System.out.println("Written by: [Your Full Name]");

        clearOutputDirectories();

        do_part1();
        do_part2();
        do_part3();

        System.out.println("Processing complete. Check the output files for results.");
        System.out.println("Program terminated. Thank you for using the Book Catalog Processor.");
    }

    /**
     * Clears the `output_files` and `binary_files` directories at the start of the program,
     * ensuring no leftover files or data from previous runs.
     */
    private static void clearOutputDirectories() {
        deleteDirectoryRecursively(new File("resources/output_files"));
        deleteDirectoryRecursively(new File("resources/binary_files"));
    }

    /**
     * Recursively deletes a directory and all its contents (files and subdirectories).
     * If the directory does not exist, it does nothing.
     *
     * @param directory The directory to delete.
     */
    private static void deleteDirectoryRecursively(File directory) {
        if (directory.exists()) {
            File[] allContents = directory.listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    deleteDirectoryRecursively(file);  // Recursively delete subdirectories and files
                }
            }
            directory.delete();  // Delete the directory itself
        }
    }

    /**
     * Executes Part 1 of the assignment, which involves syntax validation and
     * categorization of book records into genre-specific files.
     */
    private static void do_part1() {
        FileProcessor.processFiles("resources/Part1_input_file_names.txt");
    }

    /**
     * Executes Part 2 of the assignment, performing semantic validation on
     * the categorized records and serializing valid records to binary files.
     */
    private static void do_part2() {
        SemanticValidator.validateAndSerializeFiles();
    }

    /**
     * Executes Part 3 of the assignment, providing an interactive menu-driven
     * interface for navigating through serialized book records.
     */
    private static void do_part3() {
        InteractiveNavigator.start();
    }
}