package org.sm.simple_engine;

import java.awt.*;

public interface Game {

    void tick(Engine e);
    void render(Engine e, Graphics g);

}
