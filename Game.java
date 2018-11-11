import java.awt.Color;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame{
	
	public Client client;
	public final int WIDTH = 1024, HEIGHT = 768;
	public final int gridW = 16, gridH = 16;
	public GamePanel gamePanel = new GamePanel(this);
	public Color[] colors;
	public int r = (int)(Math.random() * 256);
	public int g = (int)(Math.random() * 256);
	public int b = (int)(Math.random() * 256);
	
	public Game(Client client) throws IOException {
		this.client = client;
		colors = new Color[client.userLimit];
		for(int i = 0; i < colors.length; i++) {
			colors[i] = new Color(0, 0, 0);
		}
		colors[client.userID] = new Color(r, g, b);
		setup(this);
	}
	
	public void setup(JFrame frame) throws IOException {
		frame.setTitle("Network Game");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			input = client.in.readLine();
			while (input != null) {
				// SERVER ins
				
				switch(input) {
					case "RC" :
						//client.out.println(client.userID + " " + r + " " + g + " " + b);
						client.out.println("Col " + client.userID + " " + colors[client.userID].toString());
						recieveColors();
						for(int i = 0; i < colors.length; i++) {
							System.out.println(colors[i].toString());
						}
				}
				// OTHER CLIENTS ins
				
				input = client.in.readLine();		
			}
		}
	}
	
	public void play(JFrame frame) throws IOException {
		
	}
	
	public void recieveColors() throws IOException {
		while(true) {
			String input;
			input = client.in.readLine();
			StringTokenizer st = new StringTokenizer(input);
			String checker = st.nextToken();
			//System.out.println(input + "     " + checker);
			if(checker != "RecCol") {
				//comm();
				return;
			}
			while (input != null) {
				int index = Integer.parseInt(st.nextToken());
				Color c = Color.decode(st.nextToken());
				colors[index] = c;
				System.out.println(index + c.toString());
				input = client.in.readLine();
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
