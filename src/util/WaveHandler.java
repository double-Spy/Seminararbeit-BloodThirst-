package util;

import java.awt.Dimension;

import entity.Enemy;
import enums.Gamestate;
import gameState.Running;


public class WaveHandler {
	private Running master;
	private Dimension prefSize;
	
	private int waveTimer;
	private int currentWave;
	private int spawnIntervall;
	
	private EnemySpawner enemySpawner;

	public WaveHandler(Running master , Dimension prefSize) {
		this.master = master;
		this.prefSize = prefSize;
		
		enemySpawner = new EnemySpawner(this);
		
		currentWave=1;
	}
	
	
	public void update() {
		if (waveTimer<=0) {
			Gamestate.state=Gamestate.STORE;
		}else {
			enemySpawner.update(currentWave);
		}
		
	}
	
	public void initWave(int wave) {
		waveTimer=30;
	}

	
	public void nextWave() {
		
		master.getEnemys().clear();
		master.getP().setObjectPosition(new Coordinate(master.getGameMaster().getPrefSize().getWidth() / 2, master.getGameMaster().getPrefSize().getHeight() / 2));
		
		currentWave += 1;
		
		initWave(currentWave);
		
		
	}
	
	public void substractWaveTime() {
		waveTimer--;
	}
	
	//getter setter
	public double getWaveTimer() {
		return waveTimer;
	}

	public Dimension getPrefSize() {
		return prefSize;
	}


	public void setPrefSize(Dimension prefSize) {
		this.prefSize = prefSize;
	}


	public int getCurrentWave() {
		return currentWave;
	}


	public void setCurrentWave(int currentWave) {
		this.currentWave = currentWave;
	}


	public int getSpawnIntervall() {
		return spawnIntervall;
	}


	public void setSpawnIntervall(int spawnIntervall) {
		this.spawnIntervall = spawnIntervall;
	}


	public void setWaveTimer(int waveTimer) {
		this.waveTimer = waveTimer;
	}


	public Running getMaster() {
		return master;
	}


	public void setMaster(Running master) {
		this.master = master;
	}
	
	
	
}
