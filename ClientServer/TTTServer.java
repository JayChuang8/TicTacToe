import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TTTServer {

	/**
	 * The socket on the server's side.
	 */
	private ServerSocket serverSocket;
	
	/**
	 * ExecutorService for a pool of threads.
	 */
	private ExecutorService pool;
	
	/**
	 * Constructor for TTTServer. Makes a pool of threads.
	 * @param port The port that the server will use.
	 */
	public TTTServer(int port) {
		
		try {
			serverSocket = new ServerSocket(port);
			pool = Executors.newCachedThreadPool();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Makes new tic tac toe games. When two clients connect, a game is executed.
	 * @throws IOException
	 */
	public void communicate() throws IOException{
		try {
			while(true) {
				
				Player xPlayer = new Player(serverSocket.accept(), 'X');
				System.out.println("[SERVER] Server accepted first player X!");
				Player oPlayer = new Player(serverSocket.accept(), 'O');
				System.out.println("[SERVER] Server accepted first player O!");
				
				Referee theRef = new Referee();
				
				theRef.setxPlayer(xPlayer);
				System.out.println("[SERVER] Player X has been set by the referee.");
				theRef.setoPlayer(oPlayer);
				System.out.println("[SERVER] Player O has been set by the referee.");
				
				Game theGame = new Game();
				theGame.appointReferee(theRef);
				
				System.out.println("[SERVER] Started a game.");
				
				pool.execute(theGame);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			pool.shutdown();
		}
	}
	
	/**
	 * Instantiates a TTTServer and calls communicate which runs the tic tac toe game.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{

		TTTServer myServer = new TTTServer(9090);
		System.out.println("[SERVER] Server is running...");
		myServer.communicate();
	}

}
