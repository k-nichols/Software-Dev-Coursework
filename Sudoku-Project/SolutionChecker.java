//Class Project
//CS371 - Software Development
//
//Authors: McLane, Kurt
//		   Near, Kathleen
//		   Olivas, Tanya
//		   Peterson, Jared

import java.util.HashSet;
import java.util.Set;

/**
 * The SudokuGrid class creates the GUI grid on which the puzzle will be presented and
 * The SolutionChecker class applies the rules of Sudoku:
 * 	- Each box can contain only one number 1 to size
 * 	- Each number can only appear once per row, column, and section
 * and returns whether or not the solution is valid (true if valid, false if not)
 **/

public class SolutionChecker{
	
	/**
     * Translates a Sudoku grid's content into a 2-D integer array, then utilizes the
     * solutionIsCorrect method to verify the solution with respect to Sudoku's rules.
     * @param gridContents is the contents of the grid's text fields
     * @param puzzleSize is the size of the grid (length and width in text field boxes)
     * Returns true if the solution is correct, otherwise returns false
     * 
     * Author: Jared Peterson
     **/
	public static boolean inputIsValid(String gridContents[][], int puzzleSize) {
		
		//Creates 2-D integer array to store the solution
		int[][] intGridContents = new int [puzzleSize][puzzleSize];
		
		//Converts the text field strings to integers
		for (int row = 0; row < puzzleSize; ++row) {
			for (int col = 0; col < puzzleSize; ++col) {
				try {
					intGridContents[row][col] = Integer.parseInt(gridContents[row][col]);
				//Returns false if the solution contains any non-integer input or empty field(s)
				}catch(Exception e) {
					return false;
				}
			}
		}
		//Cross-checks each entry of the solution and returns the overall validity
		if(solutionIsValid(intGridContents, puzzleSize)) {
			return true;
		}
		return false;
	}
	
	/**
     * Checks the validity of the assortment of numbers in each grid, column, and section
     * @param intGridContents is the contents of the Sudoku grid in integer form
     * @param puzzleSize is the size of the grid (in text field boxes)
     * Returns true if the solution is correct, and false if incorrect
     * 
     * Author: Jared Peterson
     **/
	public static boolean solutionIsValid(int intGridContents[][], int puzzleSize) {
		
		//Boolean variable to store the result
	    boolean isSolved = false;
	    
	    //Traverses the entire 2-D array and checks each number's placement 
	    //(in respect to the rest of its row, column, and section)
		for (int row = 0; row < puzzleSize; ++row) {
			for (int col = 0; col < puzzleSize; ++col) {
				
				if( checkRow( intGridContents, row, puzzleSize )
					&& checkCol( intGridContents, col, puzzleSize )
					&& checkSection( intGridContents, row, col, puzzleSize )) {
					isSolved = true;
				}else {
					isSolved = false;
					break;
				}
			}
			if(isSolved == false) {
				break;
			}
		}
		return isSolved;
	}

	/**
     * Verifies that all numbers are present in the given row (no duplicates or empty fields)
     * @param intGridContents is the contents of the grid in integer form
     * @param row is the row being checked
     * @param puzzleSize is the size of the grid (in text field boxes)
     * returns true if all of the numbers 1-puzzleSize are present in the given row, otherwise returns false
     * 
     * Author: Jared Peterson
     **/
	protected static boolean checkRow(int intGridContents[][], int row, int puzzleSize) {
		
		//Creates a set of integers from 1 to puzzleSize
		Set<Integer> numSet = new HashSet<>();
		for(int i = 1; i <= puzzleSize; i++) {
			numSet.add(i);
		}
		int temp;
		//Checks the given column for validity (no empty fields or duplicates)
		for( int col = 0; col < puzzleSize; col++ ) {
			 if(numSet.isEmpty()) {
				 return true;
			 }
	         temp = intGridContents[row][col];
	         if(numSet.contains(temp)) {
	        	 numSet.remove(temp);
	         }else {
	        	 return false;
	         }
		}
		return true;
	}
	
	/**
     * Verifies that all numbers are present in the given column (no duplicates or empty fields)
     * @param intGridContents is the contents of the grid in integer form
     * @param col is the column being checked
     * @param puzzleSize is the size of the grid (in text field boxes)
     * returns true if all of the numbers 1-puzzleSize are present in the given column, otherwise returns false
     * 
     * Author: Jared Peterson
     **/
	protected static boolean checkCol(int intGridContents[][], int col, int puzzleSize) {
		
		//Creates a set of integers from 1 to puzzleSize
		Set<Integer> numSet = new HashSet<>();
		for(int i = 1; i <= puzzleSize; i++) {
			numSet.add(i);
		}
		int temp;
		//Checks given row for validity (no empty fields or duplicates)
		for( int row = 0; row < puzzleSize; row++ ) {
			if(numSet.isEmpty()) {
				return true;
			}
	        temp = intGridContents[row][col];
	        if(numSet.contains(temp)) {
	        	numSet.remove(temp);
	        }else {
	        	return false;
	        }
		}
	    return true;
	}
	
	/**
     * Verifies that all numbers are present in the box (no duplicates or empty fields)
     * @param intGridContents is the contents of the grid in integer form
     * @param row is the row of the box that is being checked
     * @param col is the column of the box that is being checked
     * @param puzzleSize is the size of the grid (in text field boxes)
     * returns true if all of the numbers 1-puzzleSize are present in the given box, otherwise returns false
     * 
     * Author: Jared Peterson
     **/
	protected static boolean checkSection(int intGridContents[][], int row, int col, int puzzleSize) {
		
		//Uses integer division to place row and col to the beginning of the box being checked
	    row = (int)(row / Math.sqrt(puzzleSize)) * (int)Math.sqrt(puzzleSize);
	    col = (int)(col / Math.sqrt(puzzleSize)) * (int)Math.sqrt(puzzleSize);
	    
	    //Creates a set of integers from 1 to puzzleSize
	    Set<Integer> numSet = new HashSet<>();
		for(int i = 1; i <= puzzleSize; i++) {
		   numSet.add(i);
		}
	    int temp;
	    //Checks given box for validity (no empty fields or duplicates)
	    for( int r = 0; r < Math.sqrt(puzzleSize); r++ ) {
	       for( int c = 0; c < Math.sqrt(puzzleSize); c++ ) {
	          if(numSet.isEmpty()) {
	             return true;
	          }
	          temp = intGridContents[row+r][col+c];
	          if(numSet.contains(temp)) {
	             numSet.remove(temp);
	          }
	          else {
	             return false;
	          }
	       }
	    }
	    return true ;
	}
}
