package util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Rectangle2D;

import entity.Player;
import enums.PlayerMovement;
import gameState.Running;

public class Scroller {
	
	private Player p;
	private Dimension prefSize;
	private Dimension mapSize;
	private Running master;
	
	private int game_offsetX; 
	private int game_offsetY; 
	
	private int leftBorder; // relativ zum linken Rand des Spiels
	private int rightBorder; // relativ zum rechten Rand des Spiels
	private int upperBorder; // relativ zum oberen Rand des Spiels
	private int lowerBorder; // relativ zum unteren Rand des Spiels
	
	private int playerBoxHoehe = 200;
	private int playerBoxBreite	= 200;
	private Rectangle2D playerBox;
	
	public Scroller(Player p,Dimension prefSize, Dimension mapSize, Running master) {
		this.p=p;
		this.prefSize=prefSize;
		this.mapSize=mapSize;
		this.master=master;

		game_offsetX = 0;
		game_offsetY = 0;
		
		playerBox = new Rectangle(
				200,
				200,
					playerBoxBreite,
					playerBoxHoehe);
		
		leftBorder=(int) (.6 * prefSize.getWidth());
		rightBorder=(int) (.4 * prefSize.getWidth());
		upperBorder=(int) (.4 * prefSize.getHeight());
		lowerBorder=(int) (.6 * prefSize.getHeight());
	}
	
	public void update() {
		
		int playerX = (int) p.getHitBox().getCenterX();
		int playerY = (int) p.getHitBox().getCenterY();
		
		int centerX = (int) (prefSize.getWidth() / 2);
		int centerY = (int) (prefSize.getHeight() / 2);
		
		int distanceMiddleX = playerX - centerX - game_offsetX;
		int distanceMiddleY = playerY - centerY - game_offsetY;
		
		game_offsetX += distanceMiddleX/40;
		game_offsetY += distanceMiddleY/40;
	}
	
	public void game_offsetXPlus(double xDistanze) {
		game_offsetX += xDistanze;
	}
	
	public void game_offsetYPlus(double yDistanze) {
		game_offsetY += yDistanze;
	}
	
	//getter setter

	public Dimension getMapSize() {
		return mapSize;
	}

	public void setMapSize(Dimension mapSize) {
		this.mapSize = mapSize;
	}

	public int getGame_offsetX() {
		return game_offsetX;
	}

	public void setGame_offsetX(int game_offsetX) {
		this.game_offsetX = game_offsetX;
	}

	public int getGame_offsetY() {
		return game_offsetY;
	}

	public void setGame_offsetY(int game_offsetY) {
		this.game_offsetY = game_offsetY;
	}

	public int getLeftBorder() {
		return leftBorder;
	}

	public void setLeftBorder(int leftBorder) {
		this.leftBorder = leftBorder;
	}

	public int getRightBorder() {
		return rightBorder;
	}

	public void setRightBorder(int rightBorder) {
		this.rightBorder = rightBorder;
	}

	public int getUpperBorder() {
		return upperBorder;
	}

	public void setUpperBorder(int upperBorder) {
		this.upperBorder = upperBorder;
	}

	public int getLowerBorder() {
		return lowerBorder;
	}

	public void setLowerBorder(int lowerBorder) {
		this.lowerBorder = lowerBorder;
	}
	
	

}
