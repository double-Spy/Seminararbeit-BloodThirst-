package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.GameMaster;

public class Store {
	
	private GameMaster gameMaster;
	
	private int item1;
	private int item2;
	private int item3;
	private int item4;
	
	public Store(GameMaster gameMaster) {
		this.gameMaster=gameMaster;
	}

	public void update() {

	}

	public void paint(Graphics g) {
		
		gameMaster.getRunning().paint(g);
		
		//zeichnet durchsichtigen grauen hintergrunf
		g.setColor(new Color(0,0,0,150));
		g.fillRect(0,0,(int)gameMaster.getPrefSize().getWidth(),(int) gameMaster.getPrefSize().getHeight());
		g.fillRect((int)(gameMaster.getPrefSize().getWidth()*0.8),(int)(gameMaster.getPrefSize().getHeight()*0.1),(int)(gameMaster.getPrefSize().getWidth()*0.18),(int)(gameMaster.getPrefSize().getHeight()*0.8));
		
		 
		g.setFont(new Font(Font.SERIF, Font.BOLD, 50));
		g.setColor(Color.RED);
		
		gameMaster.getButtonHandler().paint(g);
	}
	
	public void buy(int itemIndex) {
		
	}
	
	public void reroll() {
		
	}
	
	public void item1() {
		buy(item1);
	}
	
	public void item2() {
		buy(item2);
	}

	public void item3() {
		buy(item3);
	}

	public void item4() {
		buy(item4);
	}
}
