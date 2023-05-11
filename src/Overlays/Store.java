package Overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import gameState.Running;
import util.*;

public class Store {

	private Button itemButton1;
	private Button itemButton2;
	private Button itemButton3;
	private Button itemButton4;

	private boolean bought1;
	private boolean bought2;
	private boolean bought3;
	private boolean bought4;

	private Button reRollButton;
	private Button nextWaveButton;

	private Running master;
	private Dimension prefSize;
	private ButtonImages buttonImages;
	private int item;

	public Store(Running master, Dimension pefSize) {
		
		System.out.println("store erstellen");
		
		this.master = master;
		this.prefSize = pefSize;
		
		buttonImages = new ButtonImages();
		
		newItems();
		
		int buttonHoehe = (int)(prefSize.getHeight()/10);
		int buttonBreite = (int)(prefSize.getWidth()/8);
		
		//rerollbutton
		String name = "1"; // muss noch geändert werden
		reRollButton = new Button(
				name, 
				(int) (itemButton4.getBounds().getCenterX()-buttonBreite/2),
				(int) (itemButton4.getBounds().getMinY()-prefSize.getHeight()*0.2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));
		
		//nextWaveButton
		name = "1"; //muss noch geändert werden
		nextWaveButton = new Button(						
				name, 
				(int) (prefSize.getWidth()/2-buttonBreite/2),
				(int) (prefSize.getHeight()-prefSize.getHeight()*0.2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));
	}
	
	public void openStore(int currentWave) {
		newItems();
	}

	public void paintMe(Graphics g) {
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//zeichnet durchsichtigen grauen hintergrunf
		g.setColor(new Color(0,0,0,150));
		g.fillRect(0,0,(int)prefSize.getWidth(),(int) prefSize.getHeight());
		g.fillRect((int)(prefSize.getWidth()*0.8),(int)(prefSize.getHeight()*0.1),(int)(prefSize.getWidth()*0.18),(int)(prefSize.getHeight()*0.8));
		
		
		g2d.setFont(new Font(Font.SERIF, Font.BOLD, 50));
		g2d.setColor(Color.RED);

		if (!bought1) {
			itemButton1.paintMe(g2d);
			
			g2d.drawString(itemButton1.getName() , 	(int) itemButton1.getBounds().getCenterX()-(g2d.getFontMetrics().stringWidth(""+itemButton1.getName())) , 
													(int) itemButton1.getBounds().getCenterY()-(g2d.getFontMetrics().getHeight()));
		}
		if (!bought2) {
			itemButton2.paintMe(g2d);
			
			g2d.drawString(itemButton2.getName() , 	(int) itemButton2.getBounds().getCenterX()-(g2d.getFontMetrics().stringWidth(""+itemButton2.getName())) , 
													(int) itemButton2.getBounds().getCenterY()-(g2d.getFontMetrics().getHeight()));
		}
		if (!bought3) {
			itemButton3.paintMe(g2d);
			
			g2d.drawString(itemButton3.getName() , 	(int) itemButton3.getBounds().getCenterX()-(g2d.getFontMetrics().stringWidth(""+itemButton3.getName())) , 
													(int) itemButton3.getBounds().getCenterY()-(g2d.getFontMetrics().getHeight()));
		}
		if (!bought4) {
			itemButton4.paintMe(g2d);
			
			g2d.drawString(itemButton4.getName() , 	(int) itemButton4.getBounds().getCenterX()-(g2d.getFontMetrics().stringWidth(""+itemButton4.getName())) , 
													(int) itemButton4.getBounds().getCenterY()-(g2d.getFontMetrics().getHeight()));
		}

		reRollButton.paintMe(g2d);
		nextWaveButton.paintMe(g2d);
	}

	public void newItems() {
		
		bought1	= false;
		bought2	= false;
		bought3	= false;
		bought4	= false;
		
		// button1
		String name = "" + randomItem();
		int buttonHoehe = (int)(prefSize.getHeight()*0.4);
		int buttonBreite = (int)(prefSize.getWidth()*0.15);
		itemButton1 = new Button(
				name, 
				(int) (prefSize.getWidth()*0.07), 
				(int) (prefSize.getHeight() / 2) - (buttonHoehe / 2), 
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));

		// button2
		name = "" + randomItem();
		itemButton2 = new Button(
				name, 
				(int) ((itemButton1.getBounds().getMinX())+(itemButton1.getBounds().getWidth())+(prefSize.getWidth()*0.03)),
				(int) (prefSize.getHeight() / 2) - (buttonHoehe / 2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));

		// button3
		name = "" + randomItem();
		itemButton3 = new Button(
				name,
				(int) ((itemButton2.getBounds().getMinX())+(itemButton2.getBounds().getWidth())+(prefSize.getWidth()*0.03)),
				(int) (prefSize.getHeight() / 2) - (buttonHoehe / 2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));

		// button4
		name = "" + randomItem();
		itemButton4 = new Button(		
				name, 
				(int) ((itemButton3.getBounds().getMinX())+(itemButton3.getBounds().getWidth())+(prefSize.getWidth()*0.03)),
				(int) (prefSize.getHeight() / 2) - (buttonHoehe / 2),
				buttonHoehe, buttonBreite,
				buttonImages.ButtonNormal("/Items/"+name), buttonImages.ButtonHover("/Items/"+name));
	}

	public void checkButtonsClick(MouseEvent e) {
		if (itemButton1.check(e)) {
			buyItem(itemButton1.getName());
			bought1=true;
		}
		if (itemButton2.check(e)) {
			buyItem(itemButton2.getName());
			bought2=true;
		}
		if (itemButton3.check(e)) {
			buyItem(itemButton3.getName());
			bought3=true;
		}
		if (itemButton4.check(e)) {
			buyItem(itemButton4.getName());
			bought4=true;
		}
		if (reRollButton.check(e)) {
			newItems();
		}
		if (nextWaveButton.check(e)) {
			master.getWaveHandler().nextWave();
		}
	}

	private void buyItem(String name) {
		try {
			item = Integer.parseInt(name);
		} catch (NumberFormatException ex) {
			
		}
		
		switch (item) {
		
		case 1: {
			master.getP().setAttackSpeed(master.getP().getAttackSpeed()-0.2);
			break;
		}
		case 5: {
			master.getP().setMaxLives(master.getP().getMaxLives()+10);
			break;
		}
		case 10: {
			master.getP().setSpeed(master.getP().getSpeed()+2);
			break;
		}
		default:
			master.getP().setDamage(master.getP().getDamage()+item);;
		}
		
		System.out.println(item);
	}
	
	private int randomItem() {
		int anzahltems = 9;
		return (int) Math.round(Math.random()*anzahltems+1);
	}

}
