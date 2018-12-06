package org.sm.simple_engine_examples;

import org.sm.simple_engine.Engine;
import org.sm.simple_engine.Game;

import java.awt.*;

public class BoxExample01 implements Game {

    private double x = 10;
    private double y = 10;

    @Override
    public void tick(Engine e) {
        x+=1D/5;
        y+=1D/5;
        if (x > 400) {
            x = 10;
            y = 10;
        }
    }

    @Override
    public void render(Engine e, Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, e.getWidth(), e.getHeight());
        g.setColor(Color.GREEN);
        // g.drawString("frame:"  + frameRate.getFrameRate(), 100, 100);
        g.drawString("Mouse: " + e.getMouse().getPressed(), 50, 50);
        g.setColor(Color.BLUE);
        g.fillRect((int)x, (int)y, 50, 50);
    }

    public static void main(String[] args) {
        Game g = new BoxExample01();
        Engine e = new Engine(g);
        e.start();
    }
}
