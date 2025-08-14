package com.physmo.reference.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.Utils;
import com.physmo.minvio.utils.ecs.Component;
import com.physmo.minvio.utils.ecs.Entity;

public class FlashyDrawComponent extends Component {

    int i;

    public FlashyDrawComponent() {
        i = (int) (Math.random() * 100.0);
    }

    @Override
    public void tick(DrawingContext dc, Entity e, double d) {
        dc.setDrawColor(Utils.getDistinctColor(i++, 0.6));

        //bd.drawFilledCircle(e.position.x, e.position.y, 20);
        dc.drawFilledRect((int) e.position.x - 10, (int) e.position.y - 10, 20, 20);
    }
}
