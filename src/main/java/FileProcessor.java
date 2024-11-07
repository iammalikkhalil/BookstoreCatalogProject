
/**
 * -----------------------------------------------------
 * Assignment 2 - Part 1
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
  * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * The FileProcessor class handles reading, processing, and validating book records
 * from input CSV files. It checks for syntax errors and categorizes valid records 
 * by genre, saving them to genre-specific output files.
 * 
 * It also logs any syntax errors encountered during processing to a separate file.
 */

import java.io.*;
import java.util.*;

public class FileProcessor {

    private static final String INPUT_DIR = "resources/input_files/";
    private static final String OUTPUT_DIR = "resources/output_files/";

    /**
     * Processes a list of input files specified in the provided file.
     * Ensures necessary directories exist, reads each input file, and
     * validates and categorizes book records based on syntax.
     *
     * @param fileName The path to the file containing the list of input CSV files.
     */
    public static void processFiles(String fileName) {
        // Ensure directories exist
        checkAndCreateDirectory("resources");
        checkAndCreateDirectory(INPUT_DIR);
        checkAndCreateDirectory(OUTPUT_DIR);

        List<String> fileNames = readInputFileNames(fileName);

        for (String inputFileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_DIR + inputFileName))) {
                processRecords(reader, inputFileName);
            } catch (IOException e) {
                System.out.println("Error reading file: " + inputFileName);
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
     * Reads a list of file names from the specified file.
     * The first line of the file contains the number of files listed.
     *
     * @param fileName The file containing the list of input file names.
     * @return A list of file names to process.
     */
    private static List<String> readInputFileNames(String fileName) {
        List<String> fileNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int count = Integer.parseInt(reader.readLine().trim());
            for (int i = 0; i < count; i++) {
                fileNames.add(reader.readLine().trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading file list: " + fileName);
        }
        return fileNames;
    }

    /**
     * Processes each record in the input file, validating syntax.
     * Valid records are saved to genre-specific files, while syntax errors
     * are logged to an error file.
     *
     * @param reader        The BufferedReader for reading the input file.
     * @param inputFileName The name of the current input file being processed.
     */
    private static void processRecords(BufferedReader reader, String inputFileName) {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                try {
                    Book record = SyntaxValidator.validateRecord(line);
                    writeToGenreFile(record);
                } catch (Exception e) {
                    logSyntaxError(e.getMessage(), line, inputFileName);
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing records in file: " + inputFileName);
        }
    }

    /**
     * Writes a valid book record to the corresponding genre file.
     *
     * @param record The validated Book object to write.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    private static void writeToGenreFile(Book record) throws IOException {
        String genreFile = OUTPUT_DIR + record.getGenreFileName();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(genreFile, true))) {
            writer.write(record.toCsvLine());
            writer.newLine();
        }
    }

    /**
     * Logs a syntax error to the syntax error file, recording the error
     * message, the faulty record, and the file in which it was found.
     *
     * @param error    The error message for the syntax issue.
     * @param record   The record that caused the error.
     * @param fileName The name of the file where the error was found.
     */
    private static void logSyntaxError(String error, String record, String fileName) {
        String errorFile = OUTPUT_DIR + "syntax_error_file.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(errorFile, true))) {
            writer.write("syntax error in file: " + fileName);
            writer.newLine();
            writer.write("====================");
            writer.newLine();
            writer.write("Error: " + error);
            writer.newLine();
            writer.write("Record: " + record);
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error logging syntax error: " + e.getMessage());
        }
    }
}