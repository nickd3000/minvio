package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Teaches ArrayList by adding a dot each time the mouse is clicked.
 */
class ArrayListExample extends MinvioApp {

    // An ArrayList can grow as the program runs.
    List<Point> points = new ArrayList<>();
    boolean mouseWasDown;

    public static void main(String... args) {
        MinvioApp app = new ArrayListExample();
        app.start(400, 300, "ArrayList Example", 60);
    }

    @Override
    public void draw(double delta) {
        boolean mouseIsDown = getBasicDisplay().getMouseButtonLeft();

        // Add one point when the button changes from up to down.
        if (mouseIsDown && !mouseWasDown) {
            points.add(new Point(getMouseX(), getMouseY()));
        }
        mouseWasDown = mouseIsDown;

        cls(new Color(32, 37, 45));
        setDrawColor(new Color(255, 190, 82));

        for (Point point : points) {
            drawFilledCircle(point, 8);
        }

        setDrawColor(Color.WHITE);
        drawText("Click to add dots. Count: " + points.size(), 20, 30);
    }
}
