import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
public class Player {
	/**
	 * Name of the player.
	 */
	private String name;
	
	/**
	 * Object of type Board.
	 */
	private Board board;
	
	/**
	 * Object of type Player.
	 */
	private Player opponent;
	
	/**
	 * Char containing x or o.
	 */
	private char mark;
	
	/**
	 * The socket that the server reads from and writes to.
	 */
	private Socket socket;
	
	/**
	 * Reads from the socket.
	 */
	public BufferedReader receiver;
	
	/**
	 * Prints to the socket.
	 */
	public PrintWriter sender;
	
	/**
	 * Constructor for the class Player. Gets the names of both the x and o player. Also welcomes the first player to enter the game (X).
	 * @param name Name of the player to be copied into the Player object.
	 * @param letter Either X or 0 depending on which player they are.
	 */
	public Player(Socket s, char letter) {
		this.socket = s;
		this.mark = letter;
		
		try {
			receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sender = new PrintWriter(socket.getOutputStream(), true);
		}catch(IOException e) {
			e.printStackTrace();
		}
		this.getPlayerName();
		this.setup();
	}

	/**
	 * Checks to see if anyone has won yet, or if the game is a tie.
	 * Checks to see which player should have the next move, and calls makeMove().
	 * @throws IOException Input needed for makeMove().
	 */
	public void play() throws IOException {
		
		board.clear();
		
		while((board.xWins() == false) && (board.oWins() == false) && (board.isFull() == false)) {
			
			opponent.sender.println("Please wait, it is the opponent's turn");
			makeMove();
			this.board.display(sender);
			opponent.board.display(opponent.sender);
			
			if(board.xWins() == false && board.isFull() == false) {
				sender.println("Please wait, it is the opponent's turn");
				opponent.makeMove();
				this.board.display(sender);
				opponent.board.display(opponent.sender);
			}
		}
		
		sender.println("The game is over");
		opponent.sender.println("The game is over");
		
		if(board.xWins() == true) {
			sender.println("Game over, you, " + name + " have won!!!");
			opponent.sender.println("Game over, you, " + opponent.name + " have lost...");
		}
		else if(board.oWins() == true) {
			opponent.sender.println("Game over, you, " + opponent.name + " have won!!!");
			sender.println("Game over, you, " + name + " have lost...");
		}
		else {
			sender.println("Game over, tie game");
			opponent.sender.println("Game over, tie game");
		}
		
		sender.println("QUIT");
		opponent.sender.println("QUIT");
	}
	
	/**
	 * Asks the user where they want to place their X or O, and sends the indexes to addMark to fill in the board.
	 * Checks for any input errors such as an invalid input or a space being taken already.
	 * @throws IOException Input needed to read what index the user wants to place their character.
	 */
	public void makeMove() throws IOException {
		
		while(true) {
			String regex = "[0-2]+";
			
			sender.println(name + ", what row should your " + mark + " be placed in? \0");
			String row = receiver.readLine();
		
			while(!row.matches(regex)) {
				sender.println("Invalid input, try again");
				sender.println(name + ", what row should your " + mark + " be placed in? \0");
				row = receiver.readLine();
			}
			
			sender.flush();
			sender.println(name + ", what column should your " + mark + " be placed in? \0");
			String col = receiver.readLine();
		
			while(!col.matches(regex)) {
				sender.println("Invalid input, try again");
				sender.println(name + ", what column should your " + mark + " be placed in? \0");
				col = receiver.readLine();
			}
		
			if(board.getMark(Integer.parseInt(row), Integer.parseInt(col)) == ' ') {
				board.addMark(Integer.parseInt(row), Integer.parseInt(col), mark);
				break;
			}
			
			sender.println("This spot is taken, please try again.");
		}
	}
	
	/**
	 * Sets the opponent to the other player.
	 * @param p The opponent to be set as.
	 */
	public void setOpponent(Player p) {
		this.opponent = p;
	}
	
	/**
	 * Object of type board points to theBoard.
	 * @param theBoard The playing board.
	 */
	public void setBoard(Board theBoard) {
		this.board = theBoard;
	}
	
	/**
	 * Prints the message to enter name to the client's screen.
	 */
	public void sendString(String toSend) {
		sender.println(toSend);
		sender.flush();
	}
	
	/**
	 * Makes the string to ask the player to enter their name, and checks to see if they entered something.
	 * If not, if will ask the player to try again.
	 */
	public void getPlayerName() {
		try {
			sendString("Enter the " + mark + " player's name: \0");
			name = receiver.readLine();
			
			while(name == null) {
				sendString("Please try again: \0");
				name = receiver.readLine();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Welcomes the first player in the game.
	 */
	private void setup() {
		sender.println("Welcome " + name);
		
		if(this.mark == 'X')
			sender.println("Waiting for the opponent to connect");
	}

}
