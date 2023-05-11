package util;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import entity.Enemy;
import entity.Player;

//Klasse zum Überprüfen von Kollisionen zwischen Feinden
public class CollisionDetector {

	//Methode zum Überprüfen von Kollisionen zwischen Feinden
	public void checkCollision(ArrayList<Enemy> enemies, Player player) {
		//Schleife durch die Liste der Feinde
		for (int i = 0; i < enemies.size(); i++) {
			//Überprüfe ob der aktuelle Feind mit dem Spieler kollidiert
			if (enemies.get(i).getHitBox().intersects(player.getHitBox())) {
				//Behandle die Kollision indem der Feind von dem Spieler weg bewegt wird
				handleCollision(enemies.get(i), player);
				
				//Verringere das Leben des Spielers um den Schaden des Feindes
				player.setLives(player.getLives() - enemies.get(i).getDamage());
				
			}
			//Schleife durch die Liste der Feinde
			for (int j = i + 1; j < enemies.size(); j++) {
				// Überprüfe ob der aktuelle Feind mit einem anderen Feind kollidiert
				if (enemies.get(i).getHitBox().intersects(enemies.get(j).getHitBox())) {
					// Behandle die Kollision indem die Feinde voneinander weg bewegt werden
					handleCollision(enemies.get(i), enemies.get(j));
				}
			}
		}
		//heilt den spieler jedet tick wenn er nicht schon maximale leben hat
		if (player.getLives()<player.getMaxLives()) {
			player.setLives(player.getLives() + 0.1); 
			}
	}

	//Methode zum Behandeln von Kollisionen zwischen Feinden
	private void handleCollision(Enemy enemy1, Enemy enemy2) {
		// Berechne den Winkel zwischen den Feinden
		double angle = Math.atan2(enemy2.getObjectPosition().getY() - enemy1.getObjectPosition().getY(),
				enemy2.getObjectPosition().getX() - enemy1.getObjectPosition().getX());
		// Berechne die Distanz die die Feinde voneinander entfernt sein sollen
		double distance = overlapingDistance(enemy1.getHitBox(),enemy2.getHitBox());
		// Setze den Bewegungswinkel und die Distanz des ersten Feindes
		enemy1.setMovingAngle(angle);
		enemy1.setMovingDistance(-(distance / 40));
		// Bewege den ersten Feind
		enemy1.makeMove();
		// Setze den Bewegungswinkel und die Distanz des zweiten Feindes
		// (entgegengesetzte Richtung)
		enemy2.setMovingAngle(angle + Math.PI);
		enemy2.setMovingDistance(-(distance / 40));
		// Bewege den zweiten Feind
		enemy2.makeMove();
	}
	
//	TODO player collision zieht enemys irgendwie in den player wenn sie von oben oder links kommen und 
//	ist zu früh wenn sie von unten oder rechts kommen
	
	// Methode zum Behandeln von Kollisionen zwischen Feinden und spieler
	private void handleCollision(Enemy enemy, Player player) {
		// Berechne den Winkel zwischen dem Feind und dem Spieler
		double angle = Math.atan2(player.getObjectPosition().getY() - enemy.getObjectPosition().getY(),
				player.getObjectPosition().getX() - enemy.getObjectPosition().getX());
		// Add 2π to the angle if it's negative
//	    if (angle < 0) {
//	        angle += 2 * Math.PI;
//	    }
		// Berechne die Distanz die der Feind vom Spieler entfernt sein sollte
		double distance = overlapingDistance(enemy.getHitBox(),player.getHitBox());
		enemy.setMovingAngle(angle);
		enemy.setMovingDistance(-distance/20);
		// Bewege den Feind
		enemy.makeMove();
	}
	
	private double overlapingDistance(Rectangle2D rect1,Rectangle2D rect2) {
		 // Mittelpunkte der Rechtecke berechnen
        Point2D center1 = new Point2D.Double(rect1.getCenterX(), rect1.getCenterY());
        Point2D center2 = new Point2D.Double(rect2.getCenterX(), rect2.getCenterY());

        // Linie zwischen den Mittelpunkten berechnen
        Line2D centerLine = new Line2D.Double(center1, center2);

        // Schnittpunkte der Linie mit den Rechtecken berechnen
        Point2D.Double intersection1 = new Point2D.Double();
        Point2D.Double intersection2 = new Point2D.Double();
        
        intersection1 = computeIntersectionPoint(centerLine,rect1);
        intersection2 = computeIntersectionPoint(centerLine,rect2);
        
        // Distanz zwischen den Schnittpunkten berechnen und zurückgeben
        if (intersection1==null || intersection2==null) {
        	return center1.distance(center2);
        } else {
        	return intersection1.distance(intersection2);
        }
    }
	
	private Point2D.Double computeIntersectionPoint(Line2D line,Rectangle2D rect) {

        double minX = rect.getMinX();
        double minY = rect.getMinY();
        double maxX = rect.getMaxX();
        double maxY = rect.getMaxY();

        Point2D intersectionPoint = null;
        
        //4 lines of the rectangle
        Line2D.Double lineOben = new Line2D.Double(minX,minY,maxX,minY); 
        Line2D.Double lineUnten = new Line2D.Double(minX,maxY,maxX,maxY); 
        Line2D.Double lineRechts = new Line2D.Double(maxX,minY,maxX,maxY); 
        Line2D.Double lineLinks = new Line2D.Double(minX,minY,minX,maxY); 

        // check for intersection with top line of rectangle
        if (line.intersectsLine(lineOben)) {
            intersectionPoint = intersectionPoint(line,lineOben);
        }

        // check for intersection with bottom line of rectangle
        if (line.intersectsLine(lineUnten)) {
            intersectionPoint = intersectionPoint(line,lineUnten);
        }

        // check for intersection with right line of rectangle
        if (line.intersectsLine(lineRechts)) {
            intersectionPoint = intersectionPoint(line,lineRechts);
        }

        // check for intersection with left line of rectangle
        if (line.intersectsLine(lineLinks)) {
            intersectionPoint = intersectionPoint(line,lineLinks);
        }

        return (Double) intersectionPoint;
	}
	
	private Point2D intersectionPoint(Line2D line1, Line2D line2) {
		
		double x1 = line1.getX1();
	    double y1 = line1.getY1();
	    double x2 = line1.getX2();
	    double y2 = line1.getY2();
	    double x3 = line2.getX1();
	    double y3 = line2.getY1();
	    double x4 = line2.getX2();
	    double y4 = line2.getY2();
	    
	    double x = x1 + (((x3 - x1) * (y4 - y3) - (y3 - y1) * (x4 - x3)) / ((x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3))) * (x2 - x1);
	    double y = y1 + (((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) / ((x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3))) * (y2 - y1);
	    
	    return new Point2D.Double(x,y);
	}
}