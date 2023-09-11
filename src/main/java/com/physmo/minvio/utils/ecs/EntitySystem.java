package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;

import java.util.ArrayList;
import java.util.List;

public class EntitySystem {

    private final List<Entity> entities = new ArrayList<>();

    public void tickAll(DrawingContext dc, double delta) {
        entities.forEach(entity -> entity.tick(dc, delta));
    }

    public void drawAll(DrawingContext dc, double delta) {
        entities.forEach(entity -> entity.draw(dc, delta));
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    /**
     * Return a list of entities that contain a specific component type.
     *
     * @param clazz The class of the component type we are interested in.
     * @return list of entities containing clazz
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
