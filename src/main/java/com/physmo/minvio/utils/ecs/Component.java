package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;

public abstract class Component {
    public abstract void tick(DrawingContext dc, Entity e, double t);
}
