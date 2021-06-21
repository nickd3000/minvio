package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

class CubeWave {

    public static final int FPS = 60;
    static final Color backCol = new Color(92, 108, 113);
    static final Color topCol = new Color(249, 249, 238, 224);
    static final Color leftCol = new Color(96, 159, 184);
    static final Color rightCol = new Color(159, 209, 245);
    static double time = 0;

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);

        bd.setTitle("CubeWave");

        // Define 3 points representing the 3 points of the triangle.
        double[] pointList = {0.5, 0, 0, 1, 1, 1};

        // Set the initial position for the point.
        double x = pointList[0];
        double y = pointList[1];

        // Clear the screen to dark gray.
        bd.cls(Color.darkGray);
        bd.setDrawColor(Color.BLUE);
        int loopCount = 0;

        // Loop forever.
        while (true) {

            bd.cls(backCol);
            time += 0.05;

            //drawColumn(bd,0,0,30,200);
            drawColumns(bd, 5, 200 + 45);
            drawColumns(bd, 5, 200 - 45);


            loopCount++;
            bd.repaint(FPS);

        }
    }

    public static void drawColumns(BasicDisplay bd, int xx, int yy) {
        double oblique = 0.5;
        int columnWidth = 20;
        int columnHeight = 30;
        int gridSize = (bd.getWidth() / columnWidth) - 1;

        for (int x = gridSize; x > 0; x--) {
            for (int y = 0; y < gridSize; y++) {

                int p2 = -(int) (x * ((columnWidth / 2) * oblique));
                int p1 = (int) (y * ((columnWidth / 2) * oblique));

                int h = (int) ((Math.sin(x / 2.0 + time) * 10) + (Math.cos(y / 1.5 + time) * 10));
                h += (int) ((Math.cos(x / 3.0 + time) * 5) + (Math.sin(x / 2.5 + time) * 5));
                drawColumn(bd, (xx + (x * (columnWidth / 2))) + (y * ((columnWidth / 2))), h + yy + p1 + p2, columnWidth, columnHeight);
            }
        }
    }

    public static void drawColumn(BasicDisplay bd, int x, int y, int w, int h) {
        double oblique = 0.5; // 1==45 degree slopes.
        //    b      |
        // a     c   | drop (half width)*oblique
        //    d
        //
        // e     g
        //    f
        int drop = (int) ((w / 2) * oblique);
        int hw = w / 2;
        int hh = h / 2;

        // Bounding rect for testing.
        //bd.drawRect(x,y,(x+w),(y+h));
        int[] xPoints, yPoints;

        // Right.
        bd.setDrawColor(rightCol);
        xPoints = new int[]{x + w, x + hw, x + hw, x + w, x + w};
        yPoints = new int[]{y + drop, y + drop * 2, y + h, y + h - drop, y + drop};
        bd.drawFilledPolygon(xPoints, yPoints, 5);

        // Left.
        bd.setDrawColor(leftCol);
        xPoints = new int[]{x, x + hw, x + hw, x, x};
        yPoints = new int[]{y + drop, y + drop * 2, y + h, y + h - drop, y + drop};
        bd.drawFilledPolygon(xPoints, yPoints, 5);

        // Top.
        bd.setDrawColor(topCol);
        xPoints = new int[]{x, x + hw, x + w, x + hw, x};
        yPoints = new int[]{y + drop, y, y + drop, y + drop * 2, y + drop};
        bd.drawFilledPolygon(xPoints, yPoints, 5);


    }
}
