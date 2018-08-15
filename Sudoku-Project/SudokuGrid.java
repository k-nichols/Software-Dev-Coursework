//Class Project
//CS371 - Software Development
//
//Authors: McLane, Kurt
//		   Near, Kathleen
//		   Olivas, Tanya
//		   Peterson, Jared

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;

/**
 * The SudokuGrid class creates the GUI grid on which the puzzle will be presented and
 * solved and a "Solve" button to check the solution. The grid consists of an array of
 * JPanels (which make up the bold-lined sections), which contain arrays of JTextFields
 * (which make up the individual boxes). A bank of ready-to-solve puzzles are used to
 * fill the grid.
 **/

final class SudokuGrid extends JPanel {
	
	//Grid variables
	private final JTextField[][] grid;
	private final JPanel gridPanel;
	private final JPanel[][] sectionSquares;
	private String[][] numbersArr;
	
	//Button variables
	private final JPanel buttonPanel;
	private final JButton solveButton;
	
	//Puzzle difficulty variables
	public static boolean easy;
	public static boolean medium;
	public static boolean hard;
	
	/***********************************
	 *  Ready-to-solve Sudoku Puzzles  *
	 ***********************************/
	
	/** 4x4 PUZZLES **/
	// Difficulty: easy //
	private final String[][] fourEasyFill1 = new String[][] {
		{ "1",  "", "3", "4"  },
		{ "3", "4",  "",  "2"  },
		{ "4",  "",  "2",  "1" },
		{ "2",  "1", "", "3"  }
	};
	private final String[][] fourEasyFill2 = new String[][] {
		{ "3",  "4", "1", ""  },
		{ "2", "1",  "",  "4"  },
		{ "",  "3",  "2",  "1" },
		{ "1",  "2", "4", ""  }
	};
	private final String[][] fourEasyFill3 = new String[][] {
		{ "1",  "2", "3", ""  },
		{ "3", "4",  "1",  ""  },
		{ "4",  "3",  "",  "1" },
		{ "2",  "1", "4", ""  }
	};
	private final String[][] fourEasyFill4 = new String[][] {
		{ "3",  "4", "", "2"  },
		{ "2", "1",  "3",  ""  },
		{ "4",  "",  "2",  "1" },
		{ "1",  "", "4", "3"  }
	};
	private final String[][] fourEasyFill5 = new String[][] {
		{ "1",  "", "3", "4"  },
		{ "3", "",  "1",  "2"  },
		{ "2",  "1",  "4",  "" },
		{ "",  "3", "2", "1"  }
	};
	
	// Difficulty: medium //
	private final String[][] fourMediumFill1 = new String[][] {
		{ "",  "", "3", "1"  },
		{ "1", "3",  "",  ""  },
		{ "",  "4",  "",  "3" },
		{ "3",  "", "", "2"  }
	};
	private final String[][] fourMediumFill2 = new String[][] {
		{ "4",  "", "3", ""  },
		{ "", "3",  "",  "4"  },
		{ "2",  "4",  "",  "" },
		{ "3",  "1", "", ""  }
	};
	private final String[][] fourMediumFill3 = new String[][] {
		{ "",  "", "3", "4"  },
		{ "3", "",  "",  "2"  },
		{ "",  "",  "2",  "1" },
		{ "2",  "1", "", ""  }
	};
	private final String[][] fourMediumFill4 = new String[][] {
		{ "2",  "", "1", ""  },
		{ "", "",  "2",  "4"  },
		{ "",  "1",  "",  "2" },
		{ "3",  "", "4", ""  }
	};
	private final String[][] fourMediumFill5 = new String[][] {
		{ "3",  "4", "", ""  },
		{ "2", "1",  "",  ""  },
		{ "4",  "",  "",  "1" },
		{ "",  "2", "4", ""  }
	};
	
