package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * <p>Duplicate entries are accepted.</p>
     *
     * @param entity entity to append
     */
    public void addEntity(Entity entity) {
        this.entities.add(Objects.requireNonNull(entity, "entity"));
    }

    /**
     * Return a list of entities that contain a specific component type.
     *
     * <p>Matching uses {@link Entity#getComponentOfType(Class)} and therefore
     * supports assignable/interface-aware matching. The returned list is a new
     * mutable list and changes to it do not affect this system.</p>
     *
     * @param clazz component implementation class or interface
     * @return entities containing an assignable match
     */
    public List<Entity> getEntitiesWithComponent(Class<?> clazz) {
        Objects.requireNonNull(clazz, "component class");

        List<Entity> matched = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getComponentOfType(clazz) != null) matched.add(entity);
        }

        return matched;
    }
}
