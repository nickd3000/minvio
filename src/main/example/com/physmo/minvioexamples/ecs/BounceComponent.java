package com.physmo.minvioexamples.ecs;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

public class BounceComponent extends Component {
    @Override
    public void tick(BasicDisplay bd, Entity e, double d) {
        double pad = 20;
        if (e.position.x < pad) {
            e.position.x = pad;
            e.velocity.x *= -1;
        }
        if (e.position.x > bd.getWidth() - pad) {
            e.position.x = bd.getWidth() - pad;
            e.velocity.x *= -1;
        }
        if (e.position.y < pad) {
            e.position.y = pad;
            e.velocity.y *= -1;
        }
        if (e.position.y > bd.getHeight() - pad) {
            e.position.y = bd.getHeight() - pad;
            e.velocity.y *= -1;
        }
    }
}
