package com.physmo.minvio.utils.ecs;

/**
 * Unit of logic behavior attached to an {@link Entity}.
 */
public interface UpdateComponent {
    /**
     * Performs this component's update behavior.
     *
     * @param entity entity that owns or invokes this component
     * @param delta  caller-supplied time or delta value
     */
    void update(Entity entity, double delta);
}
