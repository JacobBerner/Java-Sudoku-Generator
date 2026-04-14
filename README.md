APCSA Sudoku Generator and Game in Java

Jacob Berner

## Project Description
This project is a Java console application that generates a valid 9x9 Sudoku board and also provides a playable Sudoku game. The generated board follows all Sudoku rules: each row, column, and 3x3 subgrid contains the numbers 1 through 9 exactly once.

How to Run
1. Open the file in a Java IDE or terminal.
2. Compile the program:
   javac SudokuGenerator.java
3. Run the program:
   java SudokuGenerator
4. A Sudoku puzzle will appear in the console, and the user can begin playing.

How the Sudoku Board is Generated
The program first creates a valid Sudoku solution using a mathematical pattern. It then randomizes the board by shuffling digits, rows, and columns while maintaining all Sudoku rules.

Features
- Generates a valid solved Sudoku board
- Uses a 2D array to store the board
- Uses an ArrayList to randomize digits
- Prints the board in a clear format

Bonus Features
- Removes numbers to create a playable puzzle
- Allows user input for row, column, and number
- Checks whether the input is correct
- Prevents changes to fixed cells
- Continues until the puzzle is solved or the user quits

Files Included
- SudokuGenerator.java
- README.md
- DesignDocument.pdf

## Author
Jacob Berner