	// Difficulty: hard //
	private final String[][] fourHardFill1 = new String[][] {
		{ "1", "", "", ""  },
		{ "3", "", "", ""  },
		{ "", "", "", "3" },
		{ "", "3", "", ""  }
	};
	private final String[][] fourHardFill2 = new String[][] {
		{ "", "", "3", ""  },
		{ "", "3", "", ""  },
		{ "2", "", "", "" },
		{ "", "", "", "2"  }
	};
	private final String[][] fourHardFill3 = new String[][] {
		{ "", "", "", "4"  },
		{ "", "", "", "2"  },
		{ "2", "", "", "" },
		{ "4", "", "", ""  }
	};
	private final String[][] fourHardFill4 = new String[][] {
		{ "1", "", "", ""  },
		{ "", "3", "", ""  },
		{ "", "", "4", "" },
		{ "", "", "1", ""  }
	};
	private final String[][] fourHardFill5 = new String[][] {
		{ "", "", "3", ""  },
		{ "", "4", "", ""  },
		{ "4", "", "", "" },
		{ "", "", "4", ""  }
	};
	
	
	/** 9x9 PUZZLES **/
	// Difficulty: easy //
	private final String[][] nineEasyFill1 = new String[][] {
		{ "", "", "", "", "", "3", "7", "6", "8" },
		{ "6", "1", "", "", "", "8", "9", "", "" },
		{ "5", "8", "", "7", "9", "", "", "4", "" },
		{ "", "", "", "", "", "7", "5", "8", "" },
		{ "3", "", "6", "", "", "", "1", "", "9" },
		{ "", "9", "8", "6", "", "", "", "", "" },
		{ "", "3", "", "", "4", "9", "", "1", "7" },
		{ "", "", "4", "1", "", "", "", "9", "2" },
		{ "9", "6", "1", "3", "", "", "", "", "" }
	};
	private final String[][] nineEasyFill2 = new String[][] {
		{ "", "", "2", "6", "", "1", "", "8", "" },
		{ "", "", "8", "5", "2", "", "4", "6", "" },
		{ "4", "", "", "", "7", "", "", "9", "5" },
		{ "5", "1", "", "", "", "4", "", "", "9" },
		{ "7", "", "", "", "", "", "", "", "2" },
		{ "2", "", "", "1", "", "", "", "3", "6" },
		{ "8", "9", "", "", "1", "", "", "", "3" },
		{ "", "2", "5", "", "9", "8", "7", "", "" },
		{ "", "3", "", "4", "", "5", "9", "", "" }
	};
	private final String[][] nineEasyFill3 = new String[][] {
		{ "9", "1", "8", "", "", "2", "", "", "" },
		{ "", "4", "", "", "3", "", "2", "", "" },
		{ "", "2", "7", "", "5", "", "", "", "6" },
		{ "", "7", "6", "2", "9", "", "3", "4", "" },
		{ "", "8", "", "", "", "", "", "9", "" },
		{ "", "9", "3", "", "1", "8", "6", "5", "" },
		{ "2", "", "", "", "4", "", "9", "8", "" },
		{ "", "", "9", "", "8", "", "", "2", "" },
		{ "", "", "", "1", "", "", "7", "6", "3" }
	};
	private final String[][] nineEasyFill4 = new String[][] {
		{ "", "4", "", "6", "", "3", "", "", "5" },
		{ "8", "", "9", "7", "2", "", "6", "4", "" },
		{ "", "6", "1", "", "", "8", "7", "", "" },
		{ "", "", "", "", "", "", "5", "7", "" },
		{ "", "", "5", "3", "", "1", "4", "", "" },
		{ "", "9", "7", "", "", "", "", "", "" },
		{ "", "", "6", "8", "", "", "2", "9", "" },
		{ "", "7", "8", "", "5", "2", "1", "", "3" },
		{ "9", "", "", "1", "", "4", "", "5", "" }
	};
	private final String[][] nineEasyFill5 = new String[][] {
		{ "9", "2", "", "", "", "", "6", "", "" },
		{ "", "4", "", "", "", "", "", "", "7" },
		{ "1", "", "", "4", "6", "", "2", "5", "8" },
		{ "", "", "", "", "9", "8", "4", "", "5" },
		{ "", "", "7", "3", "", "4", "9", "", "" },
		{ "4", "", "9", "6", "5", "", "", "", "" },
		{ "7", "8", "4", "", "3", "2", "", "", "1" },
		{ "5", "", "", "", "", "", "", "2", "" },
		{ "", "", "6", "", "", "", "", "8", "9" }
	};
	private final String[][] nineEasyFill6 = new String[][] {
		{ "9", "", "", "1", "", "", "7", "8", "" },
		{ "", "", "1", "8", "", "9", "2", "", "6" },
		{ "", "", "", "3", "", "5", "", "9", "1" },
		{ "4", "", "7", "", "", "", "8", "", "5" },
		{ "", "", "", "7", "", "8", "", "", "" },
		{ "6", "", "8", "", "", "", "9", "", "2" },
		{ "8", "9", "", "2", "", "6", "", "", "" },
		{ "5", "", "6", "9", "", "4", "1", "", "" },
		{ "", "1", "3", "", "", "7", "", "", "9" }
	};
	private final String[][] nineEasyFill7 = new String[][] {
		{ "", "", "6", "", "", "7", "2", "8", "" },
		{ "", "", "", "", "6", "1", "3", "", "" },
		{ "", "", "", "9", "8", "3", "", "6", "" },
		{ "4", "", "", "6", "9", "", "", "", "8" },
		{ "", "2", "3", "", "", "", "4", "5", "" },
		{ "8", "", "", "", "3", "5", "", "", "2" },
		{ "", "6", "", "3", "2", "9", "", "", "" },
		{ "", "", "2", "1", "7", "", "", "", "" },
		{ "", "3", "7", "8", "", "", "1", "", "" }
	};
	private final String[][] nineEasyFill8 = new String[][] {
		{ "5", "", "", "3", "", "", "", "1", "7" },
		{ "3", "", "", "", "", "1", "4", "", "5" },
		{ "7", "", "4", "", "8", "2", "", "", "6" },
		{ "2", "3", "1", "", "6", "", "", "7", "" },
		{ "", "", "", "", "", "", "", "", "" },
		{ "", "4", "", "", "9", "", "1", "6", "3" },
		{ "1", "", "", "6", "2", "", "9", "", "4" },
		{ "4", "", "5", "7", "", "", "", "", "2" },
		{ "6", "2", "", "", "", "3", "", "", "1" }
	};
	private final String[][] nineEasyFill9 = new String[][] {
		{ "6", "", "", "", "5", "", "", "4", "1" },
		{ "", "1", "", "", "3", "4", "9", "", "" },
		{ "", "", "5", "1", "", "7", "", "", "6" },
		{ "", "9", "3", "", "", "", "6", "", "" },
		{ "", "", "", "5", "6", "3", "", "", "" },
		{ "", "", "4", "", "", "", "5", "8", "" },
		{ "3", "", "", "8", "", "2", "7", "", "" },
		{ "", "", "2", "4", "7", "", "", "5", "" },
		{ "4", "7", "", "", "9", "", "", "", "8" }
	};
	private final String[][] nineEasyFill10 = new String[][] {
		{ "9", "8", "", "5", "", "7", "1", "", "4" },
		{ "", "", "", "", "1", "", "", "", "" },
		{ "6", "2", "", "", "", "", "8", "", "" },
		{ "1", "", "", "", "", "2", "", "3", "9" },
		{ "5", "", "", "1", "6", "4", "", "", "7" },
		{ "2", "7", "", "9", "", "", "", "", "1" },
		{ "", "", "4", "", "", "", "", "9", "8" },
		{ "", "", "", "", "7", "", "", "", "" },
		{ "3", "", "5", "6", "", "8", "", "1", "2" }
	};
	
