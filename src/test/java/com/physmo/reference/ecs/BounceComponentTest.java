package com.physmo.reference.ecs;

import com.physmo.minvio.utils.ecs.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BounceComponentTest {

    @Test
    void defaultConstructorUsesCurrentExampleBoundsAndPadding() {
        Entity entity = new Entity();
        entity.position.set(500, 500, 0);
        entity.velocity.set(10, 20, 0);

        new BounceComponent().update(entity, 0.0);

        assertEquals(380.0, entity.position.x);
        assertEquals(380.0, entity.position.y);
        assertEquals(-10.0, entity.velocity.x);
        assertEquals(-20.0, entity.velocity.y);
    }

    @Test
    void customBoundsAndPaddingConstrainEdges() {
        Entity entity = new Entity();
        entity.position.set(5, 95, 0);
        entity.velocity.set(-10, 20, 0);

        new BounceComponent(100, 100, 10).update(entity, 0.0);

        assertEquals(10.0, entity.position.x);
        assertEquals(90.0, entity.position.y);
        assertEquals(10.0, entity.velocity.x);
        assertEquals(-20.0, entity.velocity.y);
    }
}
