import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame{
	
	public Client client;
	public final int WIDTH = 1024, HEIGHT = 768;
	public final int gridW = 16, gridH = 16;
	public GamePanel gamePanel = new GamePanel(this);
	public Color[] colors;
	public int r = (int)(Math.random() * 256 + 1);
	public int g = (int)(Math.random() * 256 + 1);
	public int b = (int)(Math.random() * 256 + 1);
	
	public Game(Client client) throws IOException {
		this.client = client;
		colors = new Color[client.userLimit];
		colors[client.userID] = new Color(r, g, b);
		setup(this);
	}
	
	public void setup(JFrame frame) throws IOException {
		frame.setTitle("Network Game");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(gamePanel);
		comm();
		play(frame);
	}
	
	public void comm() throws IOException{
		while(true) {
			String input;
			while ((input = client.in.readLine()) != null) {
				switch(input) {
					case "RC" :
						//client.out.println(client.userID + " " + r + " " + g + " " + b);
						client.out.print("Col " + client.userID + " " + colors[client.userID].toString());
						recieveColors();
						return;
					
				}
			}
		}
	}
	
	public void play(JFrame frame) throws IOException {
		
	}
	
	public void recieveColors() throws IOException {
		while(true) {
			String input;
			while ((input = client.in.readLine()) != null) {
				switch(input) {
					
				}
			}
		}
	}

	
	public void exit(JFrame frame) {
		frame.dispose();
	}
}

class GamePanel extends JPanel{
	public Game game;
	
	public GamePanel(Game game) {
		this.game = game;
	}
	
}
