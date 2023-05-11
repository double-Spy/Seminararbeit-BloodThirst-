package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import enums.PlayerMovement;
import gameState.Running;
import util.*;

public class Player extends GameObject {

	private Rectangle2D hitBox;
	private double damage; // mehr = besser
	private double lives; // mehr= besser
	private double maxLives; // mehr= besser
	private double speed; // mehr= besser
	private double attackSpeed; // weniger= besser
	private double attackIntervall;

	private Running master;

	private Enemy nearestEnemy;

	private BufferedImage[] runLeftImages;
	private BufferedImage[] runRightImages;
	private BufferedImage[] standingImages;
	private int currentImage;
	private int imageSpeed;
	private int imageCounter;
	private boolean runningLeft;
	private boolean runningRight;
	private boolean runningUpDown;
	private boolean lastMove;

	public Player(Coordinate objectPosition, int width, int height, double damage, double lives, Running master,
			double attackSpeed, double speed) {
		super(objectPosition, width, height);

		this.master = master;

		this.damage = damage;
		this.lives = lives;
		maxLives = lives;
		this.speed = speed;
		this.attackSpeed = attackSpeed;
		attackIntervall = attackSpeed;

		ImageLoader imageLoader = new ImageLoader();

		runLeftImages = new BufferedImage[2];
		runLeftImages = imageLoader.loadImages("res/Player/runningLeft/", 15);

		runRightImages = new BufferedImage[2];
		runRightImages = imageLoader.loadImages("res/Player/runningRight/", 15);

		standingImages = new BufferedImage[4];
		standingImages = imageLoader.loadImages("res/Player/standing/", 15);

		imageSpeed = 2;

		hitBox = new Rectangle2D.Float((float) getObjectPosition().getX(), (float) getObjectPosition().getY(),
				getWidth(), getHeight());
	}

	// Player Movement:

	public void moveUp() {

		if (getObjectPosition().getY() - getHeight() / 2 > 0) {// prüft ob der spieler außerhalb des spielfelds ist

			getObjectPosition().setY(getObjectPosition().getY() - speed);// wenn nein dann move

			hitBox.setFrame(getObjectPosition().getX(), getObjectPosition().getY(), getWidth(), getHeight());
		}
	}

	public void moveDown(double spielflaechenHoehe) {
		if (getObjectPosition().getY() + getHeight() / 2 < spielflaechenHoehe) {// prüft ob der spieler außerhalb des
																				// spielfelds ist
			getObjectPosition().setY(getObjectPosition().getY() + speed); // wenn nein dann move

			hitBox.setFrame(getObjectPosition().getX(), getObjectPosition().getY(), getWidth(), getHeight());
		}
	}

	public void moveRight(double spielflaechenbreite) {
		if (getObjectPosition().getX() + getWidth() / 2 < spielflaechenbreite) {// prüft ob der spieler außerhalb des
																				// spielfelds ist
			getObjectPosition().setX(getObjectPosition().getX() + speed);// wenn nein dann move

			hitBox.setFrame(getObjectPosition().getX(), getObjectPosition().getY(), getWidth(), getHeight());
		}
	}

	public void moveLeft() {
		if (getObjectPosition().getX() - getWidth() / 2 > 0) {// prüft ob der spieler außerhalb des spielfelds ist
			getObjectPosition().setX(getObjectPosition().getX() - speed);// wenn nein dann move

			hitBox.setFrame(getObjectPosition().getX(), getObjectPosition().getY(), getWidth(), getHeight());
		}
	}
	// Player Movment ENDE

	public void attack() {
		if (master.getEnemys().isEmpty()) {
			nearestEnemy = null;
		}

		if (attackIntervall <= 0) {
			double distance = 99999999;
			for (int i = 0; i < master.getEnemys().size(); i++) {
				master.getEnemys().get(i).setEnemyColor(Color.RED);
				if (distance > master.getEnemys().get(i).distance(this.getObjectPosition())) {
					distance = master.getEnemys().get(i).distance(this.getObjectPosition());
					nearestEnemy = master.getEnemys().get(i);
				}
			}
			if (nearestEnemy != null) {
				attackNearestEnemy(nearestEnemy);
			}
			attackIntervall = attackSpeed;
		} else {
			attackIntervall -= 0.1;
		}
		if (nearestEnemy != null) {
			nearestEnemy.setEnemyColor(Color.ORANGE);
		}
	}

