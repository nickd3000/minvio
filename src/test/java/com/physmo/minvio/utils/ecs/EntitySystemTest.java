package com.physmo.minvio.utils.ecs;

import com.physmo.minvio.DrawingContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

        system.tickAll(null, 0.5);
        system.drawAll(null, 0.75);

        assertEquals(List.of(
                "tick-first-0.5", "tick-second-0.5",
                "draw-first-0.75", "draw-second-0.75"), calls);
    }

    @Test
    void filtersByExactComponentTypeAndHandlesNull() {
        EntitySystem system = new EntitySystem();
        Entity first = new Entity().addComponent(new MarkerComponent());
        Entity second = new Entity().addComponent(new OtherComponent());
        Entity third = new Entity().addComponent(new MarkerComponent());
        system.addEntity(first);
        system.addEntity(second);
        system.addEntity(third);

        assertEquals(List.of(first, third), system.getEntitiesWithComponent(MarkerComponent.class));
        assertEquals(List.of(second), system.getEntitiesWithComponent(OtherComponent.class));
        assertEquals(List.of(), system.getEntitiesWithComponent(Component.class));
        assertNull(system.getEntitiesWithComponent(null));
    }

    private static Entity entityWithComponents(String name, List<String> calls) {
        return new Entity()
                .addComponent(new NamedComponent("tick-" + name, calls))
                .addDrawComponent(new NamedComponent("draw-" + name, calls));
    }

    private static class NamedComponent extends Component {
        private final String name;
        private final List<String> calls;

        private NamedComponent(String name, List<String> calls) {
            this.name = name;
            this.calls = calls;
        }

        @Override
        public void tick(DrawingContext dc, Entity e, double t) {
            calls.add(name + "-" + t);
        }
    }

    private static final class MarkerComponent extends Component {
        @Override
        public void tick(DrawingContext dc, Entity e, double t) {
        }
    }

    private static final class OtherComponent extends Component {
        @Override
        public void tick(DrawingContext dc, Entity e, double t) {
        }
    }
}
