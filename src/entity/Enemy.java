package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.GameMaster;
import util.Coordinate;

public class Enemy extends GameObject {

	private double damage = 1; // der schaden des enemys
	private double lives;
	public boolean death;
	public double speed = 1;
	private Rectangle2D hitBox;
	
	private GameMaster gameMaster;

	private Color enemyColor;

	public Enemy(Coordinate objectPosition, int width, int height, double damage, double lives, GameMaster gameMaster) {

		super(objectPosition, width, height);
		this.gameMaster = gameMaster;
		this.damage=damage;
		this.lives=lives;
		
		hitBox = new Rectangle2D.Float((float) objectPosition.getX(), (float) objectPosition.getY(), width, height);
		enemyColor = Color.RED;
	}
	
	public void update(Coordinate playerPosition) {
		if (lives<=0) {
			enemyColor=Color.BLACK;
			death=true;
		} else {
			move(playerPosition);
		}
	}
	
	public void getHit(double damage) {
		lives=lives-damage;
	}

	// Movement:
	// wird jeden tick aufgerufen und moves den enemy zum spieler
	public void move(Coordinate playerPosition) {

		super.setMovingAngle(Math.atan2((getObjectPosition().getY() - playerPosition.getY()),
				(getObjectPosition().getX() - playerPosition.getX())));

		super.setMovingDistance(-speed);

		super.makeMove();

		hitBox.setFrame(getObjectPosition().getX() - getWidth() / 2, getObjectPosition().getY() - getHeight() / 2,
				getWidth(), getHeight());
	}

	@Override
	public void paintMe(Graphics g) {
		// Graphics2D g2d = (Graphics2D) g;
		g.setColor(enemyColor);

		// zeichne hitbox
		g.fillRect(
				(int) hitBox.getMinX()- gameMaster.getRunning().getScroller().getGame_offsetX(),
				(int) hitBox.getCenterY()- gameMaster.getRunning().getScroller().getGame_offsetY(),
				(int) hitBox.getWidth(),
				(int) hitBox.getHeight());
//		g.fillRect(
//				(int) getObjectPosition().getX() - ((int) getWidth() / 2)- gameMaster.getRunning().getScroller().getGame_offsetX(),
//				(int) getObjectPosition().getY() - ((int) getHeight() / 2) - gameMaster.getRunning().getScroller().getGame_offsetY(),
//				(int) getWidth(),
//				(int) getHeight());
		
		g.setFont(new Font(Font.SERIF, Font.BOLD, 10));
		g.setColor(Color.RED);
		g.drawString(""+lives,
				(int) (hitBox.getCenterX()-(g.getFontMetrics().stringWidth(""+lives)) - gameMaster.getRunning().getScroller().getGame_offsetX()),
				(int) (hitBox.getCenterY()-(g.getFontMetrics().getHeight()) - gameMaster.getRunning().getScroller().getGame_offsetY())); 
					//zentriert den text egal wie lang er ist
	}

	public double distance(Coordinate positionToCheck) {
		// berechnet pytagoras aus delta x und delta y => abstand
		// math.sqrt => 2. wurzel
		// math.pow => "hoch"
		// math.abs => betrag
		return Math.sqrt(Math.pow(Math.abs(positionToCheck.getY() - getObjectPosition().getY()), 2)
				+ Math.pow(Math.abs(positionToCheck.getX() - getObjectPosition().getX()), 2));
	}
	
// getter setter
	
	public Rectangle2D getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle2D hitBox) {
		this.hitBox = hitBox;
	}

	public Color getEnemyColor() {
		return enemyColor;
	}

	public void setEnemyColor(Color enemyColor) {
		this.enemyColor = enemyColor;
	}

	public Coordinate getCenter() {
		return new Coordinate((int)hitBox.getCenterX(),(int) hitBox.getCenterY());
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public double getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}