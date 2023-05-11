package util;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import entity.Enemy;

//klasse die sich um das spwnen von gegnern abhängig von der welle und schwierigkeit kümmert
public class EnemySpawner {

	private int wave;

	private int difficulty = 10;
	
	private int[] enemyCosts = new int[] {1, 3, 4, 7, 10};

	private double spawnIntervall;

	private WaveHandler waveHandler;

	private Rectangle2D spawnBereich;

	public EnemySpawner(WaveHandler waveHandler) {
		this.waveHandler = waveHandler;
		spawnBereich = new Rectangle(0, 0, 300, 300);
	}

	public void update(int wave) {
		this.wave=wave;
		if (spawnIntervall <= 0) {
			spawnEnemies();
			spawnIntervall = getSpawnInterval(difficulty, wave);
		} else
			spawnIntervall -= 0.1;
	}
	
	public void spawnEnemies() {
//		neue position für den spawnBereich festlegen
		spawnBereich.setFrame(
				Math.random() * waveHandler.getMaster().getGameMaster().getMapSize().getWidth(),
				Math.random() * waveHandler.getMaster().getGameMaster().getMapSize().getHeight(),
				spawnBereich.getWidth(), spawnBereich.getHeight());
//		budget Berechnen
		int budget = wave*difficulty;
		
	    int remainingBudget = budget;
	    int remainingEnemies = rundomNumber(1, wave*difficulty);
	    
//	    System.out.println("Number of enemies remaining"+remainingEnemies);
//        System.out.println("Budget: "+remainingBudget);
        
	    while (remainingEnemies > 0 && remainingBudget > 0) {
	        int maxCost = Math.min(remainingBudget / remainingEnemies, maxEnemyCost(enemyCosts));
	        int cost = chooseRandomCost(maxCost, enemyCosts);
	        int x = (int) (spawnBereich.getX() + Math.random() * spawnBereich.getWidth());
	        int y = (int) (spawnBereich.getY() + Math.random() * spawnBereich.getHeight());
	        spawnEnemyWithCostAtPosition(cost, x, y);
	        remainingBudget -= cost;
	        remainingEnemies--;
	    }
	    
	}

	private int chooseRandomCost(int maxCost, int[] enemyCosts) {
	    int[] validCosts = Arrays.stream(enemyCosts).filter(cost -> cost <= maxCost).toArray();
	    if (validCosts.length > 0) {
	        return validCosts[(int) (Math.random() * validCosts.length)];
	    } else {
	        return enemyCosts[(int) (Math.random() * enemyCosts.length)];
	    }
	}

	private int maxEnemyCost(int[] enemyCosts) {
	    return Arrays.stream(enemyCosts).max().getAsInt();
	}

	public void spawnEnemyWithCostAtPosition(int cost, int xPosition, int yPosition) {
//		System.out.println("enemy position etc: "+cost);
	    switch (cost) {
	        case 1:
	            // Spawn enemy type 1 at (xPosition, yPosition)
	        	waveHandler.getMaster().getGameMaster().getRunning().getEnemys().add(
	        			new Enemy(new Coordinate(xPosition,yPosition), 20, 50, 1, 20,waveHandler.getMaster().getGameMaster()));
	            break;
	        case 3:
	            // Spawn enemy type 2 at (xPosition, yPosition)
	        	waveHandler.getMaster().getGameMaster().getRunning().getEnemys().add(
	        			new Enemy(new Coordinate(xPosition,yPosition), 30, 30, 2, 50,waveHandler.getMaster().getGameMaster()));
	            break;
	        case 4:
	            // Spawn enemy type 3 at (xPosition, yPosition)
	        	waveHandler.getMaster().getGameMaster().getRunning().getEnemys().add(
	        			new Enemy(new Coordinate(xPosition,yPosition), 40, 40, 5, 50,waveHandler.getMaster().getGameMaster()));
	            break;
	        case 7:
	            // Spawn enemy type 4 at (xPosition, yPosition)
	        	waveHandler.getMaster().getGameMaster().getRunning().getEnemys().add(
	        			new Enemy(new Coordinate(xPosition,yPosition), 70, 70, 3, 200,waveHandler.getMaster().getGameMaster()));
	            break;
	        case 10:
	            // Spawn enemy type 5 at (xPosition, yPosition)
	        	waveHandler.getMaster().getGameMaster().getRunning().getEnemys().add(
	        			new Enemy(new Coordinate(xPosition,yPosition), 100, 100, 10, 2000,waveHandler.getMaster().getGameMaster()));
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid enemy cost: " + cost);
	    }
	}

	public double getSpawnInterval(int difficulty, int wave) {
		double baseInterval = 100; // Basis-Spawnintervall in milliSekunden
		double difficultyMultiplier = 1.0 + difficulty * 0.1;
		double waveMultiplier = 1.0 + wave * 0.05;
		double interval = baseInterval / difficultyMultiplier / waveMultiplier;

		// füge zufälligen Faktor hinzu, der zwischen -1 und 1 liegt
		double randomFactor = Math.random() * 2.0 - 1.0;
		interval *= (1.0 + 0.2 * randomFactor); // erhöhe oder verringere um bis zu 20%

		return interval;
	}

	public int rundomNumber(int minValue, int maxValue) {
		return (int) (Math.random() * (maxValue - minValue + 1) + minValue);
	}
}