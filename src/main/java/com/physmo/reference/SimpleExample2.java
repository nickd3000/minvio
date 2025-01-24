package com.physmo.reference;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;

import java.awt.Color;


/**
 * SimpleExample2 demonstrates basic graphical drawing using the BasicDisplay framework.
 * The program utilizes sinusoidal calculations to create dynamic visual patterns
 * that update and interact based on the mouse cursor position.
 * <p>
 * Features include:
 * - Real-time drawing of points in different colors.
 * - Shapes and patterns changing dynamically based on time and randomized parameters.
 * - Mouse coordinates influencing graphical attributes dynamically.
 */
class SimpleExample2 {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);
        DrawingContext dc = bd.getDrawingContext();

        bd.setTitle("Simple Example");

        double x, y, x2, y2, a = 0, r1 = 60, r2 = 40;
        int count = 0;

        dc.cls(Color.lightGray);

        int[] multipliers = {1, 2, 3, 4};

        while (true) {
            count++;
            a += 0.003;

            dc.setDrawColor(Color.WHITE);
            x = (Math.sin(a * multipliers[0]) * r1);
            y = (Math.cos(a * multipliers[1]) * r1);
            x2 = (Math.sin(a * multipliers[2]) * r2);
            y2 = (Math.cos(a * multipliers[3]) * r2);

            //dc.drawFilledRect(100+(int)(x+x2),100+(int)(y+y2),2, 2);
            dc.setDrawColor(Color.YELLOW);
            dc.drawPoint(200 + (int) (x), 200 + (int) (y));
            dc.setDrawColor(Color.MAGENTA);
            dc.drawPoint(200 + (int) (x2), 200 + (int) (y2));
            dc.setDrawColor(Color.BLUE);
            dc.drawPoint(200 + (int) (x + x2), 200 + (int) (y + y2));


            if ((count % 1000) == 0) {
                bd.repaint(60);
                r1 = bd.getMouseX() / 2;
                r2 = 200 - r1;
                dc.cls(Color.lightGray);
            }

            if ((count % 500000) == 0) {

                for (int i = 0; i < 4; i++) {
                    multipliers[i] = 1 + (int) (Math.random() * 4);
                }
            }
        }
    }

}
