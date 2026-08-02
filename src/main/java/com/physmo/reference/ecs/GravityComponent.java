package com.physmo.reference.ecs;

import com.physmo.minvio.utils.ecs.Entity;
import com.physmo.minvio.utils.ecs.UpdateComponent;

// This component applies a constant downward acceleration (gravity) to an entity.
public class GravityComponent implements UpdateComponent {
    @Override
    public void update(Entity e, double d) {

        // Increase vertical velocity over time.
        e.velocity.y += 1000.0 * d;

    }
}
