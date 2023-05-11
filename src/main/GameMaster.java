package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import input.*;
import util.*;
import enums.Gamestate;
import gameState.*;

//(Höhe / Höhe des Bildes) x 100
//(Breite / Breite des Bildes) x 100

public class GameMaster extends JPanel implements Runnable {

	private static Dimension prefSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize()); // Größe des
																									// Fensters

	public KeyInput keyInput;
	public Mouse mouse;

	// Game
	public int maxLeben = 20;
	public Dimension mapSize = new Dimension(5000, 5000);

	private final int FPS = 60;
	private final int UPS = 200;
	private int currentFPS;
	private int currentUPS;
	private Thread gameThread;

	public boolean endscreen;
	public boolean endWave;

//	// Buttons
	private ButtonHandler buttonHandler;

	// gamestates
	public Menu menu;
	public Running running;
	public Store store;
	public Death death;
	public Paused paused;

	public GameMaster() {
		System.out.println("master con");
		setFocusable(true);
		setPreferredSize(prefSize);
		
		buttonHandler = new ButtonHandler(this);

		menu = new Menu(this);
		running = new Running(this);
		paused = new Paused(this);
		store = new Store(this); 

		initGame(); // startGame();
		gameLoopStart();
	}

	private void gameLoopStart() {

		// create Gamethread and loop run()
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void initGame() {
		System.out.println("initGame");

		// outsorce the input
		keyInput = new KeyInput(this);
		addKeyListener(keyInput);
		mouse = new Mouse(this);
		addMouseMotionListener(mouse);
		addMouseListener(mouse);

	}

	public void startGame() {

		running.initGame();

	}

	public void run() {

		double nanoSecPerFrame = 1000000000.0f / FPS;
		double nanoSecPerUpdate = 1000000000.0f / UPS;

		double deltaUpdate = 0;
		double deltaFrame = 0;

		long prevoiusTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long currentTimeMillis = System.currentTimeMillis();
		long lastCheck = currentTimeMillis;

		while (true) {

			long currentNanoTime = System.nanoTime();

//	         update delta time variables with the time elapsed since the last loop iteration
			deltaUpdate += (currentNanoTime - prevoiusTime) / nanoSecPerUpdate;
			deltaFrame += (currentNanoTime - prevoiusTime) / nanoSecPerFrame;
			prevoiusTime = currentNanoTime;

			if (deltaUpdate >= 1) {
				update();
				updates++;
				deltaUpdate--;
			}

			if (deltaFrame >= 1) {
				repaint();
				frames++;
				deltaFrame--;
			}

//	         if one second has elapsed, update the current FPS and UPS counters
			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				currentFPS = frames;
				currentUPS = updates;
				frames = 0;
				updates = 0;
//	            zieht eine secunde vom wavetimer ab wenn die runde gerade läuft
				if (Gamestate.state==Gamestate.RUNNING) {
					running.getWaveHandler().substractWaveTime();
				}
			}
		}
	}

	public void update() {
		switch (Gamestate.state) {
		case MENU:

			menu.update();

			break;
		case RUNNING:
			running.update();
			break;
		default:
			break;
		}
	}

	public void paint(Graphics g) {
		super.paintComponent(g);

		switch (Gamestate.state) {

		case MENU:
			menu.paint(g);
			break;
			
		case RUNNING:
			running.paint(g);
			break;

		case STORE:
			store.paint(g);
			break;
			
		case PAUSED:
			paused.paint(g);
			break;
			
		case DEATH:
			death.paint(g);
			break;
		}

//		debugg info display:
		g.setColor(Color.BLACK);
		g.fillRect((int) prefSize.getWidth() - 200, 0, 200, 40);
		g.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 20));
		g.setColor(Color.WHITE);
		g.drawString("FPS:" + currentFPS, (int) prefSize.getWidth() - 180, 25);
		g.drawString("UPS:" + currentUPS, (int) prefSize.getWidth() - 90, 25);
	}

	// Getter Setter

	public Dimension getPrefSize() {
		return prefSize;

	}

	public KeyInput getKeyInput() {
		return keyInput;
	}

	public void setKeyInput(KeyInput keyInput) {
		this.keyInput = keyInput;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

	public int getMaxLeben() {
		return maxLeben;
	}

	public void setMaxLeben(int maxLeben) {
		this.maxLeben = maxLeben;
	}

	public Dimension getMapSize() {
		return mapSize;
	}

	public void setMapSize(Dimension mapSize) {
		this.mapSize = mapSize;
	}

	public int getCurrentFPS() {
		return currentFPS;
	}

	public void setCurrentFPS(int currentFPS) {
		this.currentFPS = currentFPS;
	}

	public int getCurrentUPS() {
		return currentUPS;
	}

	public void setCurrentUPS(int currentUPS) {
		this.currentUPS = currentUPS;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}

	public boolean isEndscreen() {
		return endscreen;
	}

	public void setEndscreen(boolean endscreen) {
		this.endscreen = endscreen;
	}

	public boolean isEndWave() {
		return endWave;
	}

	public void setEndWave(boolean endWave) {
		this.endWave = endWave;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Running getRunning() {
		return running;
	}

	public void setRunning(Running running) {
		this.running = running;
	}

	public int getFPS() {
		return FPS;
	}

	public int getUPS() {
		return UPS;
	}

	public static void setPrefSize(Dimension prefSize) {
		GameMaster.prefSize = prefSize;
	}

	public ButtonHandler getButtonHandler() {
		return buttonHandler;
	}

	public void setButtonHandler(ButtonHandler buttonHandler) {
		this.buttonHandler = buttonHandler;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Death getDeath() {
		return death;
	}

	public void setDeath(Death death) {
		this.death = death;
	}

	public Paused getPaused() {
		return paused;
	}

	public void setPaused(Paused paused) {
		this.paused = paused;
	}
	
	
}
