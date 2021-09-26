package com.physmo.minvio.utils;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.Point;
import com.physmo.minvio.PointInterface;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

public class BasicUtils {

    private static final Font font10 = new Font("Verdana", Font.PLAIN, 10);

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

    /**
     * Map one value range to another range.
     *
     * @param value  Input value
     * @param inMin  Start of input range
     * @param inMax  End of input range
     * @param outMin Start of output range
     * @param outMax End of output range
     * @return remapped input value.
     */
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


    public static int distance(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return (int) Math.sqrt(((dx * dx) + (dy * dy)));
    }

    public static int findClosestPointInList(List<Point> list, Point targetPoint, double threshHold) {

        double minDist = 10000;
        int minId = -1;

        for (int i = 0; i < list.size(); i++) {
            Point p = list.get(i);
            double dist = Point.distance(p, targetPoint);
            if (dist < threshHold && dist < minDist) {
                minDist = dist;
                minId = i;
            }
        }

        return minId;
    }
}
