package gameState;

import java.awt.Color;
import java.awt.Graphics;

import main.GameMaster;
import util.ButtonHandler;

public class Paused {
	
	private GameMaster gameMaster;
	
	public Paused(GameMaster gameMaster) {
		this.gameMaster=gameMaster;
	}

	public void update() {

	}

	public void paint(Graphics g) {
		
		//damit man das spiel im hintergrung immernoch sieht
		gameMaster.getRunning().paint(g);
		
		g.setColor(new Color(0,0,0,150));
		g.fillRect(0,0,(int)gameMaster.getPrefSize().getWidth(),(int) gameMaster.getPrefSize().getHeight());
		
		gameMaster.getButtonHandler().paint(g);
	}
}
