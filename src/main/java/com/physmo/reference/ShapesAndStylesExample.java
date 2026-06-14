package com.physmo.reference;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;

/**
 * Demonstrates Minvio's shape primitives, clipping, alpha, and style stack.
 */
public class ShapesAndStylesExample extends MinvioApp {

    public static void main(String... args) {
        new ShapesAndStylesExample().start(640, 400, "Shapes and Styles", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Palette.GRAY_900);

        setDrawColor(Palette.AMBER);
        setStrokeWidth(4);
        drawEllipse(30, 30, 120, 70);
        drawArc(180, 30, 100, 100, 0, Math.PI * 1.5);
        drawTriangle(330, 110, 390, 30, 450, 110);

        int[] polygonX = {500, 550, 610, 590, 520};
        int[] polygonY = {30, 15, 55, 115, 95};
        drawPolygon(polygonX, polygonY, polygonX.length);

        pushStyle();
        setAlpha(0.65);
        setDrawColor(new Color(255, 90, 90));
        drawFilledEllipse(30, 160, 130, 90);
        setDrawColor(new Color(70, 170, 255));
        drawFilledEllipse(90, 160, 130, 90);
        setDrawColor(new Color(90, 220, 140));
        drawFilledTriangle(230, 250, 290, 150, 350, 250);
        popStyle();

        pushStyle();
        setClip(350, 150, 100, 100);
        setDrawColor(Palette.AMBER);
        drawFilledEllipse(320, 130, 160, 140);
        popStyle();

        setDrawColor(Palette.MINT);
        setStrokeWidth(3);
        drawPolyline(
                new int[]{30, 100, 170, 240, 310, 380, 450, 520, 610},
                new int[]{330, 290, 350, 300, 340, 285, 345, 300, 335},
                9);

        setDrawColor(Color.WHITE);
        setStrokeWidth(1);
        drawText("ellipse, arc, triangles, polygons, alpha, clipping, and style state", 30, 385);
    }
}
