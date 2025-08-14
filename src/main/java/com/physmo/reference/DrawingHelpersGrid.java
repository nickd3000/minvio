package com.physmo.reference;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.DrawingHelpers;

import java.awt.Color;

class DrawingHelpersGrid {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(300, 300);
        DrawingContext dc = bd.getDrawingContext();

        bd.setTitle("DrawingHelpers.drawGrid()");

        while (true) {
            dc.cls(Color.lightGray);
            dc.setDrawColor(Color.WHITE);
            DrawingHelpers.drawGrid(bd, 10, 10, 280, 280, 5);
            bd.repaint(30);
        }
    }

}
