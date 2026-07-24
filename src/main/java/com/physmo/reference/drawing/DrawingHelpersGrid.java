package com.physmo.reference.drawing;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.DrawingHelpers;

import java.awt.Color;

/**
 * Demonstrates the DrawingHelpers grid utility.
 */
class DrawingHelpersGrid extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new DrawingHelpersGrid();
        app.start(300, 300, "DrawingHelpers.drawGrid()", 30);
    }

    @Override
    public void draw(double delta) {
        cls(Color.LIGHT_GRAY);
        setDrawColor(Color.WHITE);
        DrawingHelpers.drawGrid(getBasicDisplay(), 10, 10, 280, 280, 5);
    }
}
