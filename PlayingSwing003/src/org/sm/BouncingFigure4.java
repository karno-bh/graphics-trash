package org.sm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BouncingFigure4 implements GameObject{
	
	private int x;
	private int y;
	private int dx;
	private int dy;
	private int currentSprite = 0;
	private int counter = 0;
	
	private BufferedImage sprites[];
	
	public BouncingFigure4() {
		x = 30;
		y = 30;
		dx = 2;
		dy = 2;
	}
	
	public void gameTick() {
		x+=dx;
		y+=dy;
		if (x > 400) {
			dx = -((int)(Math.random() * 3) + 1);
		}
		if (y > 400) {
			dy = -((int)(Math.random() * 3) + 1);
		}
		if (x < 30) {
			dx = ((int)(Math.random() * 3) + 1);
		}
		if (y < 30) {
			dy = ((int)(Math.random() * 3) + 1);
		}
		if (counter < 10) {
			currentSprite = 0;
		} else if (counter < 20) {
			currentSprite = 1;
		} else if (counter < 30) {
			currentSprite = 2;
		} else {
			currentSprite = 0;
			counter = 0;
		}
		counter++;
	}
	
	public void render(Graphics g) {
		if (sprites == null) {
			BufferedImage spriteMap = null;
			try {
				spriteMap = ImageIO.read(new File("bomberman_bomberman_sheet.png"));
				sprites = new BufferedImage[3];
				int w = 14;
				for (int y = 0, i = 0; i < 3; y+=30, i++) {
					sprites[i] = spriteMap.getSubimage(0, y, w, 18);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		g.drawImage(sprites[currentSprite], x, y, null);
		
	}
}
