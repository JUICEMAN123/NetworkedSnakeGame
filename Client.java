import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.JFrame;

public class Client{
	
	public BufferedReader in;
	public PrintWriter out;
	
	public final int port;
	public final String host;
	public String username;
	public int userID;
	public int userLimit;
	public int userCount;
	public Socket socket;
	
	public Game game;
	public Menus menus = new Menus(this);
	
	public Client(String host, int port) {
		
		this.port = port;
		this.host = host;
		
		menus.mainMenu(null, menus);
		
	}
	
	public void Connect(JFrame frame) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		initializeGame();
		
		menus.play(frame);
	}
	
	public void initializeGame() throws IOException {

		String newConnection = in.readLine();
		String userLimitStr = in.readLine();
		String userCountStr = in.readLine();
		int id;
		int lim;
		int uCount;
		try {
			id = Integer.parseInt(newConnection.substring(newConnection.length() - 2));
		}	catch(NumberFormatException e22) {
			id = Integer.parseInt(newConnection.substring(newConnection.length() - 1));
		}
		try {
			lim = Integer.parseInt(userLimitStr.substring(userLimitStr.length() - 2));
		}	catch(NumberFormatException e22) {
			lim = Integer.parseInt(userLimitStr.substring(userLimitStr.length() - 1));
		}
		try {
			uCount = Integer.parseInt(userCountStr.substring(userCountStr.length() - 2));
		}	catch(NumberFormatException e22) {
			uCount = Integer.parseInt(userCountStr.substring(userCountStr.length() - 1));
		}
		this.userID = id;
		this.userLimit = lim;
		this.userCount = uCount;
	}	
	
	public static void main(String[] args) throws IOException {
		new Client("localhost" , 1010);
	}
	
}

class ClientThreadCOMMS extends Thread {
	public Client client;
	
	public ClientThreadCOMMS(Client client) {
		this.client = client; 
	}
	
	public void run() {
		while(!Thread.interrupted()) {
			try {
				String input;
				input = client.in.readLine();
				while (input != null) {
					// SERVER ins
					switch(input) {
						case "RC" :
							client.out.println("Col " + client.userID + " " + client.game.colors[client.userID].getRGB());
							for(int i = 0; i < client.game.colors.length; i++) {
								System.out.println(client.game.colors[i].getRGB());
							}
							break;
					}
					// OTHER CLIENTS ins
					StringTokenizer inputCode = new StringTokenizer(input);
					switch(inputCode.nextToken()) {
						case "Col":
							recieveColor(input);
					}
					
					//get input
					input = client.in.readLine();
				}
			}
			catch(SocketException e1) {
				this.interrupt();
			}
			catch(IOException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public void recieveColor(String input) {
		StringTokenizer st = new StringTokenizer(input);
		String code = st.nextToken();
		int index = Integer.parseInt(st.nextToken());
		Color c = Color.decode(st.nextToken());
		client.game.colors[index] = c;
	}
	
}

