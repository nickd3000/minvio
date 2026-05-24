package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.Palette;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

// This component draws a square that constantly changes color.
public class FlashyDrawComponent extends Component {

    int i; // Used as an index for the color palette.

    public FlashyDrawComponent() {
        i = (int) (Math.random() * 100.0);
    }

    @Override
    public void tick(DrawingContext dc, Entity e, double d) {
        // Get a color from the palette that changes based on 'i'.
        dc.setDrawColor(Palette.getDistinctColor(i++, 0.6));

        // Draw a rectangle centered at the entity's position.
        dc.drawFilledRect((int) e.position.x - 10, (int) e.position.y - 10, 20, 20);
    }
}
