package util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
	
	public BufferedImage[] loadImages(String path, int numberOfImages) {
		BufferedImage[] images = new BufferedImage[numberOfImages];
		for (int i = 0; i < numberOfImages; i++) {
			try {
				images[i] = ImageIO.read(new FileInputStream(path + (i + 1) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images;
	}
}