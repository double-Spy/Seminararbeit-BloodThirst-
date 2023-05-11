package gameState;

import util.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Overlays.Store;
import entity.*;
import enums.Gamestate;
import main.GameMaster;

public class Running {
	
//	Player:
	private Player p;
	private Coordinate mitte;
	private ArrayList<Bullet> playerShots;
	private static boolean playerMoveUp;
	private static boolean playerMoveDown;
	private static boolean playerMoveLeft;
	private static boolean playerMoveRight;
	public int maxLeben = 20;
	public int width = 45;
	public int height = 100;
	public int damage = 0;
	public double attackSpeed = 2;
	public double speed = 5;

//	enemys 
	private ArrayList<Enemy> enemys;

//	Game:
	private GameMaster gameMaster;

	private WaveHandler waveHandler;

	private CollisionDetector collisionDetector;

	private boolean endWave;

	private boolean paused;

	public Store store;

//	scroller
	private Scroller scroller;

	private BufferedImage[] hintergrunds;

	public Running(GameMaster gameMaster) {
		this.gameMaster = gameMaster;

		mitte = new Coordinate(gameMaster.getPrefSize().getWidth() / 2, gameMaster.getPrefSize().getHeight() / 2); // Coordinate
																													// bei
																													// der
																													// der
																													// Player
		// "gespawnd" wird

		initPlayer();

		collisionDetector = new CollisionDetector();

		waveHandler = new WaveHandler(this, gameMaster.getPrefSize());

		scroller = new Scroller(p, gameMaster.getPrefSize(), gameMaster.getMapSize(), this);

		store = new Store(this, gameMaster.getPrefSize());

		enemys = new ArrayList<Enemy>();

		playerShots = new ArrayList<Bullet>();

	}

	public void initGame() {

//hintergrund laden
		ImageLoader imageLoader = new ImageLoader();
		hintergrunds = new BufferedImage[1];
		hintergrunds = imageLoader.loadImages("res/hintergrund/hintergrund/", 1);

		waveHandler.setCurrentWave(1);

		waveHandler.initWave(1);

		Gamestate.state = Gamestate.RUNNING;
	}

	public void update() {

		scroller.update();

		// movment Player:
		if (playerMoveUp == true) {
			p.moveUp();
		}
		if (playerMoveDown == true) {
			p.moveDown(gameMaster.getMapSize().getHeight());
		}
		if (playerMoveRight == true) {
			p.moveRight(gameMaster.getMapSize().getWidth());
		}
		if (playerMoveLeft == true) {
			p.moveLeft();
		}

		// Enemy:

		// updates alle enemys
		for (Enemy enemy : enemys) {
			enemy.update(p.getObjectPosition());

		}
		for (int i = 0; i < enemys.size(); i++) {
			if (enemys.get(i).death) {
				enemys.remove(i);
			}
		}

		for (Bullet shot : playerShots) {
			shot.makeMove();
		}

		for (int i = 0; i < playerShots.size(); i++) {
			if (playerShots.get(i).isOutOfBounds()) {
				playerShots.remove(i);
			}
		}

		p.attack();

		collisionDetector.checkCollision(enemys, p);

		waveHandler.update();

	}

