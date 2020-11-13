import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class of object Player.
 * Makes the moves in the game, and alternates between both xPlayer and oPlayer.
 * Checks to see if the game is over, and if so a message is printed and the game is ended.
 * 
 * @author Jay Chuang
 * @version 1.0
 * @since February 2, 2020
 *
 */
abstract class Player {
	/**
	 * Name of the player.
	 */
	protected String name;
	
	/**
	 * Object of type Board.
	 */
	protected Board board;
	
	/**
	 * Object of type Player.
	 */
	protected Player opponent;
	
	/**
	 * Char containing x or o.
	 */
	protected char mark;
	
	/**
	 * Constructor for the class Player
	 * @param name Name of the player to be copied into the Player object.
	 * @param letter Either X or 0 depending on which player they are.
	 */
	public Player(String name, char letter) {
		this.name = name;
		this.mark = letter;
	}

	/**
	 * Checks to see if anyone has won yet, or if the game is a tie.
	 * Checks to see which player should have the next move, and calls makeMove().
	 * @throws IOException Input needed for makeMove().
	 */
	protected void play() throws IOException {
		int i = 1;
		while(true) {
			if(board.xWins() == true) {
				System.out.println("Game over, " + name + " has won!!!");
				board.clear();
				break;
			}
			if(board.oWins() == true) {
				System.out.println("Game over, " + opponent.name + " has won!!!");
				board.clear();
				break;
			}
			if(board.isFull() == true) {
				System.out.println("Game over, tie game");
				board.clear();
				break;
			}
			
			if(i % 2 == 1)
				makeMove();
			else
				opponent.makeMove();
			board.display();
			i++;
		}
	}
	
	/**
	 * Asks the user where they want to place their X or O, and sends the indexes to addMark to fill in the board.
	 * @throws IOException Input needed to read what index the user wants to place their character.
	 */
	protected void makeMove() throws IOException {
		BufferedReader stdin;
		stdin = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.println(name + ", what row should your " + mark + " be placed in?");
			String row = stdin.readLine();
		
			System.out.println(name + ", what column should your " + mark + " be placed in?");
			String col = stdin.readLine();
		
			if(getMark(Integer.parseInt(row), Integer.parseInt(col)) == ' ') {
				addMark(Integer.parseInt(row), Integer.parseInt(col));
				break;
			}
			System.out.println("This spot is taken already, please choose an empty spot \n");
		}
	}
	
	protected char getMark(int i, int j) {
		return board.getMark(i, j);
	}
	
	protected void addMark(int i, int j) {
		board.addMark(i, j, this.mark);
	}
	
	/**
	 * Sets the opponent to the other player.
	 * @param p The opponent to be set as.
	 */
	protected void setOpponent(Player p) {
		this.opponent = p;
	}
	
	/**
	 * Object of type board points to theBoard.
	 * @param theBoard The playing board.
	 */
	protected void setBoard(Board theBoard) {
		this.board = theBoard;
	}
}
