package org.sm2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

import javagames.util.FrameRate;
import org.sm.simple_engine.Mouse;

public class GameTemplate001 {

	private static final long TIME_BETWEEN_UPDATES = 1000L / 100L;
	private static final int MAX_ITER = 30;
	private static final int WIDTH = 1920 / 2;
	private static final int HEIGHT = (int)((double)WIDTH * 0.5625);
	
	private Mouse mouse = new Mouse();
	
	private BufferStrategy bs;
	private Frame f;
	private FrameRate frameRate;
	private double thetaDeg = 0;
	
	private double x = 10;
	private double y = 10;

	public static void main(String[] args) {
		GameTemplate001 gt = new GameTemplate001();
		gt.start();
	}

	public void start() {
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
		f.setIgnoreRepaint(true);
		c.setSize(WIDTH, HEIGHT);
		f.add(c);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.pack();
		f.setVisible(true);
		
		c.createBufferStrategy(2);
		bs = c.getBufferStrategy();
		frameRate = new FrameRate();
		frameRate.initialize();
		c.addMouseListener(mouse);

		gameLoop();
	}

	public void gameLoop() {
		long lastUpdateTime = System.currentTimeMillis();
		while (true) {
			long now = System.currentTimeMillis();
			int iter = 0;
			while (now - lastUpdateTime >= TIME_BETWEEN_UPDATES && iter < MAX_ITER) {
				//System.out.println(lastUpdateTime / 1000);
				if (iter > 0) {
					System.out.println("Iter: " + iter);
				}
				tick();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				iter++;
			}
			draw();
			try {
				Thread.sleep(10);
			} catch (InterruptedException ignore) {}
		}
	}
	
	public void tick() {
		x+=1D/3;
		y+=1D/3;
		if (x > 400) {
			x = 10;
			y = 10;
		}
		
		thetaDeg++;
		thetaDeg %= 360;
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
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.black);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		frameRate.calculate();
		g2.setColor(Color.GREEN);
		g2.drawString(frameRate.getFrameRate(), 30, 30);
		g2.drawString("Mouse: " + mouse.getPressed(), 50, 50);
		g2.setColor(Color.BLUE);
		AffineTransform at = g2.getTransform();
		AffineTransform atNow = new AffineTransform();
		atNow.translate(WIDTH / 2 , HEIGHT / 2);
		atNow.rotate(Math.PI / 180 * thetaDeg);
		g2.setTransform(atNow);
//		g2.drawLine(0, 0, 30, 0);
//		g2.drawLine(0, 0, 0, 30);
		g2.fillRect(-50, -50, 100, 100);
	}
}
