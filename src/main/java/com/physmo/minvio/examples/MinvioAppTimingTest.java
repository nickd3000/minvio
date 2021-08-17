package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MinvioAppTimingTest extends MinvioApp {

    private final int timeListSize = 20;
    int updateDeltaPtr = 0;
    double[] updateDeltas = new double[timeListSize];
    int drawDeltaPtr = 0;
    double[] drawDeltas = new double[timeListSize];
    NumberFormat formatter = new DecimalFormat("#0.0000000");

    double rotationSpeed = 0.5;
    double angles[] = {0, 0};


    public static void main(String... args) {
        MinvioApp app = new MinvioAppTimingTest();
        app.start(new BasicDisplayAwt(600, 400));
    }

    @Override
    public void init(BasicDisplay bd) {
        setFps(10);
    }

    @Override
    public void update(double delta) {
        angles[0] += rotationSpeed * delta;
        updateDeltas[updateDeltaPtr++ % updateDeltas.length] = delta;
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {

        angles[1] += rotationSpeed * delta;
        drawDeltas[drawDeltaPtr++ % drawDeltas.length] = delta;

        bd.cls(Color.BLUE);
        bd.setDrawColor(Color.YELLOW);
        bd.drawText("Update delta average (In seconds) " + formatter.format(calculateListAverage(updateDeltas)), 20, 20);
        bd.drawText("Draw delta average (In seconds)   " + formatter.format(calculateListAverage(drawDeltas)), 20, 50);

        for (int i = 0; i < 2; i++) {
            double radius = 80 + (i * 20);
            int x = (int) (150 + Math.sin(angles[i]) * radius);
            int y = (int) (150 + Math.cos(angles[i]) * radius);
            bd.drawFilledCircle(x, y, 10);
        }
    }

    public double calculateListAverage(double[] list) {
        double sum = 0;
        for (double v : list) {
            sum += v;
        }
        return (double) sum / (double) list.length;
    }
}
