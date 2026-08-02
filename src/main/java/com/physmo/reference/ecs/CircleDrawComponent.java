package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.ecs.DrawComponent;
import com.physmo.minvio.utils.ecs.Entity;

import java.awt.Color;

// This component draws a simple colored circle for the entity.
public class CircleDrawComponent implements DrawComponent {
    Color color;

    public CircleDrawComponent(Color c) {
        color = c;
    }

    @Override
    public void draw(DrawingContext dc, Entity e, double d) {
        // Set the color and draw a circle at the entity's position.
        dc.setDrawColor(color);
        dc.drawFilledCircle(e.position.x, e.position.y, 20);

        // Check if the entity has a "special" property. If it does, draw an extra inner circle.
        e.getProperty("special").ifPresent(o -> {
            if ((boolean) o) {
                dc.setDrawColor(Color.white);
                dc.drawFilledCircle(e.position.x, e.position.y, 10);
            }
        });

    }
}
