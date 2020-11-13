
import java.io.*;

/**
 * Contains starting point of the program, initializes objects theGame, xPlayer, oPlayer, theRef.
 * Sets the board, appoints the referee, and creates the players.
 * 
 * @author Jay Chuang
 * @version 1.0
 * @since February 2, 2020
 *
 */
public class Game implements Constants, Runnable{
	/**
	 * Object of type board
	 */
	private Board theBoard;
	
	/**
	 * Points to theRef in main
	 */
	private Referee theRef;
	
	/**
	 * Constructor for object Game, constructs an object of type Board
	 * @param sender 
	 * @param receiver 
	 */
    public Game() {
        theBoard  = new Board();
	}
    
    /**
     * Connects theRef of type theGame to the object theRef in main
     * @param r r is theRef in main that theRef in theGame points to
     * @throws IOException input needed for theRef.runTheGame()
     */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
        theRef.setBoard(theBoard);
		theRef.getxPlayer().setBoard(theBoard);
		theRef.getoPlayer().setBoard(theBoard);
    }

    /**
     * Run method which calls runTheGame() to start everything.
     */
	@Override
	public void run() {
		try {
			theRef.runTheGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

