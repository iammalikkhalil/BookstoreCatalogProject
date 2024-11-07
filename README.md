# Book Catalog Processor - Assignment 2 (COMP249)

## Overview

This program processes book records from CSV files, performs syntax and semantic validation, serializes valid records, and provides an interactive navigation interface for users.

- **Part 1**: Validates the syntax of records and categorizes them into genre-specific files.
- **Part 2**: Checks semantics (price, year, ISBN) and serializes valid records.
- **Part 3**: Allows interactive navigation through records in serialized binary files.

## Files and Directories

- **src/main/java**: Contains all source code files.
- **resources/input_files**: Contains input CSV files.
- **resources/output_files**: Stores categorized CSV files and error files.
- **resources/binary_files**: Contains serialized binary files of valid records.

## Running the Program

To run the program, execute `Main.java`. The program will:
1. Process and validate records in `input_files`.
2. Create and store valid and error files in `output_files`.
3. Serialize valid records to `binary_files`.
4. Allow interactive navigation of serialized records.

## Requirements Met

This program meets all specified requirements, including:
- Syntax and semantic validation with custom exceptions.
- Output file generation and error logging.
- Interactive navigation and user interface.

## Author Information

- **Written by**: [Your Name]
- **Student ID**: [Your Student ID]
- **Course**: COMP249, Fall 2024
