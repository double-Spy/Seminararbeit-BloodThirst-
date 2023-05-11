package util;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import enums.Gamestate;
import gameState.Store;
import main.GameMaster;

public class ButtonHandler {

	private GameMaster gameMaster;
	
	private ButtonImages buttonImages;
	
//	Running
	private Button pauseButton;

//	Menu
	private Button playButton;
	private Button settingsButton;
	private Button leaveButton;
//	Death

//	Store
	private Button reroll;
	private Button nextWave;
	private Button item1;
	private Button item2;
	private Button item3;
	private Button item4;

//	Paused
	private Button menuButton;
	private Button playKleinButton;

	public ButtonHandler(GameMaster gameMaster) {
		this.gameMaster = gameMaster;
		buttonImages = new ButtonImages();
		initButtons();
	}

	private void initButtons() {

		buttonImages = new ButtonImages();

		// butten wird erstellt mit
		// (name,x-position,y-position,hoehe,breite,normalimage,hoverimage)
		String name = "Play";
		int buttonHoehe = (int) (gameMaster.getPrefSize().getHeight() * 0.0694444444444444);
		int buttonBreite = (int) (gameMaster.getPrefSize().getWidth() * 0.078125);
		playButton = new Button(
				// in der mitte des screens+offset nach oben
				name, ((int) gameMaster.getPrefSize().getWidth() / 2) - (buttonBreite / 2), // mitte der breite - halbe breite des
																			// Buttons
				((int) gameMaster.getPrefSize().getHeight() / 2 - 200) - (buttonHoehe / 2), // mitte der hoehe - halbe hoehe des Buttons
				buttonHoehe, buttonBreite, buttonImages.ButtonNormal(name), buttonImages.ButtonHover(name));

		name = "Settings";
		settingsButton = new Button(
				// in der mitte des screens
				name, ((int) gameMaster.getPrefSize().getWidth() / 2) - (buttonBreite / 2),
				((int) gameMaster.getPrefSize().getHeight() / 2) - (buttonHoehe / 2), buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal(name), buttonImages.ButtonHover(name));

		name = "Leave";
		leaveButton = new Button(
				// in der mitte des screens+ offset nach unten
				name, ((int) gameMaster.getPrefSize().getWidth() / 2) - (buttonBreite / 2),
				((int) gameMaster.getPrefSize().getHeight() / 2 + 200) - (buttonHoehe / 2), buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal(name), buttonImages.ButtonHover(name));

		// ButtonsRunning
		name = "Pause";
		buttonHoehe = 50;
		buttonBreite = 50;
		pauseButton = new Button(
				// oben links
				name, +20, 20, buttonHoehe, buttonBreite, buttonImages.ButtonNormal(name),
				buttonImages.ButtonHover(name));

		// ButtonsRunning+Paused

		name = "PlayKlein";
		buttonHoehe = 50;
		buttonBreite = 50;
		playKleinButton = new Button(
				// oben links
				name, 20, 20, buttonHoehe, buttonBreite, buttonImages.ButtonNormal(name),
				buttonImages.ButtonHover(name));

		name = "Menu";
		buttonHoehe = (int) (gameMaster.getPrefSize().getHeight() * 0.0694444444444444);
		buttonBreite = (int) (gameMaster.getPrefSize().getWidth() * 0.078125);
		menuButton = new Button(
				// in der mitte des screens
				name, ((int) gameMaster.getPrefSize().getWidth() / 2) - (buttonBreite / 2),
				((int) gameMaster.getPrefSize().getHeight() / 2) - (buttonHoehe / 2), buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal(name), buttonImages.ButtonHover(name));
		
		buttonHoehe = (int)(gameMaster.getPrefSize().getHeight()/10);
		buttonBreite = (int)(gameMaster.getPrefSize().getWidth()/8);
		
		
		
		// button1
		name = "1";
		buttonHoehe = (int)(gameMaster.getPrefSize().getHeight()*0.4);
		buttonBreite = (int)(gameMaster.getPrefSize().getWidth()*0.15);
		item1 = new Button(
				name, 
				(int) (gameMaster.getPrefSize().getWidth()*0.07), 
				(int) (gameMaster.getPrefSize().getHeight() / 2) - (buttonHoehe / 2), 
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));

		// button2
		name = "1";
		item2 = new Button(
				name, 
				(int) ((item1.getBounds().getMinX())+(item1.getBounds().getWidth())+(gameMaster.getPrefSize().getWidth()*0.03)),
				(int) (gameMaster.getPrefSize().getHeight() / 2) - (buttonHoehe / 2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));

