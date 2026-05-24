package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;
import com.physmo.reference.Reference;

// This component checks if an entity has hit the edge of the screen and reverses
// its velocity to make it bounce back.
@Reference
public class BounceComponent extends Component {
    @Override
    public void tick(DrawingContext dc, Entity e, double d) {
        double pad = 20; // Padding from the edge of the screen.

        // Check left edge.
        if (e.position.x < pad) {
            e.position.x = pad;
            e.velocity.x *= -1; // Reverse horizontal velocity.
        }
        // Check right edge.
        if (e.position.x > dc.getWidth() - pad) {
            e.position.x = dc.getWidth() - pad;
            e.velocity.x *= -1; // Reverse horizontal velocity.
        }
        // Check top edge.
        if (e.position.y < pad) {
            e.position.y = pad;
            e.velocity.y *= -1; // Reverse vertical velocity.
        }
        // Check bottom edge.
        if (e.position.y > dc.getHeight() - pad) {
            e.position.y = dc.getHeight() - pad;
            e.velocity.y *= -1; // Reverse vertical velocity.
        }
    }
}
