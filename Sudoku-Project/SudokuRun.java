//Class Project
//CS371 - Software Development
//
//Authors: McLane, Kurt
//		   Near, Kathleen
//		   Olivas, Tanya
//		   Peterson, Jared

import javax.swing.SwingUtilities;

/**
 * The SudokuRun class runs the program, defaulting to a 9x9 grid of medium difficulty
 * 
 * Author: Kathleen Near
 **/

public class SudokuRun {
	//Updates the GUI via Event Dispatch Thread (EDT)
	public static void main(String[] args) {
		//Default dimensions of 9x9
		int defaultDimension = 9;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SudokuJFrame(defaultDimension);
			}
		});
	}
}
