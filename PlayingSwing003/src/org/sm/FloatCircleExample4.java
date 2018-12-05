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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class FloatCircleExample4 extends JPanel {
	
	public static final int FRAME_HEIGHT = 640;
	public static final int FRAME_WIDTH = 480;
	public static final int TIME_BETWEEN_UPDATES = 1000 / 30;
	public static final int MAX_DELAY_MS = 1000 / TIME_BETWEEN_UPDATES;
	public static final int MAX_ITER = 100;
	
	private Timer updateTimer;
	private BufferedImage buffer;
	private long lastUpdateTime = System.currentTimeMillis();
	
	private boolean mousePressed = false;
	private boolean mouseEntered = false;
	
	public static void startOnEDT() {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Simple Graphics");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());
			FloatCircleExample4 floatCircleExample = new FloatCircleExample4();
			frame.add(floatCircleExample);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			floatCircleExample.start();
		});
	}
	
	private List<GameObject> gameObjects = new ArrayList<>();
	
	public void start() {
		updateTimer = new Timer(10, new ActionListener() {	
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
		
		for (int  i = 0; i < 10; i++) {
			gameObjects.add(new BouncingFigure4());
		}
		//gameObjects.add(new BouncingFigure4());
//		gameObjects.add(new BouncingFigure2());
//		gameObjects.add(new BouncingFigure());
		
		updateTimer.start();
	}
	
	
	public void tick() {
		//System.out.println("Called");
		long now = System.currentTimeMillis();
		int iter =  0;
		while (now - lastUpdateTime >= TIME_BETWEEN_UPDATES && iter < MAX_ITER) {
			processInput();
			//long cycleWas = System.currentTimeMillis();
			cycle();
			//long cycleNow = System.currentTimeMillis();
			//System.out.println("Cycle take: " + (cycleNow - cycleWas) + " ; Iter=" + iter);
			lastUpdateTime += TIME_BETWEEN_UPDATES;
			iter++;
		}
		long renderWas = System.currentTimeMillis();
		render();
		long renderNow = System.currentTimeMillis();
		System.out.println("Render take: " + (renderNow - renderWas));
	}
	
	public void processInput() {
		
	}
	
	public void cycle() {
		for (GameObject gameObject : gameObjects) {
			gameObject.gameTick();
		}
	}
	
	public void render() {
		BufferedImage buffer = getBuffer();
    	Graphics g = null;
    	try {
    		g = buffer.getGraphics();
    		
    		
    		g.setColor(Color.BLACK);
            g.fillRect(0, 0, FRAME_HEIGHT, FRAME_WIDTH);
            
            g.setColor(Color.RED);
            
            String text = "Mouse Pressed: " + mousePressed + " Mouse In: " + mouseEntered; 
            g.drawString(text, 20, 20);
            
            for (GameObject gameObject : gameObjects) {
    			gameObject.render(g);
    		}
            
            
    	} finally {
    		if (g != null) {
    			g.dispose();
    		}
    	}
    	repaint();
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
			//buffer = (BufferedImage)createImage(FRAME_HEIGHT, FRAME_WIDTH);
			buffer = new BufferedImage(FRAME_HEIGHT, FRAME_WIDTH, BufferedImage.TYPE_INT_ARGB);
		}
		return buffer;
	}
	
	public static void main(String[] args) {
		FloatCircleExample4.startOnEDT();
	}
}
