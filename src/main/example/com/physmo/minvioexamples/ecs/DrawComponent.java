package com.physmo.minvioexamples.ecs;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

import java.awt.Color;

public class DrawComponent extends Component {
    Color color;

    public DrawComponent(Color c) {
        color = c;
    }

    @Override
    public void tick(BasicDisplay bd, Entity e, double d) {
        bd.setDrawColor(color);
        bd.drawFilledCircle(e.position.x, e.position.y, 20);

        e.getProperty("special").ifPresent(o -> {
            if ((boolean) o == true) {
                bd.setDrawColor(Color.white);
                bd.drawFilledCircle(e.position.x, e.position.y, 10);
            }
        });

    }
}
