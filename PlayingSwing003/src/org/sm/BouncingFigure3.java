package org.sm;

import java.awt.Color;
import java.awt.Graphics;

public class BouncingFigure3 implements GameObject{
	
	private int x;
	private int y;
	private int dx;
	private int dy;
	
	public BouncingFigure3() {
		x = 30;
		y = 30;
		dx = 2;
		dy = 2;
	}
	
	public void gameTick() {
		x+=dx;
		y+=dy;
		if (x > 400) {
			dx = -((int)(Math.random() * 10) + 1);
		}
		if (y > 400) {
			dy = -((int)(Math.random() * 10) + 1);
		}
		if (x < 30) {
			dx = ((int)(Math.random() * 10) + 1);
		}
		if (y < 30) {
			dy = ((int)(Math.random() * 10) + 1);
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
        g.drawLine(x, y, x, y);
	}
}