	// Difficulty: medium //
	private final String[][] nineMediumFill1 = new String[][] {
		{ "", "", "", "", "", "7", "", "5", "" },
		{ "", "", "6", "3", "8", "", "9", "7", "" },
		{ "", "9", "", "5", "6", "", "", "", "4" },
		{ "", "", "5", "", "", "", "4", "", "" },
		{ "", "7", "4", "", "1", "", "2", "8", "" },
		{ "", "", "8", "", "", "", "1", "", "" },
		{ "3", "", "", "", "4", "8", "", "2", "" },
		{ "", "5", "1", "", "7", "3", "6", "", "" },
		{ "", "2", "", "1", "", "", "", "", "" }
	};
	private final String[][] nineMediumFill2 = new String[][] {
		{ "1", "3", "", "", "7", "", "", "", "" },
		{ "9", "", "2", "8", "", "", "", "1", "3" },
		{ "", "", "", "", "", "9", "", "", "" },
		{ "3", "", "", "9", "4", "", "", "7", "2" },
		{ "", "", "", "6", "5", "7", "", "", "" },
		{ "6", "7", "", "", "2", "8", "", "", "1" },
		{ "", "", "", "4", "", "", "", "", "" },
		{ "4", "1", "", "", "", "5", "3", "", "7" },
		{ "", "", "", "", "9", "", "", "2", "4" }
	};
	private final String[][] nineMediumFill3 = new String[][] {
		{ "9", "", "", "7", "4", "5", "", "", "" },
		{ "", "", "", "", "", "", "2", "", "" },
		{ "6", "3", "", "1", "", "", "", "", "" },
		{ "", "8", "", "4", "2", "", "", "6", "5" },
		{ "", "2", "7", "", "8", "", "4", "3", "" },
		{ "4", "6", "", "", "1", "3", "", "7", "" },
		{ "", "", "", "", "", "4", "", "2", "7" },
		{ "", "", "3", "", "", "", "", "", "" },
		{ "", "", "", "8", "6", "1", "", "", "3" }
	};
	private final String[][] nineMediumFill4 = new String[][] {
		{ "", "2", "8", "", "", "", "", "9", "4" },
		{ "", "", "1", "", "", "", "", "", "" },
		{ "3", "4", "", "", "", "8", "", "2", "6" },
		{ "7", "1", "", "9", "", "", "", "3", "" },
		{ "", "5", "", "3", "", "2", "", "7", "" },
		{ "", "3", "", "", "", "1", "", "4", "8" },
		{ "6", "8", "", "5", "", "", "", "1", "9" },
		{ "", "", "", "", "", "", "4", "", "" },
		{ "4", "9", "", "", "", "", "8", "5", "" }
	};
	private final String[][] nineMediumFill5 = new String[][] {
		{ "4", "", "", "", "5", "", "", "", "9" },
		{ "", "1", "8", "7", "", "", "", "", "3" },
		{ "2", "", "", "", "", "", "", "4", "" },
		{ "6", "4", "", "", "", "7", "", "1", "" },
		{ "", "", "2", "", "1", "", "4", "", "" },
		{ "", "9", "", "4", "", "", "", "7", "5" },
		{ "", "5", "", "", "", "", "", "", "2" },
		{ "3", "", "", "", "", "1", "9", "5", "" },
		{ "8", "", "", "", "6", "", "", "", "1" }
	};
	private final String[][] nineMediumFill6 = new String[][] {
		{ "", "", "", "", "8", "7", "3", "6", "" },
		{ "", "4", "", "", "3", "9", "", "8", "" },
		{ "", "", "", "", "2", "", "", "", "1" },
		{ "", "", "1", "", "", "", "6", "", "9" },
		{ "", "", "", "8", "", "1", "", "", "" },
		{ "4", "", "7", "", "", "", "8", "", "" },
		{ "8", "", "", "", "9", "", "", "", "" },
		{ "", "5", "", "3", "6", "", "", "4", "" },
		{ "", "9", "4", "7", "1", "", "", "", "" }
	};
	private final String[][] nineMediumFill7 = new String[][] {
		{ "9", "", "", "5", "", "", "", "6", "" },
		{ "2", "3", "8", "", "", "", "", "", "" },
		{ "", "", "", "", "1", "3", "9", "", "" },
		{ "", "", "", "", "5", "7", "", "8", "" },
		{ "5", "", "", "2", "", "8", "", "", "9" },
		{ "", "7", "", "6", "3", "", "", "", "" },
		{ "", "", "4", "9", "6", "", "", "", "" },
		{ "", "", "", "", "", "", "8", "9", "5" },
		{ "", "5", "", "", "", "1", "", "", "3" }
	};
	private final String[][] nineMediumFill8 = new String[][] {
		{ "", "", "6", "5", "", "", "", "7", "" },
		{ "", "", "7", "", "8", "1", "3", "", "6" },
		{ "2", "4", "", "9", "", "", "", "", "" },
		{ "", "", "5", "", "", "", "", "8", "" },
		{ "", "7", "", "6", "9", "8", "", "3", "" },
		{ "", "8", "", "", "", "", "9", "", "" },
		{ "", "", "", "", "", "9", "", "6", "2" },
		{ "7", "", "9", "8", "6", "", "5", "", "" },
		{ "", "6", "", "", "", "3", "8", "", "" }
	};
	private final String[][] nineMediumFill9 = new String[][] {
		{ "", "", "4", "", "6", "7", "", "1", "" },
		{ "1", "", "", "4", "", "", "", "9", "" },
		{ "", "", "", "", "1", "", "", "", "" },
		{ "7", "2", "", "", "", "", "9", "4", "1" },
		{ "", "", "3", "", "", "", "7", "", "" },
		{ "6", "4", "9", "", "", "", "", "2", "8" },
		{ "", "", "", "", "8", "", "", "", "" },
		{ "", "9", "", "", "", "5", "", "", "2" },
		{ "", "6", "", "9", "2", "", "4", "", "" }
	};
	private final String[][] nineMediumFill10 = new String[][] {
		{ "", "", "", "", "", "5", "4", "8", "" },
		{ "", "", "", "", "6", "", "", "9", "" },
		{ "5", "1", "2", "8", "9", "", "3", "", "" },
		{ "", "", "7", "", "", "", "", "", "" },
		{ "", "6", "", "2", "", "7", "", "1", "" },
		{ "", "", "", "", "", "", "6", "", "" },
		{ "", "", "9", "", "3", "6", "2", "5", "8" },
		{ "", "5", "", "", "7", "", "", "", "" },
		{ "", "8", "6", "1", "", "", "", "", "" }
	};
	
