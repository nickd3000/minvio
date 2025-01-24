package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

public class MoveComponent extends Component {

    @Override
    public void tick(DrawingContext dc, Entity e, double t) {
        e.position.addi(e.velocity.scale(t));
    }
}
