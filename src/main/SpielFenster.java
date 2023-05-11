package main;


import java.awt.event.WindowAdapter;	//Abschnitt 4
import java.awt.event.WindowEvent;		//Abschnitt 4

import javax.swing.JFrame;  //Abschnitt 1

import input.KeyInput;

public class SpielFenster extends JFrame{ //Durch Ableiten von JFrame werden z.B.Fenstereigenschaften geerbt
										  //Dar�ber hinaus ist JFrame ein Container f�r andere Objekte (JPanel, Menu,..)
	public GameMaster master;
	public KeyInput keyInput;
	
	
	public SpielFenster() {
		
		//GameMaster erzeugen
		System.out.println("erstelle GameMaster");
		master = new GameMaster();
		
		
		registerWindowListener();    // WindowListener registrieren (z.B. Schlie�en des Fensters)
		//createMenu();  // Erzeugen des Men�s
		
		add(master);  //Hinzuf�gen des Spielfeldes zum SpielFenster ; (add() erben alle von Container)
		
		this.setUndecorated(true);
		this.setResizable(false);// nicht verstellbar
		
		pack();  //Ideale Gr��e berechnen
		
		this.setTitle("Game");
		this.setLocationRelativeTo(null); //Linke obere Fensterecke festlegen
		this.setVisible(true); 
		
		
		repaint();
	}
	
	
	
	private void registerWindowListener() {        
	    addWindowListener(new WindowAdapter() {  
	    	//Hier wird von der Abstrakten Klasse WindowAdapter, einer abstrakten Klasse,
	    	//abgeleitet, die Fensterereignisse empf�ngt.
			//Es m�ssen nur ben�tigte Methoden der Klasse ausprogrammiert werden (sonst bleiben sie leer).
	        @Override
	        public void windowClosing(WindowEvent e) { 	        	
	        	System.exit(0); 
	        }
	        @Override
	        public void windowDeactivated(WindowEvent e) {
	            // hier werden wir sp�ter unser Spiel pausieren                
	        }
	        @Override
	        public void windowActivated(WindowEvent e) {
	            // hier werden wir sp�ter unser Spiel wieder fortsetzen
	        }            
	    });        
	}
	
	
}
