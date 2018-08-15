//Class Project
//CS371 - Software Development
//
//Authors: McLane, Kurt
//		   Near, Kathleen
//		   Olivas, Tanya
//		   Peterson, Jared

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;

/**
 * The SudokuJFrame class creates a JFrame to hold the Sudoku grid and a menu with the following options:
 * 	- Options (4x4 or 9x9)					determines the size of the Sudoku puzzle
 * 	- Difficulty (easy, medium, or hard)	determines the difficulty of the given size's puzzle
 * 	- "Next Puzzle" button					generates a new puzzle for the user from a bank of puzzles
 **/

public class SudokuJFrame {
	
	//Global variables
	private final JFrame frame = new JFrame("Sudoku");
	private SudokuGrid grid;
	private int puzzleSize;
	public static final Font ARIAL = new Font("Arial", Font.CENTER_BASELINE, 22);
	public static final Font ARIAL_SM = new Font("Arial", Font.CENTER_BASELINE, 15);
	
	/**
	 * Constructor
	 * Builds the GUI: menu, grid, and basic settings
	 * @param size is the desired dimension of the puzzle
	 * 
	 * Authors: Authors: Kathleen Near, Tanya Olivas, &  Jared Peterson
	 **/
	public SudokuJFrame(int size) {
		//Sets global size variable
		puzzleSize = size;
		
		//Builds frame's menu
		buildMenu();
		
		//Adds the SudokuGrid to the frame's pane
		Container contentPane = frame.getContentPane();
		contentPane.add(grid = new SudokuGrid(size));
		
		//Defines frame settings
		frame.pack();											//Set to grid's preferred size
		frame.setVisible(true);
		frame.setResizable(false);								//Window is Non-resizable
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Exits the program on window close
		
		//Centers the window on the screen
		centerView();
	}
	
	/**
	 * Builds the menu along the top of the screen
	 * 
	 * Author: Kathleen Near
	 **/
	private void buildMenu() {
      //Creates menu bar
      JMenuBar menuBar = new JMenuBar();
      
      //Sets the font size for the menu text (both the JMenus & JMenuItems)
      UIManager.put("Menu.font", ARIAL_SM);
      UIManager.put("MenuItem.font", ARIAL_SM);
      
      //Adds the Options, 
      addOptionsMenu(menuBar);
      addDifficultyMenu(menuBar);
      addNextPuzzleButton(menuBar);
      
      //Sets the frame's JMenuBar to menuBar
      frame.setJMenuBar(menuBar);
	}

	/**
     * Centers the grid on the screen
     * 
     * Author: Tanya Olivas
     **/
	private void centerView() {
		//Gets the screen and frame size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		
		//Calculates and sets center location
		frame.setLocation((screen.width - frameSize.width) >> 1,
		                  (screen.height - frameSize.height) >> 1);
	}
	
	/**
	 * Adds the Options menu to the menu bar
	 * @param menuBar is the menu bar the option will be added to
	 * 
	 *  Author: Tanya Olivas & Jared Peterson
	 **/
	private void addOptionsMenu(JMenuBar menuBar) {
	      //Creates options menu
	      JMenu optionMenu = new JMenu("Options");
	      
	      //Creates options menu items
	      JMenuItem size4x4  = new JMenuItem("4x4");
	      JMenuItem size9x9  = new JMenuItem("9x9");
	      
	      //Attaches options menu items to the options menu
	      optionMenu.add(size4x4);
	      optionMenu.addSeparator();
	      optionMenu.add(size9x9);
	      
	      //Adds options menu to menu bar
	      menuBar.add(optionMenu);
	      

	       //Action listener that changes the grid to a 4x4
	       //@param e is the click event where "4x4" is selected from the options menu
	       size4x4.addActionListener((ActionEvent e) -> {
	     	  puzzleSize = 4;
	           frame.getContentPane().removeAll();
	           frame.getContentPane().add(grid = new SudokuGrid(4));
	           frame.pack();
	           centerView();
	       });
	       //Action listener that changes the grid to a 9x9
	       //@param e is the click event where "9x9" is selected from the options menu
	       size9x9.addActionListener((ActionEvent e) -> {
	     	  puzzleSize = 9;
	           frame.getContentPane().removeAll();
	           frame.getContentPane().add(grid = new SudokuGrid(9));
	           frame.pack();
	           centerView();
	       });
	}
	
