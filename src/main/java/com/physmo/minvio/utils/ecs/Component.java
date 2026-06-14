package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;

/**
 * Unit of behavior attached to an {@link Entity}.
 *
 * <p>This optional ECS helper does not manage component lifecycles or enforce
 * unique component types.</p>
 */
public abstract class Component {
    /**
     * Performs this component's update or draw behavior.
     *
     * <p>The time value is forwarded unchanged by {@link EntitySystem}. Minvio
     * examples conventionally supply elapsed seconds, but callers may define a
     * different meaning.</p>
     *
     * @param dc drawing context supplied by the caller; may be {@code null} if
     *           the component does not draw
     * @param e  entity that owns or invokes this component
     * @param t  caller-supplied time or delta value
     */
    public abstract void tick(DrawingContext dc, Entity e, double t);
}