	public void paint(Graphics g) {

		g.drawImage(hintergrunds[0], 
				0 - getScroller().getGame_offsetX(), 
				0 - getScroller().getGame_offsetY(),
				(int) gameMaster.getMapSize().getWidth(), 
				(int) gameMaster.getMapSize().getHeight(), null);

		// ab hier keine verschiebung da det text ja immer gleich bleibt
		g.setFont(new Font(Font.SERIF, Font.BOLD, 50));
		g.setColor(Color.RED);
		g.drawString("Wave: " + waveHandler.getCurrentWave(), (int) (gameMaster.getPrefSize().getWidth() / 2)
				- (g.getFontMetrics().stringWidth("Wave: " + waveHandler.getCurrentWave())), 50); // zentriert den text
																									// egal wie lang er
																									// ist
		g.drawString("" + (int) waveHandler.getWaveTimer(), (int) (gameMaster.getPrefSize().getWidth() / 2)
				- (g.getFontMetrics().stringWidth("" + (int) waveHandler.getWaveTimer())), 100);
		g.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		g.drawString("Leben: " + (int) p.getLives(), 600, 30);
		g.drawString("MaxLives: " + p.getMaxLives(), 50, 200);
		g.drawString("Speed: " + p.getSpeed(), 50, 400);
		g.drawString("AttackSpeed: " + p.getAttackSpeed(), 50, 600);

		// ab hier wieder offset
		p.paintMe(g);
		
		for (int i = 0; enemys.size() > i; i++) {
			enemys.get(i).paintMe(g);
		}
		for (int i = 0; playerShots.size() > i; i++) {
			playerShots.get(i).paintMe(g);
		}

		gameMaster.getButtonHandler().paint(g);
		

//testinfo wie viele enemys es gibt unter den FPS/UPS
		g.setColor(Color.BLACK);
		g.fillRect((int) gameMaster.getPrefSize().getWidth() - 200, 40, 200, 40);
		g.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 20));
		g.setColor(Color.WHITE);
		g.drawString("Enemys: " + enemys.size(), (int) gameMaster.getPrefSize().getWidth() - 150, 65);
		
//		g.drawRect(
//				scroller.getLeftBorder(),
//				scroller.getUpperBorder(),
//				scroller.getRightBorder()-scroller.getLeftBorder(),
//				scroller.getLowerBorder()-scroller.getUpperBorder());
		
		g.drawRect(
				scroller.getRightBorder(),
				scroller.getUpperBorder(),
				scroller.getLeftBorder()-scroller.getRightBorder(),
				scroller.getLowerBorder()-scroller.getUpperBorder());
		
		System.out.println(scroller.getRightBorder()-scroller.getLeftBorder());

	}

	private void initPlayer() {
//		p = new Player(mitte, width, height, damage, maxLeben, this, attackSpeed, speed);
		p = new Player(new Coordinate(500,500), width, height, damage, maxLeben, this, attackSpeed, speed);
	}

//	Bullets
	public void initPlayerShot(Enemy nearestEnemy, Coordinate position) {
		playerShots.add(new Bullet(position, nearestEnemy, this));
	}

	public void removePlayerShot(Bullet bulletToRemove) {
		playerShots.remove(bulletToRemove);
	}

	public boolean isPlayerMoveUp() {
		return playerMoveUp;
	}

	public void setPlayerMoveUp(boolean b) {
		Running.playerMoveUp = b;
	}

	public boolean isPlayerMoveDown() {
		return playerMoveDown;
	}

	public void setPlayerMoveDown(boolean b) {
		Running.playerMoveDown = b;
	}

	public boolean isPlayerMoveLeft() {
		return playerMoveLeft;
	}

	public void setPlayerMoveLeft(boolean b) {
		Running.playerMoveLeft = b;
	}

	public boolean isPlayerMoveRight() {
		return playerMoveRight;
	}

	public void setPlayerMoveRight(boolean b) {
		Running.playerMoveRight = b;
	}

	public ArrayList<Enemy> getEnemys() {
		return enemys;
	}

	public void setEnemys(ArrayList<Enemy> enemys) {
		this.enemys = enemys;
	}

	public boolean isEndWave() {
		return endWave;
	}

	public void setEndWave(boolean endWave) {
		this.endWave = endWave;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public ArrayList<Bullet> getPlayerShots() {
		return playerShots;
	}

	public void setPlayerShots(ArrayList<Bullet> playerShots) {
		this.playerShots = playerShots;
	}

	public WaveHandler getWaveHandler() {
		return waveHandler;
	}

	public void setWaveHandler(WaveHandler waveHandler) {
		this.waveHandler = waveHandler;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public GameMaster getGameMaster() {
		return gameMaster;
	}

	public void setGameMaster(GameMaster gameMaster) {
		this.gameMaster = gameMaster;
	}

	public Scroller getScroller() {
		return scroller;
	}

	public void setScroller(Scroller scroller) {
		this.scroller = scroller;
	}

}
