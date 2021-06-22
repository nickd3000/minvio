package com.physmo.minvio;

import java.awt.Color;
import java.awt.Font;

public class BasicUtils {

    private static Font font10 = new Font("Verdana", Font.PLAIN, 10);

    public static Point createRandomPointInCircle(double x, double y, double radius) {
        double angle = Math.random() * Math.PI * 2;
        radius *= Math.random();
        double xx = Math.sin(angle) * radius;
        double yy = Math.cos(angle) * radius;
        return new Point(x + xx, y + yy);
    }

    public static void ringOfPoints(int numPoints, double x, double y, double radius, double angle, PointInterface function) {
        double angleSpan = (Math.PI * 2) / (double) numPoints;
        for (int i = 0; i < numPoints; i++) {
            double xx = x + Math.sin(angle + i * angleSpan) * radius;
            double yy = y + Math.cos(angle + i * angleSpan) * radius;
            function.process(new Point(xx, yy));
        }
    }

    // Map one value range to another range.
    public static double mapper(double value, double inMin, double inMax, double outMin, double outMax) {
        if (outMax - outMin == 0) return 0;
        value = (value - inMin) / ((inMax - inMin) / (outMax - outMin));
        return value + outMin;
    }

    public static void drawCursorPosition(BasicDisplay bd, int x, int y) {
        int mouseX = bd.getMouseX();
        int mouseY = bd.getMouseY();
        int yOffset = 10;
        String str = "(" + mouseX + "," + mouseY + ")";
        bd.setFont(font10);
        bd.setDrawColor(Color.BLACK);
        bd.drawText(str, x + 1, y + 1 + yOffset);
        bd.setDrawColor(Color.WHITE);
        bd.drawText(str, x, y + yOffset);
    }
}
