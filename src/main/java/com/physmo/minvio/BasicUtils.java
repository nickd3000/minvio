package com.physmo.minvio;

import java.util.function.Predicate;

public class BasicUtils {

    public static Point createRandomPointInCircle(double x, double y, double radius) {
        double angle = Math.random()*Math.PI*2;
        radius *= Math.random();
        double xx = Math.sin(angle)*radius;
        double yy = Math.cos(angle)*radius;
        return new Point(x+xx,y+yy);
    }

    public static void ringOfPoints(int numPoints, double x, double y, double radius, double angle, PointInterface function) {
        double angleSpan = (Math.PI * 2)/(double)numPoints;
        for (int i=0;i<numPoints;i++) {
            double xx = x + Math.sin(angle+i*angleSpan)*radius;
            double yy = y + Math.cos(angle+i*angleSpan)*radius;
            function.process(new Point(xx, yy));
        }
    }
}
