package com.physmo.reference;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;

import java.awt.Color;

class MouseFollowExample {

    public static void main(String... args) {
        int width = 400;
        int height = 400;
        double x = 200, y = 200, speed = 0.1;
        BasicDisplay bd = new BasicDisplayAwt(width, height);

        bd.setTitle("Mouse Follow Example");
        DrawingContext dc = bd.getDrawingContext();

        while (true) {

            if (!bd.getMouseButtonLeft()) dc.cls(Color.black); // Don't clear screen when mouse clicked.

            double targetX = bd.getMouseX();
            x += (targetX - x) * speed;
            double targetY = bd.getMouseY();
            y += (targetY - y) * speed;

            dc.drawCircle(x, y, 20);

            bd.repaint(60);
        }
    }

}
