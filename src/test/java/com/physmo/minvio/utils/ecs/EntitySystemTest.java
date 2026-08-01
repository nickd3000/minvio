package com.physmo.minvio.utils.ecs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

class EntitySystemTest {

    @Test
    void entitiesRemainInInsertionOrderAndReturnedListIsLive() {
        EntitySystem system = new EntitySystem();
        Entity first = new Entity();
        Entity second = new Entity();

        system.addEntity(first);
        system.getEntities().add(second);

        assertEquals(List.of(first, second), system.getEntities());
        assertSame(system.getEntities(), system.getEntities());
    }

    @Test
    void tickAndDrawAllDelegateInEntityOrder() {
        EntitySystem system = new EntitySystem();
        List<String> calls = new ArrayList<>();
        system.addEntity(entityWithComponents("first", calls));
        system.addEntity(entityWithComponents("second", calls));

        system.tickAll(0.5);
        system.drawAll(null, 0.75);

        assertEquals(List.of(
                "tick-first-0.5", "tick-second-0.5",
                "draw-first-0.75", "draw-second-0.75"), calls);
    }

    @SuppressWarnings("deprecation")
    @Test
    void deprecatedTickAllWithDrawingContextDelegatesToContextFreeUpdate() {
        EntitySystem system = new EntitySystem();
        List<String> calls = new ArrayList<>();
        system.addEntity(entityWithComponents("first", calls));

        system.tickAll(null, 0.5);

        assertEquals(List.of("tick-first-0.5"), calls);
    }

    @Test
    void filtersByAssignableComponentTypeAndRejectsNull() {
        EntitySystem system = new EntitySystem();
        Entity first = new Entity().addComponent(new MarkerComponent());
        Entity second = new Entity().addComponent(new OtherComponent());
        Entity third = new Entity().addComponent(new DerivedMarkerComponent());
        system.addEntity(first);
        system.addEntity(second);
        system.addEntity(third);

        assertEquals(List.of(first, third), system.getEntitiesWithComponent(MarkerComponent.class));
        assertEquals(List.of(second), system.getEntitiesWithComponent(OtherComponent.class));
        assertEquals(List.of(first, second, third), system.getEntitiesWithComponent(UpdateComponent.class));
        assertThrows(NullPointerException.class, () -> system.getEntitiesWithComponent(null));
    }

    @Test
    void rejectsNullEntities() {
        EntitySystem system = new EntitySystem();

        assertThrows(NullPointerException.class, () -> system.addEntity(null));
    }

    private static Entity entityWithComponents(String name, List<String> calls) {
        return new Entity()
                .addComponent(new NamedUpdateComponent("tick-" + name, calls))
                .addDrawComponent(new NamedDrawComponent("draw-" + name, calls));
    }

    private static class NamedUpdateComponent implements UpdateComponent {
        private final String name;
        private final List<String> calls;

        private NamedUpdateComponent(String name, List<String> calls) {
            this.name = name;
            this.calls = calls;
        }

        @Override
        public void update(Entity e, double t) {
            calls.add(name + "-" + t);
        }
    }

    private static final class NamedDrawComponent implements DrawComponent {
        private final String name;
        private final List<String> calls;

        private NamedDrawComponent(String name, List<String> calls) {
            this.name = name;
            this.calls = calls;
        }

        @Override
        public void draw(com.physmo.minvio.DrawingContext dc, Entity entity, double delta) {
            calls.add(name + "-" + delta);
        }
    }

    private static class MarkerComponent implements UpdateComponent {
        @Override
        public void update(Entity entity, double delta) {
        }
    }

    private static final class DerivedMarkerComponent extends MarkerComponent {
    }

    private static final class OtherComponent implements UpdateComponent {
        @Override
        public void update(Entity entity, double delta) {
        }
    }
}
