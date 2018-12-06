package org.sm.simple_engine;

import javagames.util.FrameRate;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Objects;

public class Engine {

	private static final long TICK_HZ = 100;
	private static final int MAX_ITER = 30;
	private static final int WIDTH = 1920 / 2;
	private static final int HEIGHT = (int)((double)WIDTH * 0.5625);
	
	private final Game game;
	private final int width;
	private final int height;
	private final int maxIter;
	private final long timeBetweenUpdates;
	private final long sleepOnRenderMs;



	private Mouse mouse = new Mouse();

	// use the same thread from where "public static void main" started
	private final Object onStartSync = new Object();
	
	private BufferStrategy bs;
	private Frame f;
	private Canvas c;
	private FrameRate frameRate;

	public Engine(Game game) {
		Objects.requireNonNull(game, "Game cannot be null");
		this.game = game;

		this.width = WIDTH;
		this.height = HEIGHT;
		this.maxIter = MAX_ITER;
		this.timeBetweenUpdates = 1000 / TICK_HZ;
		this.sleepOnRenderMs = 10;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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
			c = new Canvas();
			f.setIgnoreRepaint(true);
			c.setIgnoreRepaint(true);
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
			c.addMouseListener(getMouse());
			synchronized (onStartSync) {
				onStartSync.notifyAll();
			}
		});
		synchronized (onStartSync) {
			try {
				onStartSync.wait();
			} catch (InterruptedException ignored) {}
			gameLoop();
		}
	}

	public void gameLoop() {
		long lastUpdateTime = System.currentTimeMillis();
		while (true) {
			long now = System.currentTimeMillis();
			int iter = 0;
			while (now - lastUpdateTime >= timeBetweenUpdates && iter < maxIter) {
				//System.out.println(lastUpdateTime / 1000);
				/*if (iter > 0) {
					System.out.println("Iter: " + iter);
				}*/
				game.tick(this);
				lastUpdateTime += timeBetweenUpdates;
				iter++;
			}
			draw();
			// Thread.yield();
			try {
				Thread.sleep(sleepOnRenderMs);
			} catch (InterruptedException ignore) {}
		}
	}
	
	public void draw() {
		do {
			do {
				Graphics g = null;
				try {
					g = bs.getDrawGraphics();
					frameRate.calculate();
					//System.out.println(g);
					game.render(this, g);
					postRender(g);
				} finally {
					if (g != null) {
						g.dispose();
					}
				}
			} while (bs.contentsRestored());
			bs.show();
		} while (bs.contentsLost());
	}


	protected void postRender(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawString(frameRate.getFrameRate(), 0, 10);
	}
}
