package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingHelpers;

import java.awt.*;

class DrawingHelpersGrid {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(300, 300);

        bd.setTitle("DrawingHelpers.drawGrid()");

        while (true) {
            bd.cls(Color.lightGray);
            bd.setDrawColor(Color.WHITE);
            DrawingHelpers.drawGrid(bd, 10, 10, 280, 280, 5);
            bd.refresh(30);
        }
    }

}
