package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;

/**
 * Legacy update component abstraction.
 *
 * <p>Prefer implementing {@link UpdateComponent} for new logic components or
 * {@link DrawComponent} for rendering components. This compatibility class
 * adapts the older {@link #tick(DrawingContext, Entity, double)} method to the
 * update path and supplies {@code null} for the drawing context.</p>
 */
@Deprecated
public abstract class Component implements UpdateComponent {
    /**
     * Performs this component's update behavior using the legacy method shape.
     *
     * <p>The time value is forwarded unchanged by {@link EntitySystem}. The
     * drawing context is {@code null} when invoked through the update path.</p>
     *
     * @param dc drawing context supplied by the caller; may be {@code null} if
     *           invoked through the update path
     * @param e  entity that owns or invokes this component
     * @param t  caller-supplied time or delta value
     */
    public abstract void tick(DrawingContext dc, Entity e, double t);

    @Override
    public void update(Entity entity, double delta) {
        tick(null, entity, delta);
    }
}
