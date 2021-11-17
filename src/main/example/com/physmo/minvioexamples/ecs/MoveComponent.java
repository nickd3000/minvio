package com.physmo.minvioexamples.ecs;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

public class MoveComponent extends Component {

    @Override
    public void tick(BasicDisplay bd, Entity e, double t) {
        e.position.addi(e.velocity.scale(t));
    }
}
