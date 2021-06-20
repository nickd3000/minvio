package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

class SimpleExample {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(200, 200);

        bd.setTitle("Simple Example");
        bd.setFont(10);

        while (true) {
            bd.cls(Color.lightGray);
            bd.setDrawColor(Color.WHITE);
            bd.drawFilledRect(75, 75, 50, 50);
            bd.setDrawColor(Color.BLUE);
            bd.drawCircle(100, 100, 70);

            bd.drawText("X:" + bd.getMouseX() + " Y:" + bd.getMouseY(), 10, 190);
            bd.repaint(30);
        }
    }

}
