import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Contains objects xPlayer, oPlayer, and board.
 * Runs the game and lets xPlayer make the first move.
 * 
 * @author Jay Chuang
 * @version 1.0
 * @since February 2, 2020
 *
 */
public class Referee {
	/**
	 * Object of type Player
	 */
	private Player xPlayer;
	
	/**
	 * Object of type Player
	 */
	private Player oPlayer;
	
	/**
	 * Object of type Board
	 */
	private Board board;	
	
	/**
	 * Constructor for Referee, initializes xPlayer, oPlayer, and board to null.
	 * @param sender 
	 * @param receiver 
	 */
	public Referee() {
		this.xPlayer = null;
		this.oPlayer = null;
		this.board = null;
	}
	
	/**
	 * Runs the game, and calls display to print the board to each client's screen.
	 * @throws IOException Input needed for xPlayer.play().
	 */
	public void runTheGame() throws IOException {
		oPlayer.setOpponent(xPlayer);
		xPlayer.setOpponent(oPlayer);
		
		xPlayer.sender.println("Ref started the game");
		oPlayer.sender.println("Ref started the game");
		
		board.display(xPlayer.sender);
		board.display(oPlayer.sender);
		
		xPlayer.play();
		xPlayer.sender.println("Game ended");
		oPlayer.sender.println("Game ended");
	}
	
	/**
	 * Sets the board to point to the object theBoard in theGame, which is in main.
	 * @param board Object that is passed through that board is supposed to point to.
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Sets oPlayer to point to the object oPlayer in main.
	 * @param oPlayer Object that is passed through that oPlayer is supposed to point to.
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
		oPlayer.setOpponent(xPlayer);
	}
	
	/**
	 * Sets xPlayer to point to the object xPlayer in main.
	 * @param xPlayer Object that is passed through that xPlayer is supposed to point to.
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
		xPlayer.setOpponent(oPlayer);
	}

	/**
	 * Getter for the xPlayer.
	 * @return The xPlayer object
	 */
	public Player getxPlayer() {
		return this.xPlayer;
	}
	
	/**
	 * Getter for the oPlayer.
	 * @return The oPlayer object.
	 */
	public Player getoPlayer() {
		return this.oPlayer;
	}
}
