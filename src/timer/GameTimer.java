package timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.GameMaster;

public class GameTimer {
	
	private Timer t;
	
	public GameTimer(int delay,GameMaster gameMaster) {
		
		t = new Timer(delay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		t.start();
	}
}