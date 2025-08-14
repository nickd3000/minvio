package com.physmo.reference.experiments;

import com.physmo.minvio.MinvioApp;

public class ListPlotter extends MinvioApp {

    double scale = 3;

    public static void main(String... args) {
        MinvioApp app = new ListPlotter();
        app.start(400, 400, "Mapper Test", 60);
    }

    @Override
    public void draw(double delta) {

        // Circle
        //double[] points = new double[]{50,60, 54.94,58.09, 59.39,54.39, 62.94,50, 64.94,44.39, 65,40, 64.94,35.61, 62.94,29.61, 59.39,25.61, 54.94,21.91, 50,20, 45.06,21.91, 40.61,25.61, 37.06,29.61, 35.06,35.61, 35,40, 35.06,44.39, 37.06,50, 40.61,54.39, 45.06,58.09, 50,60};

        // Cat
        //double[] points = new double[]{50,70, 60,65, 65,55, 60,45, 50,40, 40,45, 35,55, 40,65, 50,70, 50,40, 70,30, 70,10, 30,10, 30,30, 50,40, 70,30, 80,20, 40,65, 35,75, 45,75, 60,65, 55,75, 65,75};

        // Cat 2
        //double[] points = new double[]{50,70, 60,65, 65,55, 60,45, 50,40, 40,45, 35,55, 40,65, 50,70, 50,40, 70,30, 70,10, 30,10, 30,30, 50,40, 70,30, 80,20, 40,65, 35,75, 45,75, 60,65, 55,75, 65,75, 55,55, 60,50, 45,55, 40,50};

        // Cat more details
        //double[] points = new double[]{50,70, 60,65, 65,55, 60,45, 50,40, 40,45, 35,55, 40,65, 50,70, 50,40, 70,30, 70,10, 30,10, 30,30, 50,40, 70,30, 80,20, 40,65, 35,75, 45,75, 60,65, 55,75, 65,75, 55,55, 60,50, 45,55, 40,50, 50,40, 50,30, 50,20, 60,25, 60,35, 40,35, 40,25, 50,20, 50,10};

        // space filling curve
        double[] points = new double[]{0, 0, 0, 33.33, 33.33, 33.33, 33.33, 0, 66.66, 0, 66.66, 33.33, 100, 33.33, 100, 0, 100, 66.66, 66.66, 66.66, 66.66, 100, 33.33, 100, 33.33, 66.66, 0, 66.66, 0, 100, 33.33, 100, 33.33, 66.66, 66.66, 66.66, 66.66, 100, 100, 100, 100, 66.66};

        double x1 = points[0];
        double y1 = points[1];

        for (int i = 1; i < (points.length / 2); i++) {
            double x2 = points[i * 2];
            double y2 = points[(i * 2) + 1];
            drawLine(x1 * scale, y1 * scale, x2 * scale, y2 * scale);
            x1 = x2;
            y1 = y2;
        }

    }
}
