package com.physmo.minvioexamples.ecs;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

public class GravityComponent extends Component {
    @Override
    public void tick(BasicDisplay bd, Entity e, double d) {

        e.velocity.y += 1000.0 * d;

    }
}
