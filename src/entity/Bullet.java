package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import gameState.Running;
import main.GameMaster;
import util.Coordinate;

//bullets werden einmal erzeugt und bewegen sich auf eine feste Position 
//wenn sie diese erreichen löschen sie sich selbst
public class Bullet extends GameObject{
	
	private Coordinate zielPosition;
	
	private boolean outOfBounds = false;
	
	private Running master;
	
	public Bullet(Coordinate spawnPosition, Enemy zielGegner, Running master) {
		super(new Rectangle(
				(int) spawnPosition.getX(),
				(int) spawnPosition.getY(),
				2,
				2));
		this.master = master;
		zielPosition=zielGegner.getCenter();
		//movieng angle wid einemal definiert und dann wird sich nur noch in diese richtung Bewegt
		super.setMovingAngle(Math.atan2((getHitBox().getY() - zielPosition.getY()),
							(getHitBox().getX() - zielPosition.getX())));
	}
	
	public void makeMove() {
		
		super.setMovingDistance(-10);
		super.makeMove();
		
		
		for (Enemy enemy : master.getEnemys())
			
			if (enemy.getHitBox().intersectsLine(getHitBox().getX(), getHitBox().getY(), getFutureposition().getX(),getFutureposition().getY())) {
				
				enemy.getHit(master.getP().getDamage());
				
//				gameMaster.removePlayerShot(this);
		}
		
		if (		getHitBox().getX()<0
				||	getHitBox().getY()<0
				||	getHitBox().getX()>master.getGameMaster().getMapSize().getWidth()
				||	getHitBox().getY()>master.getGameMaster().getMapSize().getHeight()) {
			outOfBounds=true;
		}
	}

	@Override
	public void paintMe(Graphics g) {
		g.setColor(Color.BLACK);

		g.fillOval(
				(int)getHitBox().getX()-master.getScroller().getGame_offsetX(),
				(int)getHitBox().getY()-master.getScroller().getGame_offsetY(),
				7,
				7);
		
	}

	public boolean isOutOfBounds() {
		return outOfBounds;
	}

	public void setOutOfBounds(boolean outOfBounds) {
		this.outOfBounds = outOfBounds;
	}
	
	

}
