package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

public class BounceComponent extends Component {
    @Override
    public void tick(DrawingContext dc, Entity e, double d) {
        double pad = 20;
        if (e.position.x < pad) {
            e.position.x = pad;
            e.velocity.x *= -1;
        }
        if (e.position.x > dc.getWidth() - pad) {
            e.position.x = dc.getWidth() - pad;
            e.velocity.x *= -1;
        }
        if (e.position.y < pad) {
            e.position.y = pad;
            e.velocity.y *= -1;
        }
        if (e.position.y > dc.getHeight() - pad) {
            e.position.y = dc.getHeight() - pad;
            e.velocity.y *= -1;
        }
    }
}
