package com.physmo.reference.ecs;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;
import com.physmo.minvio.utils.ecs.Entity;
import com.physmo.minvio.utils.ecs.EntitySystem;

import java.awt.Color;

// Entity-Component system example.
// NOTE: Moving mouse left and right changes tick speed.

// Entity-Component System (ECS) is a way to organize code by separating data (Entities)
// from behavior (Components). This example shows how to build objects by adding
// different behaviors to them.

public class EcsExample extends MinvioApp {

    EntitySystem entitySystem; // The manager that keeps track of all our entities.
    int numberOfEntities = 20;

    public static void main(String[] args) {
        MinvioApp app = new EcsExample();
        app.start(new BasicDisplayAwt(400, 400), "Entity Component System", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        entitySystem = new EntitySystem();

        // Create our entities. Instead of having different classes for different types
        // of objects, we create a generic 'Entity' and add 'Components' to give it behavior.
        for (int i = 0; i < numberOfEntities; i++) {
            Entity newEntity = new Entity();

            // Set a random starting position and velocity.
            newEntity.position.set(200 + (Math.random() - 0.5) * 100, 200 + (Math.random() - 0.5) * 100, 0);
            newEntity.velocity.set((Math.random() - 0.5) * 200, (Math.random() - 0.5) * 200, 0);

            // Every entity gets the ability to move and bounce off walls.
            newEntity.addComponent(new MoveComponent());
            newEntity.addComponent(new BounceComponent());

            // We can also set custom properties on entities that components can look for.
            if (i % 3 == 0) {
                newEntity.setProperty("special", true);
            }

            // Mix and match components:
            // - Even numbered entities get a specific color and gravity.
            // - Odd numbered entities get a flashy changing color.
            if (i % 2 == 0) {
                newEntity.addDrawComponent(new DrawComponent(Palette.getDistinctColor(i, 1)));
                newEntity.addComponent(new GravityComponent());
            } else {
                newEntity.addDrawComponent(new FlashyDrawComponent());
            }

            // Add the finished entity to our system.
            entitySystem.addEntity(newEntity);
        }

    }

    @Override
    public void update(BasicDisplay bd, double delta) {
        // 'tickAll' updates the logic for every component in every entity.
        // We multiply delta by the mouse position to allow controlling the speed.
        entitySystem.tickAll(bd.getDrawingContext(), delta * bd.getMousePointNormalised().x);
    }

    @Override
    public void draw(double delta) {
        // Clear the screen with a semi-transparent gray to create a motion blur effect.
        cls(new Color(150, 150, 150, 89));

        // Ask the system to draw all entities.
        entitySystem.drawAll(getDrawingContext(), delta);
    }
}
