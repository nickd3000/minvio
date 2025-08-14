package com.physmo.reference.minvioapp;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.AnchorManager;

import java.awt.Color;
import java.util.List;

/**
 * The AnchorExample class demonstrates the functionality of the AnchorManager
 * within the context of the MinvioApp framework. This application creates an
 * interactive experience where users can manipulate anchors visually and
 * interact with them through custom-drawn graphics.
 * <p>
 * This class extends MinvioApp and overrides specific lifecycle methods to
 * initialize and render interactive anchor-based graphics.
 * <p>
 * Key Features:
 * - Initializes a set of anchors with predefined positions.
 * - Allows interaction with anchors, including grabbing and mouse-over effects.
 * - Uses custom drawing logic for anchor presentation.
 * - Connects anchors visually using lines.
 * <p>
 * Methods:
 * - init(BasicDisplay bd): Sets up the application, initializes the AnchorManager,
 * defines custom drawing behavior for the anchors, and adds anchors with specific
 * positions.
 * - draw(double delta): Handles the rendering logic. Clears the screen, updates
 * anchor states, draws lines connecting anchors, and invokes anchor rendering.
 */
public class AnchorExample extends MinvioApp {

    AnchorManager anchorManager;
    Color background = new Color(218, 132, 78);

    public static void main(String[] args) {
        MinvioApp app = new AnchorExample();
        app.start(250, 250, "Anchor Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        anchorManager = new AnchorManager(6);
        anchorManager.add(20, 20);
        anchorManager.add(20, 180);
        anchorManager.add(180, 180);
        anchorManager.add(180, 20);

        // Optional custom anchor-drawing delegate.
        anchorManager.setAnchorDrawDelegate((bd1, point, radius, mouseOver, grabbed) -> {

            if (grabbed) {
                bd.getDrawingContext().setDrawColor(new Color(175, 236, 80));
                bd.getDrawingContext().drawFilledCircle(point, radius);
                bd.getDrawingContext().setDrawColor(new Color(1, 1, 1));
                bd.getDrawingContext().drawCircle(point, radius);
            } else if (mouseOver) {
                bd.getDrawingContext().setDrawColor(new Color(1, 1, 1));
                bd.getDrawingContext().drawFilledCircle(point, radius);
                bd.getDrawingContext().setDrawColor(new Color(236, 175, 80));
                bd.getDrawingContext().drawCircle(point, radius);
                bd.getDrawingContext().drawCircle(point, radius + 0.5);
            } else {
                bd.getDrawingContext().setDrawColor(new Color(1, 1, 1));
                bd.getDrawingContext().drawFilledCircle(point, radius);
            }

        });
    }

    @Override
    public void draw(double delta) {
        cls(background);
        anchorManager.update(getBasicDisplay());


        setDrawColor(new Color(225, 209, 118));

        List<Point> anchors = anchorManager.getAnchors();
        for (int i = 0; i < anchors.size() - 1; i++) {
            drawLine(anchors.get(i), anchors.get(i + 1));
        }

        anchorManager.draw(getDrawingContext());
    }
}