	// Difficulty: hard //
	private final String[][] nineHardFill1 = new String[][] {
		{ "", "", "4", "", "8", "", "", "7", "" },
		{ "7", "", "", "4", "", "2", "", "", "5" },
		{ "1", "", "", "6", "", "", "", "", "" },
		{ "", "", "9", "", "4", "", "", "8", "" },
		{ "", "", "8", "", "5", "", "3", "", "" },
		{ "", "5", "", "", "6", "", "2", "", "" },
		{ "", "", "", "", "", "6", "", "", "8" },
		{ "3", "", "", "8", "", "4", "", "", "6" },
		{ "", "7", "", "", "2", "", "9", "", "" }
	};
	private final String[][] nineHardFill2 = new String[][] {
		{ "1", "8", "9", "7", "", "", "", "", "2" },
		{ "", "", "7", "", "2", "1", "", "5", "" },
		{ "6", "", "", "", "", "", "", "", "" },
		{ "", "", "", "4", "9", "", "7", "", "" },
		{ "", "", "", "", "6", "", "", "", "" },
		{ "", "", "3", "", "1", "7", "", "", "" },
		{ "", "", "", "", "", "", "", "", "6" },
		{ "", "5", "", "3", "7", "", "1", "", "" },
		{ "9", "", "", "", "", "6", "3", "8", "4" }
	};
	private final String[][] nineHardFill3 = new String[][] {
		{ "", "", "", "3", "", "", "6", "", "" },
		{ "7", "8", "", "6", "", "", "", "5", "" },
		{ "", "", "", "", "2", "9", "8", "", "" },
		{ "3", "", "6", "", "7", "", "", "", "9" },
		{ "", "", "", "9", "", "2", "", "", "" },
		{ "5", "", "", "", "6", "", "7", "", "1" },
		{ "", "", "1", "2", "9", "", "", "", "" },
		{ "", "3", "", "", "", "8", "", "2", "6" },
		{ "", "", "4", "", "", "1", "", "", "" }
	};
	private final String[][] nineHardFill4 = new String[][] {
		{ "", "", "7", "", "", "", "", "6", "" },
		{ "6", "", "", "", "3", "", "", "5", "" },
		{ "1", "2", "8", "", "6", "", "7", "", "" },
		{ "", "", "", "", "", "", "2", "3", "" },
		{ "", "", "9", "3", "", "1", "6", "", "" },
		{ "", "6", "1", "", "", "", "", "", "" },
		{ "", "", "6", "", "4", "", "3", "9", "7" },
		{ "", "5", "", "", "9", "", "", "", "6" },
		{ "", "7", "", "", "", "", "4", "", "" }
	};
	private final String[][] nineHardFill5 = new String[][] {
		{ "", "", "", "", "", "3", "", "", "" },
		{ "3", "", "9", "", "", "2", "", "", "4" },
		{ "6", "", "", "", "4", "", "", "", "9" },
		{ "", "", "7", "1", "", "", "", "9", "" },
		{ "", "", "2", "4", "", "9", "8", "", "" },
		{ "", "4", "", "", "", "6", "3", "", "" },
		{ "5", "", "", "", "1", "", "", "", "3" },
		{ "4", "", "", "5", "", "", "9", "", "6" },
		{ "", "", "", "3", "", "", "", "", "" }
	};
	private final String[][] nineHardFill6 = new String[][] {
		{ "7", "", "", "", "3", "5", "1", "", "" },
		{ "", "3", "", "", "", "", "6", "", "" },
		{ "", "", "1", "", "7", "8", "", "", "" },
		{ "3", "", "", "7", "", "4", "5", "", "2" },
		{ "", "", "", "", "", "", "", "", "" },
		{ "8", "", "7", "9", "", "3", "", "", "6" },
		{ "", "", "", "2", "4", "", "7", "", "" },
		{ "", "", "6", "", "", "", "", "5", "" },
		{ "", "", "4", "5", "6", "", "", "", "1" }
	};
	private final String[][] nineHardFill7 = new String[][] {
		{ "4", "", "", "6", "", "", "", "7", "" },
		{ "", "", "8", "", "", "", "3", "", "6" },
		{ "", "", "", "", "", "", "9", "5", "4" },
		{ "1", "", "", "4", "5", "", "", "2", "" },
		{ "", "", "", "3", "", "1", "", "", "" },
		{ "", "3", "", "", "8", "2", "", "", "1" },
		{ "7", "5", "1", "", "", "", "", "", "" },
		{ "8", "", "3", "", "", "", "4", "", "" },
		{ "", "2", "", "", "", "9", "", "", "5" }
	};
	private final String[][] nineHardFill8 = new String[][] {
		{ "", "", "3", "5", "8", "", "", "", "" },
		{ "5", "", "", "6", "1", "", "", "", "" },
		{ "", "2", "", "", "", "", "7", "", "" },
		{ "8", "1", "", "2", "", "", "", "", "" },
		{ "9", "", "6", "", "", "", "5", "", "3" },
		{ "", "", "", "", "", "4", "", "1", "8" },
		{ "", "", "8", "", "", "", "", "4", "" },
		{ "", "", "", "", "4", "9", "", "", "2" },
		{ "", "", "", "", "6", "5", "8", "", "" }
	};
	private final String[][] nineHardFill9 = new String[][] {
		{ "", "9", "", "", "", "", "1", "4", "" },
		{ "", "", "", "", "6", "9", "5", "", "2" },
		{ "", "", "", "1", "", "", "", "", "" },
		{ "", "", "7", "", "2", "", "6", "", "5" },
		{ "", "3", "", "", "", "", "", "9", "" },
		{ "2", "", "6", "", "9", "", "4", "", "" },
		{ "", "", "", "", "", "2", "", "", "" },
		{ "4", "", "8", "3", "1", "", "", "", "" },
		{ "", "2", "3", "", "", "", "", "8", "" }
	};
	private final String[][] nineHardFill10 = new String[][] {
		{ "", "", "", "", "5", "1", "", "", "2" },
		{ "", "", "", "2", "", "", "", "", "8" },
		{ "", "3", "1", "6", "", "", "9", "", "" },
		{ "4", "2", "", "", "3", "", "", "7", "" },
		{ "", "", "", "1", "", "5", "", "", "" },
		{ "", "5", "", "", "4", "", "", "9", "3" },
		{ "", "", "7", "", "", "3", "6", "4", "" },
		{ "3", "", "", "", "", "9", "", "", "" },
		{ "6", "", "", "5", "1", "", "", "", "" }
	};
	/***********************************
	 *    End Ready-to-solve Puzzles   *
	 ***********************************/
	
