package com.physmo.minvio.utils;

import com.physmo.minvio.BasicDisplay;

import java.awt.Color;
import java.text.DecimalFormat;

public class BasicGraph {

    private static final double INERTIA = 0.1;
    private static final DecimalFormat df2 = new DecimalFormat(".##");
    private final double[] values;
    private final int numPoints;
    private double maxValue;
    private double minValue;
    private double floatingMax;
    private double floatingMin;
    private int headPos;

    public BasicGraph(int numPoints) {
        this.numPoints = numPoints;
        values = new double[numPoints];
        headPos = 0;
        maxValue = 0.1;
        minValue = -0.1;
        floatingMax = maxValue;
        floatingMin = minValue;
    }

    public void addData(double val) {
        val = -val;
        values[headPos++] = val;
        if (headPos >= numPoints) headPos = 0;
    }

    public void draw(BasicDisplay d, int x, int y, int width, int height, Color c) {

        floatingMax = floatingMax - ((floatingMax - maxValue) * INERTIA);
        floatingMin = floatingMin - ((floatingMin - minValue) * INERTIA);
        //double zoomSpan = Math.max(Math.abs(maxValue), Math.abs(minValue))*2;
        double zoomSpan = Math.max(Math.abs(floatingMax), Math.abs(floatingMin)) * 2.2;

        d.setDrawColor(Color.black);
        d.drawLine(x, y + (height / 2), x + width, y + (height / 2));
        int count = 0;
        int readPos = headPos;
        double rawValue;
        double px, py;
        maxValue = 0.001;
        minValue = -0.001;
        while (count < numPoints) {
            rawValue = values[readPos++];

            if (rawValue > maxValue) maxValue = rawValue;
            if (rawValue < minValue) minValue = rawValue;

            if (readPos >= numPoints) readPos = 0;
            count++;

            px = count * ((double) width / (double) numPoints);
            px += x;
            py = (rawValue / zoomSpan) * height;
            py += y + (height / 2.0);
            if (py <= y || py >= y + height) continue;
            d.setDrawColor(c);
            d.drawFilledRect((int) px, (int) py, 2, 2);
        }

        d.setDrawColor(Color.black);
        d.drawRect(x, y, width, height);

        String maxText = df2.format(Math.max(floatingMax, Math.abs(floatingMin)));
        //d.drawText(maxText, x+width-40+15, y+15);

        //d.drawText(maxText, x+width-70+15, y+15);
        d.drawText(maxText, 5, y + 15 + 5);
    }
}
