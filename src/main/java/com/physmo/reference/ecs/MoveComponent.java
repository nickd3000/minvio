package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

// This component makes an entity move by adding its velocity to its position each frame.
public class MoveComponent extends Component {

    @Override
    public void tick(DrawingContext dc, Entity e, double t) {
        // Position = Position + (Velocity * Time)
        e.position.addi(e.velocity.scale(t));
    }
}
