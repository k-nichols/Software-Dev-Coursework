//Class Project
//CS371 - Software Development
//
//Authors: McLane, Kurt
//		   Near, Kathleen
//		   Olivas, Tanya
//		   Peterson, Jared

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * The FieldFilter class formats the input of a JTextField to only allow
 * one character per box, only integers 1 to size
 **/

class FieldFilter extends DocumentFilter {
	
	//Contains the dimension of the puzzle (size x size)
	private int size;
	
    /**
     * Constructor
     * @param puzzleSize is the dimension of the puzzle (4 for 4x4 or 9 for 9x9)
     * 
     * Author: Kathleen Near
     */
	public FieldFilter (int puzzleSize) {
		size = puzzleSize;
	}
	
	/**
     * Getter method
     * @return the class variable size
     * 
     * Author: Kathleen Near
     */
	public int getPuzzleSize() {
		return size;
	}
	
	/**
     * Bypasses the document filter and inserts the specified text
     * 
     * Author: Kathleen Near
     */
	 @Override
    public void insertString(FilterBypass fbypass, int offset, String text, AttributeSet attrSet) throws BadLocationException {
        super.insertString(fbypass, offset, revise(text), attrSet);
    }

	/**
     * Deletes text from offset to offset + length, replacing it with the specified text
     * 
     * Author: Kathleen Near
     */
    @Override
    public void replace(FilterBypass fbypass, int offset, int length, String text, AttributeSet attrSet) throws BadLocationException {
        super.replace(fbypass, offset, length, revise(text), attrSet);
    }

	/**
     * Revises the string based upon what characters are accepted
     * 
     * Author: Kathleen Near
     */
    private String revise(String text) {
        StringBuilder builder = new StringBuilder(text);
        int index = 0;
        while (index < builder.length()) {
            if (accept(builder.charAt(index))) {
                index++;
            } else {
                builder.deleteCharAt(index);
            }
        }
        return builder.toString();
    }

    /**
     * Determines if a given character should remain in the String.
     * @param c The character being checked
     * @return true if the character is valid, false if it should be removed.
     * 
     * Author: Kathleen Near
     */
    public boolean accept(final char c) {
    	//If 4x4 puzzle, allow only integers 1-4
    	if (getPuzzleSize() == 4)
    		return (Character.isDigit(c) && (c != '0') && (c < '5')) || c == '.';
    	
    	//Allow integers 1-9
    	else
    		return (Character.isDigit(c) && (c != '0')) || c == '.';
    }
}