		// button3
		name = "1";
		item3 = new Button(
				name,
				(int) ((item2.getBounds().getMinX())+(item2.getBounds().getWidth())+(gameMaster.getPrefSize().getWidth()*0.03)),
				(int) (gameMaster.getPrefSize().getHeight() / 2) - (buttonHoehe / 2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));

		// button4
		name = "1";
		item4 = new Button(		
				name, 
				(int) ((item3.getBounds().getMinX())+(item3.getBounds().getWidth())+(gameMaster.getPrefSize().getWidth()*0.03)),
				(int) (gameMaster.getPrefSize().getHeight() / 2) - (buttonHoehe / 2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));
		
		buttonHoehe = (int)(gameMaster.getPrefSize().getHeight()/10);
		buttonBreite = (int)(gameMaster.getPrefSize().getWidth()/8);
		
		
		//rerollbutton
		
		name = "1"; // muss noch geändert werden
		reroll = new Button(
				name, 
				(int) (item4.getBounds().getCenterX()-buttonBreite/2),
				(int) (item4.getBounds().getMinY()-gameMaster.getPrefSize().getHeight()*0.2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));
		
		//nextWaveButton
		name = "1"; //muss noch geändert werden
		nextWave = new Button(						
				name, 
				(int) (gameMaster.getPrefSize().getWidth()/2-buttonBreite/2),
				(int) (gameMaster.getPrefSize().getHeight()-gameMaster.getPrefSize().getHeight()*0.2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));

	}

	public void update() {

	}

	public void paint(Graphics g) {

		switch (Gamestate.state) {
		case RUNNING: {
			paintRunningButtons(g);
			break;
		}
		case MENU: {
			paintMenuButtons(g);
			break;
		}
		case DEATH: {
			paintDeathButtons(g);
			break;
		}
		case STORE: {
			paintStoreButtons(g);
			break;
		}
		case PAUSED: {
			paintPausedButtoins(g);
			break;
		}
		}
	}

	public void paintRunningButtons(Graphics g) {
		pauseButton.paintMe(g);
	}

	public void paintMenuButtons(Graphics g) {
		playButton.paintMe(g);
		settingsButton.paintMe(g);
		leaveButton.paintMe(g);
	}

	public void paintDeathButtons(Graphics g) {

	}

	public void paintStoreButtons(Graphics g) {
		reroll.paintMe(g);
		nextWave.paintMe(g);
		item1.paintMe(g);
		item2.paintMe(g);
		item3.paintMe(g);
		item4.paintMe(g);
	}

	public void paintPausedButtoins(Graphics g) {
		menuButton.paintMe(g);
		playKleinButton.paintMe(g);
	}

	public void checkPress(MouseEvent e) {

		switch (Gamestate.state) {
		case RUNNING: {
			checkPressRunningButtons(e);
			break;
		}
		case MENU: {
			checkPressMenuButtons(e);
			break;
		}
		case DEATH: {
			checkPressDeathButtons(e);
			break;
		}
		case STORE: {
			checkPressStoreButtons(e);
			break;
		}
		case PAUSED: {
			checkPressPausedButtoins(e);
			break;
		}
		}
	}

	public void checkPressRunningButtons(MouseEvent e) {
		if (pauseButton.check(e)) {
			pauseButton.setPress(true);
		}
	}

	public void checkPressMenuButtons(MouseEvent e) {
		if (playButton.check(e)) {
			playButton.setPress(true);
		}
		if (settingsButton.check(e)) {
			settingsButton.setPress(true);
		}
		if (leaveButton.check(e)) {
			leaveButton.setPress(true);
		}
	}

	public void checkPressDeathButtons(MouseEvent e) {

	}

	public void checkPressStoreButtons(MouseEvent e) {
		if (reroll.check(e)) {
			reroll.setPress(true);
		}
		if (nextWave.check(e)) {
			nextWave.setPress(true);
		}
		if (item1.check(e)) {
			item1.setPress(true);
		}
		if (item2.check(e)) {
			item2.setPress(true);
		}
		if (item3.check(e)) {
			item3.setPress(true);
		}
		if (item4.check(e)) {
			item4.setPress(true);
		}
	}

	public void checkPressPausedButtoins(MouseEvent e) {
		if (menuButton.check(e)) {
			menuButton.setPress(true);
		}
		if (playKleinButton.check(e)) {
			playKleinButton.setPress(true);
		}
	}

	public void checkRelease(MouseEvent e) {

		switch (Gamestate.state) {
		case RUNNING: {
			checkReleaseRunningButtons(e);
			break;
		}
		case MENU: {
			checkReleaseMenuButtons(e);
			break;
		}
		case DEATH: {
			checkReleaseDeathButtons(e);
			break;
		}
		case STORE: {
			checkReleaseStoreButtons(e);
			break;
		}
		case PAUSED: {
			checkReleasePausedButtoins(e);
			break;
		}
		}
	}

	public void checkReleaseRunningButtons(MouseEvent e) {
		if (pauseButton.isPress()&&pauseButton.check(e)) {
			Gamestate.state = Gamestate.PAUSED;
		} else {
			pauseButton.setPress(false);
		}
	}

	public void checkReleaseMenuButtons(MouseEvent e) {
		if (playButton.isPress()&&playButton.check(e)) {
			gameMaster.getRunning().initGame();;
		} else {
			playButton.setPress(false);
		}
		if (settingsButton.isPress()&&settingsButton.check(e)) {

		} else {
			settingsButton.setPress(false);
		}
		if (leaveButton.isPress()&&leaveButton.check(e)) {
			System.exit(1);
		} else {
			leaveButton.setPress(false);
		}
	}

	public void checkReleaseDeathButtons(MouseEvent e) {

	}

	public void checkReleaseStoreButtons(MouseEvent e) {
		if (reroll.isPress()&&reroll.check(e)) {
			gameMaster.getStore().reroll();
		} else {
			reroll.setPress(false);
		}
		if (nextWave.isPress()&&nextWave.check(e)) {
			gameMaster.getRunning().getWaveHandler().nextWave();
		} else {
			nextWave.setPress(false);
		}
		if (item1.isPress()&&item1.check(e)) {
			gameMaster.getStore().item1();
		} else {
			item1.setPress(false);
		}
		if (item2.isPress()&&item2.check(e)) {
			gameMaster.getStore().item2();
		} else {
			item2.setPress(false);
		}
		if (item3.isPress()&&item3.check(e)) {
			gameMaster.getStore().item3();
		} else {
			item3.setPress(false);
		}
		if (item4.isPress()&&item4.check(e)) {
			gameMaster.getStore().item4();
		} else {
			item4.setPress(false);
		}
	}

	public void checkReleasePausedButtoins(MouseEvent e) {

		if(menuButton.isPress()&&menuButton.check(e)) {
			Gamestate.state = Gamestate.MENU;
		} else {
			menuButton.setPress(false);
		}
		if(playKleinButton.isPress()&&playKleinButton.check(e)) {
			Gamestate.state = Gamestate.RUNNING;
		} else {
			playKleinButton.setPress(false);
		}
		
	}

	public void checkHover(MouseEvent e) {

		switch (Gamestate.state) {
		case RUNNING: {
			checkHoverRunningButtons(e);
			break;
		}
		case MENU: {
			checkHoverMenuButtons(e);
			break;
		}
		case DEATH: {
			checkHoverDeathButtons(e);
			break;
		}
		case STORE: {
			checkHoverStoreButtons(e);
			break;
		}
		case PAUSED: {
			checkHoverPausedButtoins(e);
			break;
		}
		}
	}

	public void checkHoverRunningButtons(MouseEvent e) {
		if (pauseButton.check(e)) {
			pauseButton.setHover(true);
		} else {pauseButton.setHover(false);}
	}

	public void checkHoverMenuButtons(MouseEvent e) {
		if (playButton.check(e)) {
			playButton.setHover(true);
		} else {playButton.setHover(false);}
		
		if (settingsButton.check(e)) {
			settingsButton.setHover(true);
		} else {settingsButton.setHover(false);}
		
		if (leaveButton.check(e)) {
			leaveButton.setHover(true);
		} else {leaveButton.setHover(false);}
	}

	public void checkHoverDeathButtons(MouseEvent e) {

	}

	public void checkHoverStoreButtons(MouseEvent e) {
		if (item1.check(e)) {
			item1.setHover(true);
		} else {item1.setHover(false);}
		
		if (item2.check(e)) {
			item2.setHover(true);
		}else {item2.setHover(false);}
		
		if (item3.check(e)) {
			item3.setHover(true);
		}else {item3.setHover(false);}
		
		if (item4.check(e)) {
			item4.setHover(true);
		}else {item4.setHover(false);}
		
		if (reroll.check(e)) {
			reroll.setHover(true);
		}else {reroll.setHover(false);}
		
		if (nextWave.check(e)) {
			nextWave.setHover(true);
		}else {nextWave.setHover(false);}
	}

	public void checkHoverPausedButtoins(MouseEvent e) {
		if (menuButton.check(e)) {
			menuButton.setHover(true);
		} else {menuButton.setHover(false);}
		
		if (playKleinButton.check(e)) {
			playKleinButton.setHover(true);
		} else {playKleinButton.setHover(false);}
	}
}
