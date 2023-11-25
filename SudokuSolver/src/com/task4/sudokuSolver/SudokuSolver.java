package com.task4.sudokuSolver;

import java.util.Scanner;

public class SudokuSolver {
	private static final Scanner sc = new Scanner(System.in);
	private static final int SIZE = 9;

	public static void main(String[] args) {
		int[][] sudokuGrid = getInitialGrid();

		System.out.println("Unsolved Sudoku Puzzle:");
		printSudokuGrid(sudokuGrid);

		if (solveSudoku(sudokuGrid)) {
			System.out.println("\nSolved Sudoku Puzzle:");
			printSudokuGrid(sudokuGrid);
		} else {
			System.out.println("No solution exists for the given Sudoku puzzle.");
		}

		sc.close();
	}

	private static int[][] getInitialGrid() {
		int[][] sudokuGrid = new int[SIZE][SIZE];

		System.out.println("Enter the unsolved Sudoku puzzle (use 0 for empty spaces and -1 to end input):");

		for (int i = 0; i < SIZE; i++) {
			String inputLine = sc.nextLine().trim();

			if (inputLine.equals("-1")) {
				return sudokuGrid; // End input
			}

			char[] inputChars = inputLine.toCharArray();

			for (int j = 0; j < Math.min(SIZE, inputChars.length); j++) {
				if (Character.isDigit(inputChars[j])) {
					sudokuGrid[i][j] = Character.getNumericValue(inputChars[j]);
				} else {
					j--; // Skip non-digit characters
				}
			}
		}

		return sudokuGrid;
	}

	private static boolean solveSudoku(int[][] grid) {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (grid[row][col] == 0) {
					for (int num = 1; num <= SIZE; num++) {
						if (isValidMove(grid, row, col, num)) {
							grid[row][col] = num;

							if (solveSudoku(grid)) {
								return true;
							}

							grid[row][col] = 0; // Backtrack
						}
					}

					return false; // No valid number found for this cell
				}
			}
		}

		return true; // The puzzle is solved
	}

	private static boolean isValidMove(int[][] grid, int row, int col, int num) {
		// Check if 'num' is not present in the current row and column
		for (int x = 0; x < SIZE; x++) {
			if (grid[row][x] == num || grid[x][col] == num) {
				return false;
			}
		}

		// Check if 'num' is not present in the current 3x3 subgrid
		int subgridStartRow = row - row % 3;
		int subgridStartCol = col - col % 3;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[subgridStartRow + i][subgridStartCol + j] == num) {
					return false;
				}
			}
		}

		return true;
	}

	private static void printSudokuGrid(int[][] grid) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
//				System.out.print(grid[i][j] + " ");
				if (grid[i][j] != 0) {
					System.out.print(grid[i][j] + " ");
				} else {
					System.out.print("* ");
				}
			}
			System.out.println();
		}
	}
}
