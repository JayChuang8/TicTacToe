import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Contains most of the game's logic.
 * Creates the size of the board and the display of the board.
 * Checks to see if anyone has won yet, or if the game is a draw.
 * 
 * @author Jay Chuang
 * @version 1.0
 * @since February 2, 2020
 *
 */
public class Board implements Constants {
	/**
	 * The game board
	 */
	private char theBoard[][];
	
	/**
	 * The number of spots filled on the board
	 */
	private int markCount;
		
	/**
	 * Prints to the socket.
	 */
	PrintWriter sender;
	/**
	 * Makes the playing board of size 3x3, and sets all boxes to spaces
	 * @param sender 
	 * @param receiver 
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * Returns the value of the requested box
	 * @param row The row that is requested
	 * @param col The column that is requested
	 * @return Returns the char/value in the box
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * Checks to see if the board is full
	 * @return true if the board is full (all 9 spots are filled)
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Checks to see if player x has won or not
	 * @return true if player x has won, otherwise return false
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Checks to see if player o has won or not
	 * @return true if player o has won, otherwise return false
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Makes the row headers on the screen of the client. 
	 * Prints out row #_ and lines
	 * Prints out all the current marks as well
	 * 
	 * @param s Where the server is to print to in the socket.
	 */
	public void display(PrintWriter s) {
		sender = s;
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			sender.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				sender.print("|  " + getMark(row, col) + "  ");
			sender.println("|");
			addSpaces();
			addHyphens();
		}
	}

	/**
	 * Adds a mark (x or o) to the requested box
	 * @param row row of the mark to be inputed in
	 * @param col column of the mark to be inputed in
	 * @param mark the mark to be inputed (x or o)
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	/**
	 * Clears the board, puts spaces into all the boxes
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * Checks to see who the winner is
	 * @param mark the character that is inputed (x or o)
	 * @return returns 1 if there is a winner, otherwise false
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/**
	 * Prints out the column headers to the socket.
	 */
	void displayColumnHeaders() {
		sender.print("          ");
		for (int j = 0; j < 3; j++)
			sender.print("|col " + j);
		sender.println();
	}

	/**
	 * Fills in the intersections of the table, prints it to the socket.
	 */
	void addHyphens() {
		sender.print("          ");
		for (int j = 0; j < 3; j++)
			sender.print("+-----");
		sender.println("+");
	}

	/**
	 * Fills in spaces for the table, prints it to the socket.
	 */
	void addSpaces() {
		sender.print("          ");
		for (int j = 0; j < 3; j++)
			sender.print("|     ");
		sender.println("|");
	}
}