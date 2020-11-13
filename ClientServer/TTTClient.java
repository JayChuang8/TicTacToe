import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TTTClient {

	/**
	 * The socket that is written to or read from.
	 */
	private Socket socket;
	
	/**
	 * Used to write to the socket.
	 */
	private PrintWriter sender;
	
	/**
	 * Used to read from the socket.
	 */
	private BufferedReader receiver;
	
	/**
	 * Used to read from the keyboard.
	 */
	private BufferedReader keyboard;
	
	/**
	 * Constructor for TTTClient.
	 * @param serverName Server used, which is localhost
	 * @param portNumber Port number that is used.
	 */
	public TTTClient(String serverName, int portNumber) {
		
		try {
			socket = new Socket(serverName, portNumber);
			
			keyboard = new BufferedReader(new InputStreamReader(System.in));
			receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sender = new PrintWriter(socket.getOutputStream(), true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Communicates with the user. Prints anything received from the server to the client's screen.
	 * Sends any user input to the server via socket.
	 */
	public void communicate() {
		
		try {
			while(true) {
				String line = "";
				while(true) {
					line = receiver.readLine();
				
					if(line.contains("\0")) {
						line = line.replace("\0", "");
						System.out.println(line);
						break;
					}
					
					if(line.equals("QUIT"))
						return;
					
					System.out.println(line);
				}
				line = keyboard.readLine();
				sender.println(line);
				sender.flush();
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				keyboard.close();
				receiver.close();
				sender.close(); 
			}catch (IOException e) {
				e.getStackTrace();
			}
		}
	}
	
	/**
	 * Instantiates a TTTClient object, and calls communicate.
	 */
	public static void main(String[] args) throws IOException{
		
		TTTClient myClient = new TTTClient("localhost", 9090);
		myClient.communicate();
	}
}
