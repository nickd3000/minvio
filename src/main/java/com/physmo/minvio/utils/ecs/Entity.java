package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Small mutable entity containing update components, one optional draw
 * component, and a string-keyed property map.
 *
 * <p>Components execute in insertion order. This class is not thread-safe and
 * its public position and velocity vectors are intentionally mutable.</p>
 */
public class Entity {
    /**
     * Mutable position initialized to the origin.
     */
    public Vec3 position = new Vec3(0, 0, 0);
    /** Mutable velocity initialized to zero. */
    public Vec3 velocity = new Vec3(0, 0, 0);

    final List<Component> components = new ArrayList<>();
    Component drawComponent;
    final Map<String, Object> properties = new HashMap<>();

    /**
     * Appends an update component.
     *
     * <p>Duplicate instances and duplicate component classes are permitted.
     * A null component is accepted but will fail when ticked or inspected.</p>
     *
     * @param c component to append
     * @return this entity for fluent construction
     */
    public Entity addComponent(Component c) {
        components.add(c);
        return this;
    }

    /**
     * Replaces the component invoked by {@link #draw(DrawingContext, double)}.
     *
     * @param c replacement draw component, or {@code null} to disable drawing
     * @return this entity for fluent construction
     */
    public Entity addDrawComponent(Component c) {
        drawComponent = c;
        return this;
    }

    /**
     * Invokes every update component in insertion order.
     *
     * <p>The drawing context and time value are forwarded unchanged. Structural
     * modification of the component list during iteration is unsupported.</p>
     *
     * @param dc drawing context to forward; may be {@code null}
     * @param t caller-defined time or delta value
     */
    public void tick(DrawingContext dc, double t) {
        components.forEach(c -> c.tick(dc, this, t));
    }

    /**
     * Invokes the current draw component, if one is configured.
     *
     * @param dc drawing context to forward; may be {@code null}
     * @param t caller-defined time or delta value
     */
    public void draw(DrawingContext dc, double t) {
        if (drawComponent != null) {
            drawComponent.tick(dc, this, t);
        }
    }

    /**
     * Associates a value with a property name, replacing any existing value.
     *
     * <p>Null names and values are accepted by the backing map. A null value is
     * indistinguishable from an absent property through {@link #getProperty(String)}.</p>
     *
     * @param name property key, which may be {@code null}
     * @param value property value, which may be {@code null}
     */
    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    /**
     * Looks up a property.
     *
     * @param name property key, which may be {@code null}
     * @return optional containing a non-null mapped value, or an empty optional
     * for an absent key or null value
     */
    public Optional<Object> getProperty(String name) {
        return Optional.ofNullable(properties.get(name));
    }

    /**
     * Returns the first update component whose runtime class exactly equals the
     * requested class.
     *
     * <p>Superclass and interface matches are not considered, and the dedicated
     * draw component is not searched.</p>
     *
     * @param clazz exact component implementation class, or {@code null}
     * @return first exact match, or {@code null} when none exists or
     * {@code clazz} is null
     */
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
