import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class SudokuGenerator {
    public static final int size = 9;
    public static final int box = 3;
    public static Random rand = new Random();

    public static void main(String[] args) {
        int[][] solution = new int[size][size];

        fillBaseBoard(solution);

        ArrayList<Integer> digits = createDigitList();
        Collections.shuffle(digits);
        remapDigits(solution, digits);

        shuffleRowBands(solution);
        shuffleRowsWithinBands(solution);
        shuffleColumnStacks(solution);
        shuffleColumnsWithinStacks(solution);

        int[][] puzzle = copyBoardNew(solution);
        boolean[][] fixed = new boolean[size][size];

        removeNumbers(puzzle, fixed, 40);

        System.out.println("Sudoku Puzzle:");
        playGame(puzzle, solution, fixed);
    }

    public static void fillBaseBoard(int[][] board) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = (row * box + row / box + col) % size + 1;
            }
        }
    }

    public static ArrayList<Integer> createDigitList() {
        ArrayList<Integer> digits = new ArrayList<Integer>();
        for (int i = 1; i <= size; i++) {
            digits.add(i);
        }
        return digits;
    }

    public static void remapDigits(int[][] board, ArrayList<Integer> digits) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = digits.get(board[row][col] - 1);
            }
        }
    }

    public static void shuffleRowBands(int[][] board) {
        ArrayList<Integer> bands = new ArrayList<Integer>();
        bands.add(0);
        bands.add(1);
        bands.add(2);
        Collections.shuffle(bands);

        int[][] temp = new int[size][size];
        int newRow = 0;

        for (int band : bands) {
            for (int i = 0; i < box; i++) {
                for (int col = 0; col < size; col++) {
                    temp[newRow][col] = board[band * box + i][col];
                }
                newRow++;
            }
        }

        copyBoard(temp, board);
    }

    public static void shuffleRowsWithinBands(int[][] board) {
        for (int band = 0; band < box; band++) {
            ArrayList<Integer> rows = new ArrayList<Integer>();
            rows.add(band * box);
            rows.add(band * box + 1);
            rows.add(band * box + 2);
            Collections.shuffle(rows);

            int[][] temp = new int[box][size];
            for (int i = 0; i < box; i++) {
                for (int col = 0; col < size; col++) {
                    temp[i][col] = board[rows.get(i)][col];
                }
            }

            for (int i = 0; i < box; i++) {
                for (int col = 0; col < size; col++) {
                    board[band * box + i][col] = temp[i][col];
                }
            }
        }
    }

    public static void shuffleColumnStacks(int[][] board) {
        ArrayList<Integer> stacks = new ArrayList<Integer>();
        stacks.add(0);
        stacks.add(1);
        stacks.add(2);
        Collections.shuffle(stacks);

        int[][] temp = new int[size][size];
        int newCol = 0;

        for (int stack : stacks) {
            for (int i = 0; i < box; i++) {
                for (int row = 0; row < size; row++) {
                    temp[row][newCol] = board[row][stack * box + i];
                }
                newCol++;
            }
        }

        copyBoard(temp, board);
    }

    public static void shuffleColumnsWithinStacks(int[][] board) {
        for (int stack = 0; stack < box; stack++) {
            ArrayList<Integer> cols = new ArrayList<Integer>();
            cols.add(stack * box);
            cols.add(stack * box + 1);
            cols.add(stack * box + 2);
            Collections.shuffle(cols);

            int[][] temp = new int[size][box];
            for (int i = 0; i < box; i++) {
                for (int row = 0; row < size; row++) {
                    temp[row][i] = board[row][cols.get(i)];
                }
            }

            for (int i = 0; i < box; i++) {
                for (int row = 0; row < size; row++) {
                    board[row][stack * box + i] = temp[row][i];
                }
            }
        }
    }

    public static void copyBoard(int[][] source, int[][] destination) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                destination[row][col] = source[row][col];
            }
        }
    }

    public static int[][] copyBoardNew(int[][] board) {
        int[][] copy = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                copy[row][col] = board[row][col];
            }
        }
        return copy;
    }

    public static void removeNumbers(int[][] board, boolean[][] fixed, int count) {
        int removed = 0;

        while (removed < count) {
            int row = rand.nextInt(size);
            int col = rand.nextInt(size);

            if (board[row][col] != 0) {
                board[row][col] = 0;
                removed++;
            }
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                fixed[row][col] = board[row][col] != 0;
            }
        }
    }

    public static void playGame(int[][] puzzle, int[][] solution, boolean[][] fixed) {
        Scanner input = new Scanner(System.in);

        while (true) {
            printBoard(puzzle);

            if (isComplete(puzzle)) {
                System.out.println("You solved it!");
                break;
            }

            System.out.print("Enter row (1-9) or 0 to quit: ");
            int row = input.nextInt();

            if (row == 0) {
                System.out.println("Game ended.");
                break;
            }

            System.out.print("Enter column (1-9): ");
            int col = input.nextInt();

            System.out.print("Enter number (1-9): ");
            int num = input.nextInt();

            row--;
            col--;

            if (row < 0 || row >= size || col < 0 || col >= size || num < 1 || num > 9) {
                System.out.println("Invalid input.");
                continue;
            }

            if (fixed[row][col]) {
                System.out.println("You cannot change that cell.");
                continue;
            }

            if (solution[row][col] == num) {
                puzzle[row][col] = num;
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. Try again.");
            }
        }
    }

    public static boolean isComplete(int[][] board) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printBoard(int[][] board) {
        for (int row = 0; row < size; row++) {
            if (row % box == 0) {
                System.out.println("+-------+-------+-------+");
            }

            for (int col = 0; col < size; col++) {
                if (col % box == 0) {
                    System.out.print("| ");
                }

                if (board[row][col] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[row][col] + " ");
                }
            }

            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }
}
