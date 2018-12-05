package org.sm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class FloatCircleExample extends JPanel {
	
	public static final int FRAME_HEIGHT = 640;
	public static final int FRAME_WIDTH = 480;
	public static final int FRAME_RATE = 30;
	public static final int MAX_DELAY_MS = 1000 / FRAME_RATE;
	
	private Timer updateTimer;
	private BufferedImage buffer;
	private long lastUpdateTime = System.currentTimeMillis();
	
	private int x = 30;
	private int y = 30;
	private int dx = 3;
	private int dy = 3;
	private int dt = 0;
	
	private boolean mousePressed = false;
	private boolean mouseEntered = false;
	
	public static void startOnEDT() {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Simple Graphics");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());
			FloatCircleExample floatCircleExample = new FloatCircleExample();
			frame.add(floatCircleExample);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			floatCircleExample.start();
		});
	}
	
	public void start() {
		updateTimer = new Timer(30, new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mousePressed = false;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				mousePressed = true;
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				mouseEntered = false;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseEntered = true;
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		updateTimer.start();
	}
	
	public void tick() {
		updateTimer.stop();
		processInput();
		cycle();
		render();
		long now = System.currentTimeMillis();
		long timeDiff = now - lastUpdateTime;
		lastUpdateTime = now;
		//System.out.println("TimeDiff: " + timeDiff);
		long sleepTime = MAX_DELAY_MS - timeDiff;
		if (sleepTime < 1 ) {
			sleepTime = 1;
		}
		//System.out.println("SleepTime: " + sleepTime);
		updateTimer.setDelay((int) sleepTime);
		updateTimer.setInitialDelay((int) sleepTime);
		updateTimer.start();
		repaint();
	}
	
	public void processInput() {
		
	}
	
	public void cycle() {
		x+=dx;
		y+=dy;
		if (x > 400) {
			dx = -dx;
			dy = -dy;
		}
		if (x < 30) {
			dx = -dx;
			dy = -dy;
			dx += dt;
			dy += dt;
		}
		/*try {
			Thread.sleep((long) (Math.random() * 15));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void render() {
		BufferedImage buffer = getBuffer();
    	Graphics g = null;
    	try {
    		g = buffer.getGraphics();
    		
    		
    		g.setColor(Color.WHITE);
            g.fillRect(0, 0, FRAME_HEIGHT, FRAME_WIDTH);
            
            g.setColor(Color.RED);
            
            String text = "Mouse Pressed: " + mousePressed + " Mouse In: " + mouseEntered; 
            g.drawString(text, 20, 20);
            
            g.setColor(Color.RED);
            g.fillRect(x, y, 30, 30);
    	} finally {
    		if (g != null) {
    			g.dispose();
    		}
    	}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(FRAME_HEIGHT, FRAME_WIDTH);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(getBuffer(), 0, 0, null);
	}
	
	public BufferedImage getBuffer() {
		if (buffer == null) {
			buffer = (BufferedImage)createImage(FRAME_HEIGHT, FRAME_WIDTH);
		}
		return buffer;
	}
	
	public static void main(String[] args) {
		FloatCircleExample.startOnEDT();
	}
}
