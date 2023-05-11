package entity;

import util.Coordinate;

public abstract class GameObject{
	
	private int width;
	private int height;
	private double movingAngle;   
	private double movingDistance;
	private Coordinate objectPosition;
	
	public GameObject (Coordinate objectPosition, int width, int height) {
		this.objectPosition=objectPosition;
		this.width=width;
		this.height=height;
	}
	
	
	
	public static Coordinate polarToCartesianCoordinates(double angle) {
		double x = Math.cos(angle);
		double y = Math.sin(angle);
		
		return new Coordinate(x,y);
	}
	
	public void moveGameObject() {
		Coordinate direction = polarToCartesianCoordinates(movingAngle);		
		
		objectPosition.setX(objectPosition.getX() + direction.getX()*movingDistance);		
		objectPosition.setY(objectPosition.getY() + direction.getY()*movingDistance);
		
	}
	
	public Coordinate getFutureposition() {
		Coordinate direction = polarToCartesianCoordinates(movingAngle);
		
		return new Coordinate((objectPosition.getX() + direction.getX()*movingDistance),(objectPosition.getY() + direction.getY()*movingDistance));
	}
	
	public void makeMove() {
		moveGameObject();
	}
	
	public void scrollObject(int speedX, int speedY){
		// GameObjects werden gescrollt
		// Scrollen in X-Richtung erfolgt getrennt vom Scrollen in Y-Richtung
		
		objectPosition.setX(objectPosition.getX()+ speedX);
		objectPosition.setY(objectPosition.getY()+ speedY);
	}
	
	public abstract void paintMe(java.awt.Graphics g);
	
	
	//Getter Setter
	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}

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

	public Coordinate getObjectPosition() {
		return objectPosition;
	}

	public void setObjectPosition(Coordinate objectPosition) {
		this.objectPosition = objectPosition;
	}
}