	public void attackNearestEnemy(Enemy nearestEnemy) {
		master.initPlayerShot(nearestEnemy, getObjectPosition());
	}

	@Override
	public void paintMe(Graphics g) {
		g.setColor(Color.blue);
		runningLeft = false;
		runningRight = false;
		runningUpDown = false;
		// zustände anpassen
		if (master.isPlayerMoveLeft() && !master.isPlayerMoveRight()) {
			runningLeft = true;
			lastMove=true;//true=rechts///false=left
		}
		if (master.isPlayerMoveRight() && !master.isPlayerMoveLeft()) {
			runningRight = true;
			lastMove=false;//true=rechts///false=left
		}
		if (master.isPlayerMoveDown() ^ master.isPlayerMoveUp()) {
			runningUpDown=true;
		}
		// bild zeichnen je nach zustand
		if (runningLeft||(lastMove&&runningUpDown)) {
			// abfolge für runningLeft zeichnen
			g.drawImage(runLeftImages[currentImage], 
					(int) getObjectPosition().getX() - (50) - master.getScroller().getGame_offsetX(),
					(int) getObjectPosition().getY() - (75) - master.getScroller().getGame_offsetY(), 
					100, 150, null);

			if (imageCounter == imageSpeed) {
				currentImage = (currentImage + 1) % runLeftImages.length;
				imageCounter = 0;
			} else {
				imageCounter++;
			}
		} else if (runningRight||(!lastMove&&runningUpDown)) {
			// abfolge für runningRight zeichnen
			g.drawImage(runRightImages[currentImage], 
					(int) getObjectPosition().getX() - (50) - master.getScroller().getGame_offsetX(),
					(int) getObjectPosition().getY() - (75) - master.getScroller().getGame_offsetY(),
					100, 150, null);

			if (imageCounter == imageSpeed) {
				currentImage = (currentImage + 1) % runRightImages.length;
				imageCounter = 0;
			} else {
				imageCounter++;
			}
		} else
			// abfolge für standing zeichnen
			g.drawImage(standingImages[currentImage], 
					(int) getObjectPosition().getX() - (50) - master.getScroller().getGame_offsetX(),
					(int) getObjectPosition().getY() - (75) - master.getScroller().getGame_offsetY(),
					100, 150, null);

		if (imageCounter == imageSpeed) {
			currentImage = (currentImage + 1) % standingImages.length;
			imageCounter = 0;
		} else {
			imageCounter++;
		}

		// zeichne Hitbox
		g.drawRect(
				(int) (hitBox.getMinX() - (hitBox.getWidth() / 2) - master.getScroller().getGame_offsetX()),
				(int) (hitBox.getMinY() - (hitBox.getHeight() / 2) - master.getScroller().getGame_offsetY()),
				(int) hitBox.getWidth(), 
				(int) hitBox.getHeight());
	}

//	// scrollt das Objekt um die Scrollgeschwindigkeit in X- bzw. Y-Richtung
//	// player hat diese methode nochmal extra damit die hitbox korrekt bewegt wird
//	
//		@Override
//		public void scrollObject(int speedX, int speedY) {
//			super.scrollObject(speedX, speedY); 
//			
//			hitBox.setFrame(getObjectPosition().getX(), getObjectPosition().getY(),
//					getWidth(), getHeight());
//		}

	// getter setter

	public Rectangle2D getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle2D hitBox) {
		this.hitBox = hitBox;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getLives() {
		return lives;
	}

	public void setLives(double lives) {
		this.lives = lives;
	}

	public double getMaxLives() {

		return maxLives;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(double attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public void setMaxLives(double maxLives) {
		this.maxLives = maxLives;
	}

}
