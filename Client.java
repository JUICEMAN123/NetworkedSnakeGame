import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client{
	
	public BufferedReader in;
	public PrintWriter out;
	
	public final int port;
	public final String host;
	public String username;
	public int userID;
	public int userLimit;
	
	public Menus menus = new Menus(this);
	
	public Client(String host, int port) {
		
		this.port = port;
		this.host = host;
		
		menus.mainMenu(null, menus);
		
	}
	
	public void Connect(JFrame frame) throws UnknownHostException, IOException {
		Socket socket = new Socket(host, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		String newConnection = in.readLine();
		String userLimitStr = in.readLine();
		int id;
		int lim;
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
		this.userID = id;
		this.userLimit = lim;
		
		menus.play(frame);
	}
		
	public static void main(String[] args) throws IOException {
		new Client("localhost" , 1010);
	}
	
}

