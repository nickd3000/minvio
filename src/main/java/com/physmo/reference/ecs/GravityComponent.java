package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

public class GravityComponent extends Component {
    @Override
    public void tick(DrawingContext dc, Entity e, double d) {

        e.velocity.y += 1000.0 * d;

    }
}
