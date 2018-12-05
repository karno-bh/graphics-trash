package org.sm;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BouncingFigure implements GameObject{
	
	private int x;
	private int y;
	private int dx;
	private int dy;
	
	private BufferedImage image;
	
	public BouncingFigure() {
		x = 50;
		y = 50;
		dx = 3;
		dy = 3;
	}
	
	public void gameTick() {
		x+=dx;
		y+=dy;
		if (x > 400) {
			dx = -dx;
			dy = -dy;
		}
		if (x < 30) {
			dx = -dx;
			dy = -dy;
		}
	}
	
	public void render(Graphics g) {
		if (image == null) {
			BufferedImage tempImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gBuff = null;
			
			try {
				gBuff = tempImage.createGraphics();
				gBuff.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
				
				gBuff.fillRect(0, 0, 50, 50);
				
				gBuff.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
				gBuff.setPaint(Color.GREEN);
				gBuff.fillOval(0, 0, 50, 50);
				image = tempImage;
			} finally {
				if (gBuff != null) {
					gBuff.dispose();
				}
			}
		}
		g.drawImage(image, x, y, null);
	}
}
