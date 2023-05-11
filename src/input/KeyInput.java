package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enums.Gamestate;
import main.*;

public class KeyInput implements KeyListener{
	
	private GameMaster gameMaster;
	
	public KeyInput(GameMaster gameMaster) {
		this.gameMaster=gameMaster;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_W) {
			gameMaster.getRunning().setPlayerMoveUp(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			gameMaster.getRunning().setPlayerMoveDown(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			gameMaster.getRunning().setPlayerMoveLeft(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			gameMaster.getRunning().setPlayerMoveRight(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (Gamestate.state==Gamestate.PAUSED) 
				Gamestate.state=Gamestate.RUNNING;
			else {
				Gamestate.state=Gamestate.PAUSED;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_W) {
			gameMaster.getRunning().setPlayerMoveUp(false);		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			gameMaster.getRunning().setPlayerMoveDown(false);		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			gameMaster.getRunning().setPlayerMoveLeft(false);		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			gameMaster.getRunning().setPlayerMoveRight(false);		}
	}
	
}
