
/**
 * -----------------------------------------------------
 * Assignment 2 - Part 3
 * COMP249 - Fall 2024
 * Written by: [Your Name], [Your Student ID]
 * Due Date: November 8, 2024
 * -----------------------------------------------------
 *
 * The InteractiveNavigator class provides an interactive menu-driven interface 
 * for users to navigate through serialized book records. It allows users to 
 * select a file, view records, and navigate forward or backward within a file.
 */

import java.io.*;
import java.util.Scanner;

public class InteractiveNavigator {

    private static final String BINARY_DIR = "resources/binary_files/";
    private static Book[] currentBooks;
    private static int currentIndex;
    private static String selectedFileName = "No file selected";

    /**
     * Starts the interactive navigation interface, allowing users to select files
     * and navigate through book records.
     */
    public static void start() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("-----------------------------");
            System.out.println("Main Menu");
            System.out.println("-----------------------------");
            System.out.println("v View the selected file: " + getSelectedFileStatus());
            System.out.println("s Select a file to view");
            System.out.println("x Exit");
            System.out.println("-----------------------------");
            System.out.print("Enter Your Choice: ");

            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "v":
                    if (currentBooks == null) {
                        System.out.println("No file selected. Please select a file first.");
                    } else {
                        System.out.println("Viewing: " + getSelectedFileStatus());
                        navigateRecords(scanner);
                    }
                    break;
                case "s":
                    selectFile(scanner);
                    break;
                case "x":
                    exit = true;
                    System.out.println("Exiting. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    /**
     * Displays a file selection menu, allowing the user to choose a binary file
     * to load for navigation.
     *
     * @param scanner The Scanner object for reading user input.
     */
    private static void selectFile(Scanner scanner) {
        System.out.println("------------------------------");
        System.out.println("File Sub-Menu");
        System.out.println("------------------------------");

        String[] files = {
                "Cartoons_Comics_Books.csv.ser", "Hobbies_Collectibles_Books.csv.ser",
                "Movies_TV_Books.csv.ser", "Music_Radio_Books.csv.ser", "Nostalgia_Eclectic_Books.csv.ser",
                "Old_Time_Radio_Books.csv.ser", "Sports_Sports_Memorabilia.csv.ser", "Trains_Planes_Automobiles.csv.ser"
        };

        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + " " + files[i] + " (" + getRecordCount(files[i]) + " records)");
        }
        System.out.println("9 Exit");
        System.out.println("------------------------------");
        System.out.print("Enter Your Choice: ");

        int fileChoice = Integer.parseInt(scanner.nextLine()) - 1;

        if (fileChoice >= 0 && fileChoice < files.length) {
            loadFile(files[fileChoice]);
        } else if (fileChoice == 8) {
            System.out.println("Returning to main menu.");
        } else {
            System.out.println("Invalid selection. Try again.");
        }
    }

    /**
     * Loads the selected binary file and deserializes it into an array of Book
     * objects.
     *
     * @param fileName The name of the file to load.
     */
    private static void loadFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BINARY_DIR + fileName))) {
            currentBooks = (Book[]) ois.readObject();
            currentIndex = 0;
            selectedFileName = fileName;
            System.out.println("Loaded file: " + fileName + " with " + currentBooks.length + " records.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + fileName);
        }
    }

    /**
     * Returns the status of the currently selected file with the record count.
     * If no file is selected, returns "No file selected."
     *
     * @return The status of the selected file or "No file selected" if no file is
     *         loaded.
     */
    private static String getSelectedFileStatus() {
        return (currentBooks == null) ? "No file selected"
                : selectedFileName + " (" + currentBooks.length + " records)";
    }

    /**
     * Returns the number of records in the specified file by deserializing it.
     *
     * @param fileName The name of the binary file to check.
     * @return The number of Book objects in the file.
     */
    private static int getRecordCount(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BINARY_DIR + fileName))) {
            Book[] books = (Book[]) ois.readObject();
            return books.length;
        } catch (IOException | ClassNotFoundException e) {
            return 0;
        }
    }

    /**
     * Provides navigation within the currently loaded records, allowing users to
     * move
     * forward, backward, or return to the main menu.
     *
     * @param scanner The Scanner object for reading user input.
     */
    private static void navigateRecords(Scanner scanner) {
        if (currentBooks.length == 0) {
            System.out.println("No records available to view in the selected file.");
            return;
        }

        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\nCurrently viewing: Record " + (currentIndex + 1) + " of " + currentBooks.length);
            System.out.println(currentBooks[currentIndex]);
            System.out.print("Enter number of records to move (+/-), or 0 to return to main menu: ");

            int n = Integer.parseInt(scanner.nextLine());

            if (n == 0) {
                backToMain = true;
            } else if (n > 0) {
                moveForward(n);
            } else {
                moveBackward(-n);
            }
        }
    }

    /**
     * Moves forward by the specified number of records, updating the current index.
     *
     * @param n The number of records to move forward.
     */
    private static void moveForward(int n) {
        int newIndex = Math.min(currentIndex + n, currentBooks.length - 1);
        displayRecords(currentIndex, newIndex);
        currentIndex = newIndex;
    }

    /**
     * Moves backward by the specified number of records, updating the current
     * index.
     *
     * @param n The number of records to move backward.
     */
    private static void moveBackward(int n) {
        int newIndex = Math.max(currentIndex - n, 0);
        displayRecords(newIndex, currentIndex);
        currentIndex = newIndex;
    }

    /**
     * Displays records from the starting index to the ending index, displaying
     * boundary messages if BOF (beginning of file) or EOF (end of file) is reached.
     *
     * @param start The starting index for display.
     * @param end   The ending index for display.
     */
    private static void displayRecords(int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.println(currentBooks[i]);
        }
        if (end == currentBooks.length - 1) {
            System.out.println("EOF has been reached");
        } else if (start == 0) {
            System.out.println("BOF has been reached");
        }
    }
}