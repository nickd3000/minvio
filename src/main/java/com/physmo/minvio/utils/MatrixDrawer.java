package com.physmo.minvio.utils;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.Utils;

import java.awt.Color;

// TODO: precalc angles and distances
public class MatrixDrawer {

    final int width;
    final int height;
    double[] angles;
    double[] distances;

    public MatrixDrawer(int width, int height) {
        this.width = width;
        this.height = height;
        preCalc();
    }

    public void preCalc() {
        int w = width;
        int h = height;
        int numCells = w * h;
        angles = new double[numCells];
        distances = new double[numCells];
        int index = 0;

        double distanceScale = w / 2;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                double dx = (w / 2) - x;
                double dy = (h / 2) - y;
                double d = Math.sqrt((dx * dx) + (dy * dy));
                distances[index] = d / distanceScale;

                dx /= d;
                dy /= d;

                double angle1 = Math.atan2(dx, dy);
                if (angle1 < 0) angle1 += (Math.PI * 2);
                angles[index] = angle1;

                index++;
            }
        }

    }

    public void draw(DrawingContext dc, int xPos, int yPos, int scale, double time, MonoPixelWorker worker, Gradient gradient) {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int index = x + (y * width);
                double a = angles[index];
                double d = distances[index];
                double value = worker.go((double) (x) / (double) width, (double) (y) / (double) height, a, d, time);
                value = Utils.clamp(0, 1, value);

                if (gradient == null)
                    dc.setDrawColor(new Color((float) value, (float) value, (float) value));
                else
                    dc.setDrawColor(gradient.getColor(value));

                dc.drawFilledRect(xPos + (x * scale), yPos + (y * scale), scale, scale);
            }
        }

    }
}
