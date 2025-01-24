package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

import java.awt.Color;

public class DrawComponent extends Component {
    Color color;

    public DrawComponent(Color c) {
        color = c;
    }

    @Override
    public void tick(DrawingContext dc, Entity e, double d) {
        dc.setDrawColor(color);
        dc.drawFilledCircle(e.position.x, e.position.y, 20);

        e.getProperty("special").ifPresent(o -> {
            if ((boolean) o == true) {
                dc.setDrawColor(Color.white);
                dc.drawFilledCircle(e.position.x, e.position.y, 10);
            }
        });

    }
}
