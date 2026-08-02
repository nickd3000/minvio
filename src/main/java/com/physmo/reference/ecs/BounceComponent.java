package com.physmo.reference.ecs;

import com.physmo.minvio.utils.ecs.Entity;
import com.physmo.minvio.utils.ecs.UpdateComponent;
import com.physmo.reference.Reference;

// This component checks if an entity has hit the edge of the screen and reverses
// its velocity to make it bounce back.
@Reference
public class BounceComponent implements UpdateComponent {

    private final double width;
    private final double height;
    private final double padding;

    public BounceComponent() {
        this(400, 400, 20);
    }

    public BounceComponent(double padding) {
        this(400, 400, padding);
    }

    public BounceComponent(double width, double height, double padding) {
        this.width = width;
        this.height = height;
        this.padding = padding;
    }

    @Override
    public void update(Entity e, double d) {
        // Check left edge.
        if (e.position.x < padding) {
            e.position.x = padding;
            e.velocity.x *= -1; // Reverse horizontal velocity.
        }
        // Check right edge.
        if (e.position.x > width - padding) {
            e.position.x = width - padding;
            e.velocity.x *= -1; // Reverse horizontal velocity.
        }
        // Check top edge.
        if (e.position.y < padding) {
            e.position.y = padding;
            e.velocity.y *= -1; // Reverse vertical velocity.
        }
        // Check bottom edge.
        if (e.position.y > height - padding) {
            e.position.y = height - padding;
            e.velocity.y *= -1; // Reverse vertical velocity.
        }
    }
}
