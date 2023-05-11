package gameState;

import java.awt.Graphics;

import main.GameMaster;

public class Menu {

	public GameMaster gameMaster;

	public Menu(GameMaster gameMaster) {
		this.gameMaster = gameMaster;
	}

	public void update() {

	}

	public void paint(Graphics g) {
		gameMaster.getButtonHandler().paint(g);
	}
}
