package com.physmo.minvio.utils.ecs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertNull(entity.getComponentOfType(RecordingUpdateComponent.class));
    }

    @Test
    void componentsAreFluentTickedInInsertionOrderAndLookedUpByAssignableType() {
        Entity entity = new Entity();
        List<String> calls = new ArrayList<>();
        RecordingUpdateComponent first = new RecordingUpdateComponent("first", calls);
        RecordingUpdateComponent second = new RecordingUpdateComponent("second", calls);
        DerivedUpdateComponent derived = new DerivedUpdateComponent("derived", calls);

        assertSame(entity, entity.addComponent(first));
        entity.addComponent(second).addComponent(derived);
        entity.tick(0.25);

        assertEquals(List.of("first", "second", "derived"), calls);
        assertSame(first, entity.getComponentOfType(RecordingUpdateComponent.class));
        assertSame(first, entity.getComponentOfType(UpdateComponent.class));
        assertSame(derived, entity.getComponentOfType(DerivedUpdateComponent.class));
        assertSame(entity, first.entity);
        assertEquals(0.25, first.delta);
    }

    @SuppressWarnings("deprecation")
    @Test
    void deprecatedTickWithDrawingContextDelegatesToContextFreeUpdate() {
        Entity entity = new Entity();
        List<String> calls = new ArrayList<>();
        RecordingUpdateComponent component = new RecordingUpdateComponent("legacy", calls);
        entity.addComponent(component);

        entity.tick(null, 0.5);

        assertEquals(List.of("legacy"), calls);
        assertSame(entity, component.entity);
        assertEquals(0.5, component.delta);
    }

    @Test
    void drawComponentIsOptionalAndReceivesEntityAndDelta() {
        Entity entity = new Entity();
        RecordingDrawComponent draw = new RecordingDrawComponent("draw", new ArrayList<>());

        entity.draw(null, 1.0);
        assertSame(entity, entity.addDrawComponent(draw));
        entity.draw(null, 0.75);

        assertEquals(List.of("draw"), draw.calls);
        assertSame(entity, draw.entity);
        assertEquals(0.75, draw.delta);
        assertSame(draw, entity.getDrawComponentOfType(RecordingDrawComponent.class));
        assertSame(draw, entity.getDrawComponentOfType(DrawComponent.class));
    }

    @Test
    void drawComponentCanBeClearedExplicitly() {
        Entity entity = new Entity();
        RecordingDrawComponent draw = new RecordingDrawComponent("draw", new ArrayList<>());

        assertSame(entity, entity.addDrawComponent(draw));
        assertSame(entity, entity.clearDrawComponent());

        entity.draw(null, 1.0);
        assertTrue(draw.calls.isEmpty());
        assertNull(entity.getDrawComponentOfType(DrawComponent.class));
    }

    @Test
    void rejectsNullComponents() {
        Entity entity = new Entity();

        assertThrows(NullPointerException.class, () -> entity.addComponent(null));
        assertThrows(NullPointerException.class, () -> entity.addDrawComponent(null));
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

    private static class RecordingUpdateComponent implements UpdateComponent {
        private final String name;
        private final List<String> calls;
        private Entity entity;
        private double delta;

        private RecordingUpdateComponent(String name, List<String> calls) {
            this.name = name;
            this.calls = calls;
        }

        @Override
        public void update(Entity e, double t) {
            calls.add(name);
            entity = e;
            delta = t;
        }
    }

    private static final class DerivedUpdateComponent extends RecordingUpdateComponent {
        private DerivedUpdateComponent(String name, List<String> calls) {
            super(name, calls);
        }
    }

    private static final class RecordingDrawComponent implements DrawComponent {
        private final String name;
        private final List<String> calls;
        private Entity entity;
        private double delta;

        private RecordingDrawComponent(String name, List<String> calls) {
            this.name = name;
            this.calls = calls;
        }

        @Override
        public void draw(com.physmo.minvio.DrawingContext dc, Entity entity, double delta) {
            calls.add(name);
            this.entity = entity;
            this.delta = delta;
        }
    }
}
