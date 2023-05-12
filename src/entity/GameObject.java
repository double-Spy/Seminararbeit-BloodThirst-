package entity;

import java.awt.geom.Rectangle2D;

import util.Coordinate;

public abstract class GameObject{
	
	private Rectangle2D hitBox;
	private double movingAngle;   
	private double movingDistance;
	
	public GameObject (Rectangle2D hitBox) {
		this.hitBox=hitBox;
	}
	
	public static Coordinate polarToCartesianCoordinates(double angle) {
		double x = Math.cos(angle);
		double y = Math.sin(angle);
		
		return new Coordinate(x,y);
	}
	
	public void moveGameObject() {
		Coordinate direction = polarToCartesianCoordinates(movingAngle);		
		
//		objectPosition.setX(objectPosition.getX() + direction.getX()*movingDistance);		
//		objectPosition.setY(objectPosition.getY() + direction.getY()*movingDistance);
		
		hitBox.setFrame(
				hitBox.getX() + direction.getX()*movingDistance,
				hitBox.getY() + direction.getY()*movingDistance,
				hitBox.getWidth(),
				hitBox.getHeight());
		
	}
	
	public Coordinate getFutureposition() {
		Coordinate direction = polarToCartesianCoordinates(movingAngle);
		
		return new Coordinate((hitBox.getX() + direction.getX()*movingDistance),(hitBox.getY() + direction.getY()*movingDistance));
	}
	
	public void makeMove() {
		moveGameObject();
	}
	
	public abstract void paintMe(java.awt.Graphics g);
	
	public Coordinate getPosition() {
		return new Coordinate(getHitBox().getX(),getHitBox().getY());
	}
	
	public void setPosition(Coordinate newPosition) {
		hitBox.setFrame(
				newPosition.getX(),
				newPosition.getY(),
				hitBox.getWidth(),
				hitBox.getHeight());
	}
	
	//Getter Setter
	public double getMovingAngle() {
		return movingAngle;
	}

	public void setMovingAngle(double movingAngle) {
		this.movingAngle = movingAngle;
	}

	public double getMovingDistance() {
		return movingDistance;
	}

	public void setMovingDistance(double movingDistance) {
		this.movingDistance = movingDistance;
	}

	public Rectangle2D getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle2D hitBox) {
		this.hitBox = hitBox;
	}
}

