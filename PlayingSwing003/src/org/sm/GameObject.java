package org.sm;

import java.awt.Graphics;

public interface GameObject {
	
	void gameTick();
	void render(Graphics g);
}
