package com.physmo.reference.gallery;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

class CubeWave extends MinvioApp {

    public static final int FPS = 60;
    static int alpha = 100;
    static final Color backCol = new Color(92, 108, 113, alpha);
    static final Color topCol = new Color(249, 249, 238, alpha);
    static final Color leftCol = new Color(96, 159, 184, alpha);
    static final Color rightCol = new Color(159, 209, 245, alpha);
    static double time = 0;

    public static void main(String... args) {
        MinvioApp app = new CubeWave();
        app.start(400, 400, "Cube Wave", FPS);
    }

    @Override
    public void draw(double delta) {
        cls(backCol);
        time += delta * 2;

        drawColumns(getDrawingContext(), 5, 200 + 45);
        drawColumns(getDrawingContext(), 5, 200 - 45);
    }

    public static void drawColumns(DrawingContext dc, int xx, int yy) {
        double oblique = 0.5;
        int columnWidth = 30;
        int columnHeight = 30;
        int gridSize = (dc.getWidth() / columnWidth) - 1;

        for (int x = gridSize; x > 0; x--) {
            for (int y = 0; y < gridSize; y++) {

                int p2 = -(int) (x * (((double) columnWidth / 2) * oblique));
                int p1 = (int) (y * (((double) columnWidth / 2) * oblique));

                int h = (int) ((Math.sin(x / 2.0 + time) * 10) + (Math.cos(y / 1.5 + time) * 10));
                h += (int) ((Math.cos(x / 3.0 + time) * 5) + (Math.sin(x / 2.5 + time) * 5));
                drawColumn(dc, (xx + (x * (columnWidth / 2))) + (y * ((columnWidth / 2))), h + yy + p1 + p2, columnWidth, columnHeight);
            }
        }
    }

    public static void drawColumn(DrawingContext dc, int x, int y, int w, int h) {
        double oblique = 0.5; // 1==45 degree slopes.
        //    b      |
        // a     c   | drop (half width)*oblique
        //    d
        // e     g
        //    f
        int drop = (int) ((w / 2) * oblique);
        int hw = w / 2;

        int[] xPoints, yPoints;

        // Right.
        dc.setDrawColor(rightCol);
        xPoints = new int[]{x + w, x + hw, x + hw, x + w, x + w};
        yPoints = new int[]{y + drop, y + drop * 2, y + h, y + h - drop, y + drop};
        dc.drawFilledPolygon(xPoints, yPoints, 5);

        // Left.
        dc.setDrawColor(leftCol);
        xPoints = new int[]{x, x + hw, x + hw, x, x};
        yPoints = new int[]{y + drop, y + drop * 2, y + h, y + h - drop, y + drop};
        dc.drawFilledPolygon(xPoints, yPoints, 5);

        // Top.
        dc.setDrawColor(topCol);
        xPoints = new int[]{x, x + hw, x + w, x + hw, x};
        yPoints = new int[]{y + drop, y, y + drop, y + drop * 2, y + drop};
        dc.drawFilledPolygon(xPoints, yPoints, 5);
    }
}
