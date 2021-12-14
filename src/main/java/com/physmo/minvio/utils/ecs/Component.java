package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.BasicDisplay;

public abstract class Component {
    public abstract void tick(BasicDisplay bd, Entity e, double t);
}
