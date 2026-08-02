package com.physmo.reference.ecs;

import com.physmo.minvio.utils.ecs.Entity;
import com.physmo.minvio.utils.ecs.UpdateComponent;

// This component makes an entity move by adding its velocity to its position each frame.
public class MoveComponent implements UpdateComponent {

    @Override
    public void update(Entity e, double t) {
        // Position = Position + (Velocity * Time)
        e.position.addi(e.velocity.scale(t));
    }
}
