// -----------------------------------------------------
// Assignment 2
// Bookstore Catalog Project
// Written by: Your Name, ID: Your Student ID
// -----------------------------------------------------

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    System.out.println("Welcome to the Bookstore Catalog Management System!");

    try {
      doPart1();
      doPart2();
      doPart3();
    } catch (Exception e) {
      System.out.println("An error occurred: " + e.getMessage());
      e.printStackTrace();
    }

    System.out.println("Thank you for using the Bookstore Catalog Management System!");
  }

  private static void doPart1() {
    System.out.println("Starting Part 1: Syntax Validation and Partitioning...");
    try (BufferedWriter errorWriter = new BufferedWriter(new FileWriter("output/syntax_error_file.txt"))) {
      List<String> fileNames = readFileNames("src/main/resources/data/Part1_input_file_names.txt");

      for (String fileName : fileNames) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/" + fileName))) {
          String line;
          while ((line = reader.readLine()) != null) {
            try {
              validateAndPartition(line, fileName);
            } catch (Exception e) {
              errorWriter.write("Syntax error in file: " + fileName + "\n====================\n");
              errorWriter.write("Error: " + e.getMessage() + "\nRecord: " + line + "\n\n");
            }
          }
        } catch (FileNotFoundException e) {
          System.out.println("File not found: " + fileName);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Part 1 complete: Syntax errors written to syntax_error_file.txt");
  }

  private static List<String> readFileNames(String fileName) throws IOException {
    List<String> fileNames = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      reader.readLine();
      String line;
      while ((line = reader.readLine()) != null) {
        fileNames.add(line.trim());
      }
    }
    return fileNames;
  }

  private static void validateAndPartition(String record, String fileName) throws Exception {
    String[] fields = record.split(",", -1);
    if (fields.length > 6)
      throw new TooManyFieldsException("Too many fields");
    if (fields.length < 6)
      throw new TooFewFieldsException("Too few fields");

    if (fields[0].isEmpty())
      throw new MissingFieldException("Missing title");
    if (fields[1].isEmpty())
      throw new MissingFieldException("Missing authors");
    if (fields[2].isEmpty())
      throw new MissingFieldException("Missing price");
    if (fields[3].isEmpty())
      throw new MissingFieldException("Missing ISBN");
    if (fields[4].isEmpty())
      throw new MissingFieldException("Missing genre");
    if (fields[5].isEmpty())
      throw new MissingFieldException("Missing year");

    String genre = fields[4];
    String outputFile = getOutputFileName(genre);
    if (outputFile == null)
      throw new UnknownGenreException("Unknown genre");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/genre_files/" + outputFile, true))) {
      writer.write(record + "\n");
    }
  }

  private static String getOutputFileName(String genreCode) {
    return switch (genreCode) {
      case "CCB" -> "Cartoons_Comics_Books.csv";
      case "HCB" -> "Hobbies_Collectibles_Books.csv";
      case "MTV" -> "Movies_TV.csv";
      case "MRB" -> "Music_Radio_Books.csv";
      case "NEB" -> "Nostalgia_Eclectic_Books.csv";
      case "OTR" -> "Old_Time_Radio.csv";
      case "SSM" -> "Sports_Sports_Memorabilia.csv";
      case "TPA" -> "Trains_Planes_Automobiles.csv";
      default -> null;
    };
  }

  private static void doPart2() {
    System.out.println("Starting Part 2: Semantic Validation and Serialization...");
    try (BufferedWriter errorWriter = new BufferedWriter(new FileWriter("output/semantic_error_file.txt"))) {
      String[] genreFiles = {
          "Cartoons_Comics_Books.csv", "Hobbies_Collectibles_Books.csv", "Movies_TV.csv",
          "Music_Radio_Books.csv", "Nostalgia_Eclectic_Books.csv", "Old_Time_Radio.csv",
          "Sports_Sports_Memorabilia.csv", "Trains_Planes_Automobiles.csv"
      };

      for (String genreFile : genreFiles) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("output/genre_files/" + genreFile))) {
          String line;
          while ((line = reader.readLine()) != null) {
            try {
              Book book = validateAndCreateBook(line);
              books.add(book);
            } catch (Exception e) {
              errorWriter.write("Semantic error in file: " + genreFile + "\n====================\n");
              errorWriter.write("Error: " + e.getMessage() + "\nRecord: " + line + "\n\n");
            }
          }
        } catch (FileNotFoundException e) {
          System.out.println("File not found: " + genreFile);
        }

        if (!books.isEmpty()) {
          serializeBooks("output/binary_files/" + genreFile + ".ser", books);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Part 2 complete: Semantic errors written to semantic_error_file.txt");
  }

  private static Book validateAndCreateBook(String record) throws Exception {
    String[] fields = record.split(",", -1);
    String title = fields[0];
    String authors = fields[1];
    double price = Double.parseDouble(fields[2]);
    String isbn = fields[3];
    String genre = fields[4];
    int year = Integer.parseInt(fields[5]);

    if (price < 0)
      throw new BadPriceException("Invalid price: " + price);
    if (year < 1995 || year > 2010)
      throw new BadYearException("Invalid year: " + year);

    if (isbn.length() == 10 && !isValidISBN10(isbn))
      throw new BadIsbn10Exception("Invalid ISBN-10: " + isbn);
    if (isbn.length() == 13 && !isValidISBN13(isbn))
      throw new BadIsbn13Exception("Invalid ISBN-13: " + isbn);

    return new Book(title, authors, price, isbn, genre, year);
  }

  private static boolean isValidISBN10(String isbn) {
    int sum = 0;
    for (int i = 0; i < 10; i++)
      sum += (10 - i) * Character.getNumericValue(isbn.charAt(i));
    return sum % 11 == 0;
  }

  private static boolean isValidISBN13(String isbn) {
    int sum = 0;
    for (int i = 0; i < 13; i++)
      sum += (i % 2 == 0) ? Character.getNumericValue(isbn.charAt(i)) : 3 * Character.getNumericValue(isbn.charAt(i));
    return sum % 10 == 0;
  }

  private static void serializeBooks(String fileName, List<Book> books) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
      oos.writeObject(books.toArray(new Book[0]));
      System.out.println("Serialized " + books.size() + " books to " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void doPart3() {
    System.out.println("Starting Part 3: Deserialization and Interactive Navigation...");
    String[] binaryFiles = {
        "Cartoons_Comics_Books.csv.ser", "Hobbies_Collectibles_Books.csv.ser", "Movies_TV.csv.ser",
        "Music_Radio_Books.csv.ser", "Nostalgia_Eclectic_Books.csv.ser", "Old_Time_Radio.csv.ser",
        "Sports_Sports_Memorabilia.csv.ser", "Trains_Planes_Automobiles.csv.ser"
    };

    Scanner scanner = new Scanner(System.in);
    Book[] currentBooks = null;
    int currentIndex = 0;

    while (true) {
      System.out.println("\n-----------------------------");
      System.out.println("Main Menu");
      System.out.println("-----------------------------");
      System.out.println("v - View the selected file");
      System.out.println("s - Select a file to view");
      System.out.println("x - Exit");
      System.out.print("Enter Your Choice: ");
      String choice = scanner.nextLine().trim().toLowerCase();

      switch (choice) {
        case "v" -> {
          if (currentBooks == null) {
            System.out.println("No file selected. Please select a file first.");
          } else {
            navigateBooks(currentBooks, scanner, currentIndex);
          }
        }
        case "s" -> {
          currentBooks = selectFileToView(binaryFiles, scanner);
          currentIndex = 0;
        }
        case "x" -> {
          System.out.println("Exiting the program. Thank you for using the Bookstore Catalog Management System!");
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private static Book[] selectFileToView(String[] binaryFiles, Scanner scanner) {
    System.out.println("\n-----------------------------");
    System.out.println("File Sub-Menu");
    System.out.println("-----------------------------");

    for (int i = 0; i < binaryFiles.length; i++) {
      System.out.println((i + 1) + " - " + binaryFiles[i]);
    }
    System.out.println("9 - Exit");
    System.out.print("Enter Your Choice: ");

    int fileChoice = Integer.parseInt(scanner.nextLine().trim());
    if (fileChoice == 9)
      return null;
    if (fileChoice < 1 || fileChoice > binaryFiles.length) {
      System.out.println("Invalid choice. Returning to main menu.");
      return null;
    }

    String selectedFile = binaryFiles[fileChoice - 1];
    System.out.println("Loading file: " + selectedFile);
    return deserializeBooks("output/binary_files/" + selectedFile);
  }

  private static Book[] deserializeBooks(String fileName) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
      return (Book[]) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Error loading file: " + fileName);
      e.printStackTrace();
    }
    return null;
  }

  private static void navigateBooks(Book[] books, Scanner scanner, int currentIndex) {
    while (true) {
      System.out.println("\nViewing file. Current record: " + (currentIndex + 1) + " of " + books.length);
      System.out.print(
          "Enter a number for range (positive to move forward, negative to move backward, 0 to return to main menu): ");
      int range = Integer.parseInt(scanner.nextLine().trim());

      if (range == 0) {
        System.out.println("Returning to main menu.");
        return;
      }

      int startIndex = currentIndex;
      int endIndex = currentIndex + range;

      if (endIndex < 0) {
        endIndex = 0;
        System.out.println("BOF has been reached.");
      } else if (endIndex >= books.length) {
        endIndex = books.length - 1;
        System.out.println("EOF has been reached.");
      }

      for (int i = Math.min(startIndex, endIndex); i <= Math.max(startIndex, endIndex); i++) {
        System.out.println(books[i]);
      }

      currentIndex = (range > 0) ? endIndex : startIndex;
    }
  }
}