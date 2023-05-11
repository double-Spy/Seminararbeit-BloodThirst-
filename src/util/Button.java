package util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Button {
	
	private int x,y,width,height;
	private Rectangle2D bounds;
	private BufferedImage iamgeNormal,imageHover,image;
	private String name;
	private boolean press;
	
	public Button(String name,int x , int y , int height , int width, BufferedImage imageNormal,BufferedImage imageHover){
		this.name=name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.iamgeNormal=imageNormal;
		this.imageHover=imageHover;
		setHover(false);
		initBounds();
	}
	
	private void test() {
		int a = 0;
		int b = 7;
		
		int c = a-b;
	}
	
	private void initBounds() {
		bounds = new Rectangle2D.Float(x,y,width, height);
	}

	public boolean check(MouseEvent e) {
		if (bounds.contains(e.getX(),e.getY())) 
			return true;
		
		return false;
	}

	public void setHover(boolean hover) {
		if (hover) image = imageHover;
		
		else image = iamgeNormal;
	}
	
	public void paintMe(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image,x,y, width, height, null);
	}
	

	public String getName() {
		return name;
	}

	public Rectangle2D getBounds() {
		return bounds;
	}

	public boolean isPress() {
		return press;
	}

	public void setPress(boolean press) {
		this.press = press;
	}
	
	
}