	/**
	 * Creates and formats a grid of a given size with sections of boxes, pre-filled and ready for input
	 * and a "Solve" button that gives feedback on the validity of the solution
	 * @param size is the desired dimension of the puzzle
	 * 
	 * Authors: Kathleen Near, Tanya Olivas, Jared Peterson, & Kurt McLane
	 **/
	SudokuGrid(int size) {
		
		//Variable instantiations
		this.grid = new JTextField[size][size];
		this.gridPanel = new JPanel();
		this.buttonPanel = new JPanel();
		
		//Creates a thin border for each box and a thick border for each section
		Border gridBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		Border sectionBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		//Sets the individual box size to 60x60 pixels & the section size to a dynamic number of boxes based upon the dimensions
		Dimension fieldSize = new Dimension(60, 60);
		int sectionSize = (int) Math.sqrt(size);
		
		//Generates a random puzzle of chosen size and difficulty (default: medium 9x9 puzzle)
		generateRandomPuzzle(size);
		
		
		// GRID GENERATION //
		//Loop initiates and formats an array of text fields that will make up the grid
		for (int row = 0; row < size; ++row) {
			for (int col = 0; col < size; ++col) {
				
				//Creates a text field for each "box" with filtered input
				JTextField field = new JTextField();
				PlainDocument pDoc = (PlainDocument) field.getDocument();
				pDoc.setDocumentFilter(new FieldFilter(size));
				
			    //If there is a pre-set value for the given "box", display value and disallow editing
				if(!numbersArr[row][col].isEmpty()) {
					field.setEditable(false);
					field.setText(numbersArr[row][col]);
				}
				grid[row][col] = field;
				
				//Format each box (thin border, centered text, etc.)
				field.setBorder(gridBorder);
				field.setFont(SudokuJFrame.ARIAL);
				field.setHorizontalAlignment(JTextField.CENTER);
				field.setPreferredSize(fieldSize);
				
				//Replaces content upon keypressed to allow for only one number at a time
				field.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyPressed(java.awt.event.KeyEvent evt) {
		            	field.setText("");
		            }
	        	});
			}
		}
		//Creates a certain number of sections depending upon the grid size (4 for a 4x4 grid, 9 for a 9x9 grid)
		this.sectionSquares = new JPanel[sectionSize][sectionSize];
		this.gridPanel.setLayout(new GridLayout(sectionSize, sectionSize));
		
		//Loop creates a panel to hold each section depending upon size
		for (int row = 0; row < sectionSize; ++row) {
			for (int col = 0; col < sectionSize; ++col) {
				
				//Creates a panel to contain the sections in grid-layout
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(sectionSize, sectionSize));
				
				//Format each section to be bordered by thick lines
				panel.setBorder(sectionBorder);
				
				//Add sections to the panel
				sectionSquares[row][col] = panel;
				gridPanel.add(panel);
			}
		}
		//Fills the grid with the correct number of boxes based upon size (either 81 for 9x9, or 16 for 4x4)
		for (int y = 0; y < size; ++y) {
			for (int x = 0; x < size; ++x) {
				int sqX = x / sectionSize;
				int sqY = y / sectionSize;
				sectionSquares[sqY][sqX].add(grid[y][x]);
			}
		}
		
		// SOLVE BUTTON //
		//Creates a "Solve" button of static dimensions and defined font size
		this.solveButton = new JButton("Solve");
		solveButton.setPreferredSize(new Dimension(125, 40));
		solveButton.setFont(SudokuJFrame.ARIAL_SM);
		solveButton.setFocusable(false);
		
		//Action listener that checks the solution and gives the user feedback on whether or not the puzzle has been solved.
		//@param e is the click event 
		solveButton.addActionListener((ActionEvent e) -> {
			for (int row = 0; row < size; ++row) {
				for (int col = 0; col < size; ++col) {
					numbersArr[row][col] =  grid[row][col].getText();
				}
			}
			if(SolutionChecker.inputIsValid(numbersArr, size)) {
				//Do when solution is correct
				JOptionPane.showMessageDialog(null, "Congratulations! Your solution is correct!");
			}
			else {
				//Do when solution is incorrect
				JOptionPane.showMessageDialog(null, "Sorry, your solution is incorrect. Try again.");
			}
	      });
		
		
		//ADD ALL ITEMS
		//Adds all items to the layout
		this.setLayout(new BorderLayout());
		this.add(gridPanel, BorderLayout.NORTH);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.buttonPanel.add(solveButton, BorderLayout.CENTER);
		buttonPanel.add(Box.createRigidArea(new Dimension(15,0)));
	}
	
	/**
	 * Generates a random puzzle for the given size and difficulty
	 * @param size is the desired dimension of the puzzle
	 * 
	 * Authors: Tanya Olivas & Jared Peterson
	 **/
	private void generateRandomPuzzle(int size) {
		
		//Create a random number generator
		Random randomNumberGenerator = new Random();
		
		//4x4 Puzzle
		if(size == 4) {
			//Generate a random number between 1 & 5
			int randomNum = randomNumberGenerator.nextInt(5) + 1;
			
			//Difficulty is set to easy
			if(easy == true) {
				//Set puzzle contents to the associated ready-to-solve puzzle
				switch(randomNum) {
					case 1: 
						numbersArr = this.fourEasyFill1;
						break;
					
					case 2: 
						numbersArr = this.fourEasyFill2;
						break;
					
					case 3: 
						numbersArr = this.fourEasyFill3;
						break;
					
					case 4: 
						numbersArr = this.fourEasyFill4;
						break;
					
					case 5: 
						numbersArr = this.fourEasyFill5;
						break;
						
					default:
						numbersArr = this.fourEasyFill5;
						break;
				}
			//Difficulty is set to medium
			}else if(medium == true) {
				
                switch(randomNum) {
					case 1: 
						numbersArr = this.fourMediumFill1;
						break;
					
					case 2: 
						numbersArr = this.fourMediumFill2;
						break;
					
					case 3: 
						numbersArr = this.fourMediumFill3;
						break;
					
					case 4: 
						numbersArr = this.fourMediumFill4;
						break;
					
					case 5: 
						numbersArr = this.fourMediumFill5;
						break;
						
					default:
						numbersArr = this.fourMediumFill5;
						break;
				}
            //Difficulty is set to hard
			}else if(hard == true) {
				
                switch(randomNum) {
					case 1: 
						numbersArr = this.fourHardFill1;
						break;
					
					case 2: 
						numbersArr = this.fourHardFill2;
						break;
					
					case 3: 
						numbersArr = this.fourHardFill3;
						break;
					
					case 4: 
						numbersArr = this.fourHardFill4;
						break;
					
					case 5: 
						numbersArr = this.fourHardFill5;
						break;
						
					default:
						numbersArr = this.fourHardFill5;
						break;
				}
            //Difficulty in neither of these (should never occur)
			}else {
				numbersArr = this.fourHardFill5;
			}
		
		//9x9 Puzzle
		}else if(size == 9) {
			//Generate a random number between 1 & 10
			int randomNum = randomNumberGenerator.nextInt(10) + 1;
			
			//Difficulty is set to easy
			if(easy == true) {
				//Set puzzle contents to the associated ready-to-solve puzzle
                switch(randomNum) {
					case 1: 
						numbersArr = this.nineEasyFill1;
						break;
					
					case 2: 
						numbersArr = this.nineEasyFill2;
						break;
					
					case 3: 
						numbersArr = this.nineEasyFill3;
						break;
					
					case 4: 
						numbersArr = this.nineEasyFill4;
						break;
					
					case 5: 
						numbersArr = this.nineEasyFill5;
						break;
					
					case 6: 
						numbersArr = this.nineEasyFill6;
						break;
					
					case 7: 
						numbersArr = this.nineEasyFill7;
						break;
					
					case 8: 
						numbersArr = this.nineEasyFill8;
						break;
					
					case 9: 
						numbersArr = this.nineEasyFill9;
						break;
					
					case 10: 
						numbersArr = this.nineEasyFill10;
						break;
						
					default:
						numbersArr = this.nineEasyFill10;
						break;
				}
            //Difficulty is set to medium
			}else if(medium == true) {
				
                switch(randomNum) {
					case 1: 
						numbersArr = this.nineMediumFill1;
						break;
					
					case 2: 
						numbersArr = this.nineMediumFill2;
						break;
					
					case 3: 
						numbersArr = this.nineMediumFill3;
						break;
					
					case 4: 
						numbersArr = this.nineMediumFill4;
						break;
					
					case 5: 
						numbersArr = this.nineMediumFill5;
						break;
					
					case 6: 
						numbersArr = this.nineMediumFill6;
						break;
					
					case 7: 
						numbersArr = this.nineMediumFill7;
						break;
					
					case 8: 
						numbersArr = this.nineMediumFill8;
						break;
					
					case 9: 
						numbersArr = this.nineMediumFill9;
						break;
					
					case 10: 
						numbersArr = this.nineMediumFill10;
						break;
						
					default:
						numbersArr = this.nineMediumFill10;
						break;
				}
    		//Difficulty is set to hard
			}else if(hard == true) {
				
                switch(randomNum) {
					case 1: 
						numbersArr = this.nineHardFill1;
						break;
					
					case 2: 
						numbersArr = this.nineHardFill2;
						break;
					
					case 3: 
						numbersArr = this.nineHardFill3;
						break;
					
					case 4: 
						numbersArr = this.nineHardFill4;
						break;
					
					case 5: 
						numbersArr = this.nineHardFill5;
						break;
					
					case 6: 
						numbersArr = this.nineHardFill6;
						break;
					
					case 7: 
						numbersArr = this.nineHardFill7;
						break;
					
					case 8: 
						numbersArr = this.nineHardFill8;
						break;
					
					case 9: 
						numbersArr = this.nineHardFill9;
						break;
					
					case 10: 
						numbersArr = this.nineHardFill10;
						break;
						
					default:
						numbersArr = this.nineHardFill10;
						break;
				}
            //Difficulty in neither of these (should never occur)
			}else {
				numbersArr = this.nineHardFill10;
			}
		//Size is neither 4x4 nor 9x9 (should never occur)
		}else {
			numbersArr = this.nineHardFill10;
		}
	}
}
