import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame{
	
	public Client client;
	public static final int WIDTH = 1024, HEIGHT = 768;
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
		setup();
	}
	
	public void setup() throws IOException {
		setTitle("Network Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		add(gamePanel);
		
		comm();
		play();
	}
	// TO FIX USE THREAD AND CHECK EVERY FEW MS
	public void comm() throws IOException{
		while(true) {
			String input;
			input = client.in.readLine();
			while (input != null) {
				// SERVER ins
				switch(input) {
					case "RC" :
						//client.out.println(client.userID + " " + r + " " + g + " " + b);
						client.out.println("Col " + client.userID + " " + colors[client.userID].getRGB());
						for(int i = 0; i < colors.length; i++) {
							System.out.println(colors[i].toString());
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
	}
	
	public void play() throws IOException {
		
	}
	
	public void recieveColor(String input) {
		StringTokenizer st = new StringTokenizer(input);
		String code = st.nextToken();
		int index = Integer.parseInt(st.nextToken());
		Color c = Color.decode(st.nextToken());
		colors[index] = c;
	}

}

class GamePanel extends JPanel{
	public Game game;
	
	public GamePanel(Game game) {
		this.game = game;
		setLayout(null);
		setBackground(new Color(120, 120, 120));
		
		
		JButton exitB = new JButton("X");
		exitB.setBorderPainted(false);
		exitB.setBackground(Color.DARK_GRAY);
		exitB.setForeground(new Color(150, 50, 50));
		exitB.setBounds(Game.WIDTH - 50, 0, 50, 50);
		exitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.dispose();
			}	
		});	
		System.out.println("Higuolv;cvhfbgvodn");
		add(exitB);
		
	}
	
}
