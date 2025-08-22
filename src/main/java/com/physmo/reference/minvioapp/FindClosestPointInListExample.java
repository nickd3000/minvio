package com.physmo.reference.minvioapp;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.BasicUtils;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The FindClosestPointInListExample class extends the MinvioApp and provides an example application
 * that finds the closest point to the mouse pointer from a list of randomly generated points.
 * The closest point is highlighted in a different color.
 */
public class FindClosestPointInListExample extends MinvioApp {


    List<Point> points = new ArrayList<>();
    Color backgroundColor = Palette.BERRY;
    Color col1 = Palette.AMBER;
    Color col2 = Palette.BUBBLEGUM;

    public static void main(String... args) {
        MinvioApp app = new FindClosestPointInListExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(400, 400, "Find Closest Point In List", 60);
    }


    @Override
    public void init(BasicDisplay bd) {
        for (int i = 0; i < 100; i++) {
            points.add(BasicUtils.createRandomPointInCircle(200, 200, 175));
        }
    }

    @Override
    public void draw(double delta) {
        cls(backgroundColor);

        int closestPointIndex = BasicUtils.findClosestPointInList(points, getBasicDisplay().getMousePoint(), 400);

        for (int i = 0; i < points.size(); i++) {
            if (i == closestPointIndex) setDrawColor(col2);
            else setDrawColor(col1);

            drawFilledCircle(points.get(i), 5);
        }

    }
}
