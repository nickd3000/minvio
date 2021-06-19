package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.BasicUtils;
import com.physmo.minvio.Point;

import java.awt.*;

// ringOfPoints is a utility function that creates a list of
// evenly spaced points in a ring formation and allows us to
// perform an action on each.
public class RingOfPointsExample {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(200, 200);

        bd.setTitle("Ring Of Points Example");
        bd.setFont(10);

        double angle = 0;

        while (true) {
            angle += 0.01;
            bd.cls(Color.lightGray);
            bd.setDrawColor(Color.WHITE);

            BasicUtils.ringOfPoints(10, 100, 100, 70, angle,
                    (Point p) -> {
                        bd.drawFilledCircle(p.x, p.y, 3);
                    });


            bd.repaint(30);
        }
    }
}
