package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityTest {

    @Test
    void startsAtOriginWithNoPropertiesOrComponents() {
        Entity entity = new Entity();

        assertEquals(0.0, entity.position.x);
        assertEquals(0.0, entity.position.y);
        assertEquals(0.0, entity.position.z);
        assertEquals(0.0, entity.velocity.x);
        assertTrue(entity.getProperty("missing").isEmpty());
        assertNull(entity.getComponentOfType(null));
        assertNull(entity.getComponentOfType(RecordingComponent.class));
    }

    @Test
    void componentsAreFluentTickedInInsertionOrderAndLookedUpExactly() {
        Entity entity = new Entity();
        List<String> calls = new ArrayList<>();
        RecordingComponent first = new RecordingComponent("first", calls);
        RecordingComponent second = new RecordingComponent("second", calls);
        DerivedComponent derived = new DerivedComponent("derived", calls);

        assertSame(entity, entity.addComponent(first));
        entity.addComponent(second).addComponent(derived);
        entity.tick(null, 0.25);

        assertEquals(List.of("first", "second", "derived"), calls);
        assertSame(first, entity.getComponentOfType(RecordingComponent.class));
        assertSame(derived, entity.getComponentOfType(DerivedComponent.class));
        assertSame(entity, first.entity);
        assertEquals(0.25, first.delta);
    }

    @Test
    void drawComponentIsOptionalAndReceivesEntityAndDelta() {
        Entity entity = new Entity();
        RecordingComponent draw = new RecordingComponent("draw", new ArrayList<>());

        entity.draw(null, 1.0);
        assertSame(entity, entity.addDrawComponent(draw));
        entity.draw(null, 0.75);

        assertEquals(List.of("draw"), draw.calls);
        assertSame(entity, draw.entity);
        assertEquals(0.75, draw.delta);
    }

    @Test
    void propertiesCanBeInsertedReplacedAndClearedWithNull() {
        Entity entity = new Entity();

        entity.setProperty("score", 10);
        assertEquals(10, entity.getProperty("score").orElseThrow());

        entity.setProperty("score", 20);
        assertEquals(20, entity.getProperty("score").orElseThrow());

        entity.setProperty("score", null);
        assertTrue(entity.getProperty("score").isEmpty());
    }

    private static class RecordingComponent extends Component {
        private final String name;
        private final List<String> calls;
        private Entity entity;
        private double delta;

        private RecordingComponent(String name, List<String> calls) {
            this.name = name;
            this.calls = calls;
        }

        @Override
        public void tick(DrawingContext dc, Entity e, double t) {
            calls.add(name);
            entity = e;
            delta = t;
        }
    }

    private static final class DerivedComponent extends RecordingComponent {
        private DerivedComponent(String name, List<String> calls) {
            super(name, calls);
        }
    }
}
