package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

// This component applies a constant downward acceleration (gravity) to an entity.
public class GravityComponent extends Component {
    @Override
    public void tick(DrawingContext dc, Entity e, double d) {

        // Increase vertical velocity over time.
        e.velocity.y += 1000.0 * d;

    }
}
