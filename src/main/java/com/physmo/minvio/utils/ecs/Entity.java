package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    final List<UpdateComponent> components = new ArrayList<>();
    DrawComponent drawComponent;
    final Map<String, Object> properties = new HashMap<>();

    /**
     * Appends an update component.
     *
     * <p>Duplicate instances and duplicate component classes are permitted.</p>
     *
     * @param c component to append
     * @return this entity for fluent construction
     */
    public Entity addComponent(UpdateComponent c) {
        components.add(Objects.requireNonNull(c, "component"));
        return this;
    }

    /**
     * Replaces the component invoked by {@link #draw(DrawingContext, double)}.
     *
     * @param c replacement draw component
     * @return this entity for fluent construction
     */
    public Entity addDrawComponent(DrawComponent c) {
        drawComponent = Objects.requireNonNull(c, "draw component");
        return this;
    }

    /**
     * Removes this entity's draw component.
     *
     * @return this entity for fluent construction
     */
    public Entity clearDrawComponent() {
        drawComponent = null;
        return this;
    }

    /**
     * Invokes every update component in insertion order.
     *
     * <p>Structural modification of the component list during iteration is unsupported.</p>
     *
     * @param t caller-defined time or delta value
     */
    public void tick(double t) {
        components.forEach(c -> c.update(this, t));
    }

    /**
     * Invokes every update component in insertion order.
     *
     * <p>The drawing context parameter is retained for source and binary
     * compatibility. It is ignored because {@link UpdateComponent} receives only
     * the owning entity and delta value. Use {@link #tick(double)} for new code.</p>
     *
     * @param dc drawing context retained for compatibility; ignored
     * @param t  caller-defined time or delta value
     * @deprecated use {@link #tick(double)}
     */
    @Deprecated(since = "1.22")
    public void tick(DrawingContext dc, double t) {
        tick(t);
    }

    /**
     * Invokes the current draw component, if one is configured.
     *
     * @param dc drawing context to forward; may be {@code null}
     * @param t caller-defined time or delta value
     */
    public void draw(DrawingContext dc, double t) {
        if (drawComponent != null) {
            drawComponent.draw(dc, this, t);
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
     * Returns the first update component assignable to the requested type.
     *
     * <p>The dedicated draw component is not searched.</p>
     *
     * @param clazz component implementation class or interface, or {@code null}
     * @return first assignable match, or {@code null} when none exists or
     * {@code clazz} is null
     */
    public UpdateComponent getComponentOfType(Class<?> clazz) {
        if (clazz == null) return null;
        for (UpdateComponent component : components) {
            if (clazz.isInstance(component)) {
                return component;
            }
        }
        return null;
    }

    /**
     * Returns the draw component when it is assignable to the requested type.
     *
     * @param clazz draw component implementation class or interface, or
     *              {@code null}
     * @return matching draw component, or {@code null}
     */
    public DrawComponent getDrawComponentOfType(Class<?> clazz) {
        if (clazz == null || drawComponent == null) return null;
        return clazz.isInstance(drawComponent) ? drawComponent : null;
    }
}
