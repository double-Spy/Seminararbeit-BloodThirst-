package util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ButtonImages {
	

	public BufferedImage ButtonNormal(String name) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new FileInputStream("res/Buttons/"+name+"Normal.png"));
        } catch (IOException e) {
            // TODO: handle exception
        }
		return image;
	}
	
	public BufferedImage ButtonHover(String name) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new FileInputStream("res/Buttons/"+name+"Hover.png"));
        } catch (IOException e) {
            // TODO: handle exception
        }
		return image;
	}
}
