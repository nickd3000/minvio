package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.BasicUtils;

import java.awt.Color;

class MapperTest {

    private static Color backgroundColour = new Color(10, 31, 45);

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);

        bd.setTitle("Mapper");

        // Clear the screen
        bd.setDrawColor(Color.BLUE);

        // Loop forever.
        while (true) {
            double x = bd.getMouseX();
            double y = bd.getMouseY();

            double mappedX = BasicUtils.mapper(x, 0, 400, 100, 300);
            double mappedY = BasicUtils.mapper(y, 0, 400, 100, 300);

            bd.drawRect(0, 0, 400 - 1, 400 - 1);
            bd.drawRect(100, 100, 200, 200);
            bd.drawLine(200, 200, mappedX, mappedY, 2);
            bd.drawCircle(mappedX, mappedY, 10);

            bd.repaint(60);
            bd.cls(backgroundColour);
        }
    }
}
