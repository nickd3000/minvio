package com.physmo.minvioexamples.ecs;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

public class FlashyDrawComponent extends Component {

    int i;

    public FlashyDrawComponent() {
        i = (int) (Math.random() * 100.0);
    }

    @Override
    public void tick(BasicDisplay bd, Entity e, double d) {
        bd.setDrawColor(bd.getDistinctColor(i++, 0.6));

        //bd.drawFilledCircle(e.position.x, e.position.y, 20);
        bd.drawFilledRect((int) e.position.x - 10, (int) e.position.y - 10, 20, 20);
    }
}
