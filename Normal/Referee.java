import java.io.IOException;

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
	 */
	public Referee() {
		this.xPlayer = null;
		this.oPlayer = null;
		this.board = null;
	}
	
	/**
	 * Runs the game, and displays the starting empty board.
	 * @throws IOException Input needed for xPlayer.play().
	 */
	public void runTheGame() throws IOException {
		oPlayer.setOpponent(xPlayer);
		xPlayer.setOpponent(oPlayer);
		
		board.display();
		
		xPlayer.play();
		System.out.println("Game ended");
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
	}
	
	/**
	 * Sets xPlayer to point to the object xPlayer in main.
	 * @param xPlayer Object that is passed through that xPlayer is supposed to point to.
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
		xPlayer.setOpponent(oPlayer);
	}
}
