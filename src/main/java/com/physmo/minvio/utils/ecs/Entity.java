package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Entity {
    public Vec3 position = new Vec3(0, 0, 0);
    public Vec3 velocity = new Vec3(0, 0, 0);

    final List<Component> components = new ArrayList<>();
    Component drawComponent;
    final Map<String, Object> properties = new HashMap<>();

    public Entity addComponent(Component c) {
        components.add(c);
        return this;
    }

    public Entity addDrawComponent(Component c) {
        drawComponent = c;
        return this;
    }

    public void tick(BasicDisplay bd, double t) {
        components.forEach(c -> c.tick(bd, this, t));
    }

    public void draw(BasicDisplay bd, double t) {
        drawComponent.tick(bd, this, t);
    }

    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    public Optional<Object> getProperty(String name) {
        return Optional.ofNullable(properties.get(name));
    }

    public Component getComponentOfType(Class<?> clazz) {
        if (clazz == null) return null;
        for (Component component : components) {
            if (component.getClass() == clazz) {
                return component;
            }
        }
        return null;
    }
}
