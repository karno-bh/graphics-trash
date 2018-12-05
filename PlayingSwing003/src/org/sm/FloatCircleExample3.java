package org.sm;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class FloatCircleExample3 extends JFrame{
	
	public static final int FRAME_RATE = 30;
	public static final int MAX_DELAY_MS = 1000 / FRAME_RATE;
	public static final int MAX_ITER = 15;
	
	private BufferStrategy bufferStrategy;
	private Timer updateTimer;
	private Canvas canvas;
//	private BufferedImage buffer;
	private long lastUpdateTime = System.currentTimeMillis();
	
	
	private int x = 30;
	private int y = 30;
	private int dx = 3;
	private int dy = 3;
	private int dt = 0;
	
	private boolean mousePressed = false;
	private boolean mouseEntered = false;
	
	public FloatCircleExample3(String name) {
		super(name);
		
		canvas = createCanvas();
		getContentPane().add(canvas);
		setIgnoreRepaint(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
	}
	
	public void start() {
		updateTimer = new Timer(5, new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		canvas.addMouseListener(new MouseListener() {
			
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
	
	private Canvas createCanvas() {
		Canvas canvas = new Canvas();
		canvas.setSize(640, 480);
		canvas.setBackground(Color.BLACK);
		canvas.setIgnoreRepaint(true);
		return canvas;
	}
	
//	public void tick() {
//		updateTimer.stop();
//		processInput();
//		cycle();
//		render();
//		long now = System.currentTimeMillis();
//		long timeDiff = now - lastUpdateTime;
//		lastUpdateTime = now;
//		System.out.println("TimeDiff: " + timeDiff);
//		long sleepTime = MAX_DELAY_MS - timeDiff;
//		if (sleepTime < 1 ) {
//			sleepTime = 1;
//		}
//		System.out.println("SleepTime: " + sleepTime);
//		updateTimer.setDelay((int) sleepTime);
//		updateTimer.setInitialDelay((int) sleepTime);
//		updateTimer.start();
//	}
	
	public void tick() {
//		updateTimer.stop();
		
		long now = System.currentTimeMillis();
		//long timeDiff = now - lastUpdateTime;
		//lastUpdateTime = now;
		//System.out.println("TimeDiff: " + timeDiff);
		int iter =  0;
		while (now - lastUpdateTime >= FRAME_RATE && iter < MAX_ITER) {
			processInput();
			cycle();
			lastUpdateTime += FRAME_RATE;
			iter++;
		}
		
		render();
		
//		long sleepTime = MAX_DELAY_MS - timeDiff;
//		if (sleepTime < 1 ) {
//			sleepTime = 1;
//		}
//		System.out.println("SleepTime: " + sleepTime);
//		updateTimer.setDelay((int) sleepTime);
//		updateTimer.setInitialDelay((int) sleepTime);
//		updateTimer.start();
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
//		try {
//			Thread.sleep((long) (Math.random() * 15));
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void render() {
		 while(true) {
	         while(true) {
	            Graphics g = null;
	            try {
	               g = bufferStrategy.getDrawGraphics();
	               //g.clearRect( 0, 0, getWidth(), getHeight() );
	               
	               g.setColor(Color.LIGHT_GRAY);
	               g.fillRect(0, 0, getWidth(), getHeight() );
	               
	               g.setColor(Color.RED);
	               
	               String text = "Mouse Pressed: " + mousePressed + " Mouse In: " + mouseEntered; 
	               g.drawString(text, 20, 20);
	               
	               g.setColor(Color.RED);
	               g.fillRect(x, y, 30, 30);
	               
	               //render( g );
	            } finally {
	               if( g != null ) {
	                  g.dispose();
	               }
	            }
	            if (bufferStrategy.contentsRestored()) {
	            	System.out.println("Contents Restored");
	            	continue;
	            }
	            break;
	         }
	         bufferStrategy.show();
	         if (bufferStrategy.contentsLost()) {
	        	 System.out.println("Contents Lost");
	        	 continue;
	         }
	         break;
	      }
	}
	
	public static void startOnEDT() {
		SwingUtilities.invokeLater(() -> {
			FloatCircleExample3 frame = new FloatCircleExample3("Simple Graphics");
			
			frame.start();
		});
	}
	
	public static void main(String[] args) {
		startOnEDT();
	}
}
