package com.physmo.reference;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.BasicUtils;

import java.awt.Color;

// ringOfPoints is a utility function that creates a list of
// evenly spaced points in a ring formation and allows us to
// perform an action on each.
public class RingOfPointsExample {
    static double angle = 0;

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(200, 200);
        DrawingContext dc = bd.getDrawingContext();

        bd.setTitle("Ring Of Points Example");
        dc.setFont(10);


        while (true) {
            angle += 0.01;
            dc.cls(Color.lightGray);
            dc.setDrawColor(Color.WHITE);

            BasicUtils.ringOfPoints(10, 100, 100, 70, angle,
                    (Point p) -> {

                        dc.drawFilledCircle(p.x, p.y, 3);

                        BasicUtils.ringOfPoints(3, p.x, p.y, 10, -angle * 2,
                                (Point pp) -> {
                                    dc.drawFilledCircle(pp.x, pp.y, 3);
                                });
                    });


            bd.repaint(30);
        }
    }
}
