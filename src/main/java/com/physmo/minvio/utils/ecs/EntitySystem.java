package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Ordered collection of {@link Entity} instances with bulk update and draw
 * operations.
 *
 * <p>This is a deliberately small optional helper. It is not thread-safe and
 * exposes its mutable entity list directly.</p>
 */
public class EntitySystem {

    private final List<Entity> entities = new ArrayList<>();

    /**
     * Updates all entities in insertion order.
     *
     * <p>The arguments are forwarded unchanged to each entity. Structural
     * modification of the entity list during iteration is unsupported.</p>
     *
     * @param dc    drawing context to forward; may be {@code null}
     * @param delta caller-defined time or delta value
     */
    public void tickAll(DrawingContext dc, double delta) {
        entities.forEach(entity -> entity.tick(dc, delta));
    }

    /**
     * Draws all entities in insertion order.
     *
     * @param dc drawing context to forward; may be {@code null}
     * @param delta caller-defined time or delta value
     */
    public void drawAll(DrawingContext dc, double delta) {
        entities.forEach(entity -> entity.draw(dc, delta));
    }

    /**
     * Returns the mutable internal entity list.
     *
     * <p>Changes to the returned list immediately affect this system.</p>
     *
     * @return live mutable entity list
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Appends an entity.
     *
     * <p>Duplicate and null entries are accepted; a null entry will fail during
     * a later bulk update, draw, or component query.</p>
     *
     * @param entity entity to append
     */
    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    /**
     * Return a list of entities that contain a specific component type.
     *
     * <p>Matching uses {@link Entity#getComponentOfType(Class)} and therefore
     * requires exact runtime-class equality. The returned list is a new mutable
     * list and changes to it do not affect this system.</p>
     *
     * @param clazz exact component implementation class
     * @return entities containing an exact match, or {@code null} when
     * {@code clazz} is null
     */
    public List<Entity> getEntitiesWithComponent(Class<?> clazz) {
        if (clazz == null) return null;

        List<Entity> matched = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getComponentOfType(clazz) != null) matched.add(entity);
        }

        return matched;
    }
}
