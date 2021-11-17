package com.physmo.minvioexamples.ecs;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.ecs.Entity;
import com.physmo.minvio.utils.ecs.EntitySystem;

import java.awt.Color;

// Entity-Component system example.
// NOTE: Moving mouse left and right changes tick speed.

public class EcsExample extends MinvioApp {

    EntitySystem entitySystem;
    int numberOfEntities = 20;

    public static void main(String[] args) {
        MinvioApp app = new EcsExample();
        app.start(new BasicDisplayAwt(400, 400), "Entity Component System", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        entitySystem = new EntitySystem();

        // Create our entities, they all have a move and bounce component,
        // Some also have a gravity component, there are two render components.
        for (int i = 0; i < numberOfEntities; i++) {
            Entity newEntity = new Entity();
            newEntity.position.set(200 + (Math.random() - 0.5) * 100, 200 + (Math.random() - 0.5) * 100, 0);
            newEntity.velocity.set((Math.random() - 0.5) * 200, (Math.random() - 0.5) * 200, 0);

            newEntity.addComponent(new MoveComponent());
            newEntity.addComponent(new BounceComponent());

            if (i % 3 == 0) {
                newEntity.setProperty("special", true);
            }

            if (i % 2 == 0) {
                newEntity.addDrawComponent(new DrawComponent(bd.getDistinctColor(i, 1)));
                newEntity.addComponent(new GravityComponent());
            } else {
                newEntity.addDrawComponent(new FlashyDrawComponent());
            }

            entitySystem.addEntity(newEntity);
        }

    }

    @Override
    public void update(BasicDisplay bd, double delta) {
        entitySystem.tickAll(bd, delta * bd.getMousePointNormalised().x);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(new Color(150, 150, 150, 89));
        entitySystem.drawAll(bd, delta);
    }
}
