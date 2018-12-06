package org.sm.simple_engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
	
	private volatile boolean pressed = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}
	
	public boolean getPressed() {
		return pressed;
	}
	
}