	/**
	 * Adds the Difficulty menu to the menu bar
	 * @param menuBar is the menu bar the option will be added to
	 * 
	 *  Author: Tanya Olivas & Jared Peterson
	 **/
	private void addDifficultyMenu(JMenuBar menuBar) {
		//Creates difficulty menu and button group
		JMenu difficultyMenu = new JMenu("Difficulty");
		ButtonGroup group = new ButtonGroup();
		  
		//Creates difficulty menu items
		JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
		JRadioButtonMenuItem medium = new JRadioButtonMenuItem("Medium");
		JRadioButtonMenuItem hard = new JRadioButtonMenuItem("Hard");
		  
		//Adds difficulty menu items to button group and difficulty menu
		group.add(easy);
		difficultyMenu.add(easy);
		difficultyMenu.addSeparator();
		  
		group.add(medium);
		difficultyMenu.add(medium);
		difficultyMenu.addSeparator();
		  
		group.add(hard);
		difficultyMenu.add(hard);
		  
		//Select medium difficulty by default
		medium.setSelected(true);
		SudokuGrid.medium = true;
		  
		//Adds difficulty menu to menu bar
		menuBar.add(difficultyMenu);
		
		  
		//Action listener that changes the difficulty
		//@param e is the click event where "easy" is selected from the difficulty menu
		easy.addActionListener((ActionEvent e) -> {
			SudokuGrid.medium = false;
			SudokuGrid.hard = false;
			SudokuGrid.easy = true;
		       
			frame.getContentPane().removeAll();
			frame.getContentPane().add(grid = new SudokuGrid(puzzleSize));
			frame.pack();
			centerView();
		});
		//Action listener that changes the difficulty
		//@param e is the click event where "medium" is selected from the difficulty menu/
		medium.addActionListener((ActionEvent e) -> {
			SudokuGrid.easy = false;
		    SudokuGrid.hard = false;
		    SudokuGrid.medium = true;
		    
		    frame.getContentPane().removeAll();
		    frame.getContentPane().add(grid = new SudokuGrid(puzzleSize));
		    frame.pack();
		    centerView();
		});
		//Action listener that changes the difficulty
		//@param e is the click event where "hard" is selected from the difficulty menu
		hard.addActionListener((ActionEvent e) -> {
			SudokuGrid.easy = false;
			SudokuGrid.medium = false;
			SudokuGrid.hard = true;
		     
			frame.getContentPane().removeAll();
			frame.getContentPane().add(grid = new SudokuGrid(puzzleSize));
			frame.pack();
			centerView();
		});
	}
	
	/**
	 * Adds the "Next Puzzle" button to the menu bar
	 * @param menuBar is the menu bar the option will be added to
	 * 
	 * Authors: Kathleen Near, Tanya Olivas, Jared Peterson, & Kurt McLane
	 **/
	private void addNextPuzzleButton(JMenuBar menuBar) {
		//Creates a "Next Puzzle" button
		JButton nextPuzzle = new JButton("Next Puzzle");
		nextPuzzle.setFont(ARIAL_SM);
		nextPuzzle.setFocusable(false);
		
		//Adds button to the top left of the menu bar
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(nextPuzzle);
		
		
		//Action listener that refreshes the grid to a new puzzle
		//@param e is the click event where "Next Puzzle" is clicked
		nextPuzzle.addActionListener((ActionEvent e) -> {
			int dialogButton = JOptionPane.YES_NO_OPTION;
			//Prompts the user to verify their intent
			int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you wish to start a new puzzle?\nChanges will not be saved.", "warning", dialogButton);
			//If "Yes" refresh with a new puzzle
			if (dialogResult == JOptionPane.YES_OPTION) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(grid = new SudokuGrid(puzzleSize));
				frame.pack();
				centerView();
			}
			//If "No" do nothing
		});
	}
}
