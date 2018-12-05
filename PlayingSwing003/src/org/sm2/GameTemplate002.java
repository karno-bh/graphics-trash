package org.sm2;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.SwingUtilities;

import javagames.util.FrameRate;

public class GameTemplate002 {

	private static final long TIME_BETWEEN_UPDATES = 1000L / 100L;
	private static final int MAX_ITER = 30;
	private static final int WIDTH = 1920 / 2;
	private static final int HEIGHT = (int)((double)WIDTH * 0.5625);
	
	private Mouse mouse = new Mouse();
	private Object synch = new Object();
	
	private BufferStrategy bs;
	private Frame f;
	private FrameRate frameRate;
	
	private double x = 10;
	private double y = 10;

	public static void main(String[] args) {
		GameTemplate002 gt = new GameTemplate002();
		gt.start();
	}

	public void start() {
		EventQueue.invokeLater(() -> {
			f = new Frame("This is a test frame");
			f.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					super.windowClosing(e);
					System.exit(0);
				}
			});
			Canvas c = new Canvas();
			c.setIgnoreRepaint(true);
			// f.setIgnoreRepaint(true);
			c.setSize(WIDTH, HEIGHT);
			f.add(c);
			f.setResizable(false);
			f.pack();
			//f.setUndecorated(true);
			f.setLocationRelativeTo(null);
			f.setVisible(true);
			int attempt = 10;
			while(attempt != 0) {
				try {
					c.createBufferStrategy(2);
					break;
				} catch (Throwable t) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException ignore) {}
					attempt--;
				}
			}
			if (attempt == 0) {
				throw new IllegalStateException("Cannot launch application");
			}
			bs = c.getBufferStrategy();
			frameRate = new FrameRate();
			frameRate.initialize();
			c.addMouseListener(mouse);
			synchronized (synch) {
				synch.notifyAll();
			}
		});
		synchronized (synch) {
			try {
				synch.wait();
			} catch (InterruptedException e) {}
			gameLoop();
		}
		
	}

	public void gameLoop() {
		long lastUpdateTime = System.currentTimeMillis();
		while (true) {
			long now = System.currentTimeMillis();
			int iter = 0;
			while (now - lastUpdateTime >= TIME_BETWEEN_UPDATES && iter < MAX_ITER) {
				//System.out.println(lastUpdateTime / 1000);
				/*if (iter > 0) {
					System.out.println("Iter: " + iter);
				}*/
				tick();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				iter++;
			}
			draw();
			// Thread.yield();
			try {
				Thread.sleep(5);
			} catch (InterruptedException ignore) {}
		}
	}
	
	public void tick() {
		x+=1D/5;
		y+=1D/5;
		if (x > 400) {
			x = 10;
			y = 10;
		}
	}
	
	public void draw() {
		do {
			do {
				Graphics g = null;
				try {
					g = bs.getDrawGraphics();
					//System.out.println(g);
					render(g);
				} finally {
					if (g != null) {
						g.dispose();
					}
				}
			} while (bs.contentsRestored());
			bs.show();
		} while (bs.contentsLost());
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		frameRate.calculate();
		g.setColor(Color.GREEN);
		g.drawString("frame:"  + frameRate.getFrameRate(), 100, 100);
		g.drawString("Mouse: " + mouse.getPressed(), 50, 50);
		g.setColor(Color.BLUE);
		g.fillRect((int)x, (int)y, 50, 50);
	}
}
