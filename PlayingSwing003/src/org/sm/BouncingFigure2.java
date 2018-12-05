package org.sm;

import java.awt.Color;
import java.awt.Graphics;

public class BouncingFigure2 implements GameObject{
	
	private int x;
	private int y;
	private int dx;
	private int dy;
	
	public BouncingFigure2() {
		x = 30;
		y = 30;
		dx = 2;
		dy = 2;
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
		g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
	}
}
