package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;

/**
 * Unit of rendering behavior attached to an {@link Entity}.
 */
public interface DrawComponent {
    /**
     * Performs this component's draw behavior.
     *
     * @param dc     drawing context supplied by the caller
     * @param entity entity that owns or invokes this component
     * @param delta  caller-supplied time or delta value
     */
    void draw(DrawingContext dc, Entity entity, double delta);
}
