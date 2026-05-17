package com.physmo.reference.gallery;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.AnchorManager;

import java.awt.Color;
import java.util.List;


/**
 * Gasket2 is an application that extends MinvioApp to demonstrate a visual anchor-based effect.
 * It creates a dynamic fractal-like visualization by calculating intermediate points based on
 * anchored points and drawing them on the screen. The application uses the AnchorManager to manage
 * anchor points and perform various updates and drawings.
 * <p>
 * Key features include:
 * - Initialization of anchor points.
 * - Dynamic floating point calculations based on anchor points.
 * - Randomized color changes for visual effects.
 * - Drawing filled rectangles at calculated points.
 * - Connecting anchor points with lines.
 */
public class Gasket2 extends MinvioApp {

    AnchorManager anchorManager;
    Color background = new Color(218, 132, 78);
    Point floatingPoint = new Point(0, 0);

    public static void main(String[] args) {
        MinvioApp app = new Gasket2();
        app.start(250, 250, "Anchor Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        anchorManager = new AnchorManager(6);
        anchorManager.add(20, 20);
        anchorManager.add(20, 180);
        anchorManager.add(180, 180);


        floatingPoint.x = anchorManager.getAnchors().get(0).x;
        floatingPoint.y = anchorManager.getAnchors().get(0).y;
    }


    @Override
    public void draw(double delta) {
        cls(background);
        anchorManager.update(getBasicDisplay());

        setDrawColor(new Color(0, 0, 0));

        int ii = 0, ip, ic = 0;

        for (int i = 0; i < 15000 * 4; i++) {
            ip = ii;
            ii = (int) (Math.random() * (double) anchorManager.getAnchors().size());
            ic += ii;
            Point p2 = anchorManager.getAnchors().get(ii);

            double divisor = 2.0;

            floatingPoint.x = (floatingPoint.x + p2.x) / divisor;
            floatingPoint.y = (floatingPoint.y + p2.y) / divisor;

            switch ((ii + ip) % 3) {
                case 0 -> setDrawColor(Color.RED);
                case 1 -> setDrawColor(Color.BLUE);
                case 2 -> setDrawColor(Color.GREEN);
            }

            drawFilledRect((int) floatingPoint.x, (int) floatingPoint.y, 2, 2);
        }

        setDrawColor(new Color(225, 209, 118));
        List<Point> anchors = anchorManager.getAnchors();
        for (int i = 0; i < anchors.size() - 1; i++) {
            drawLine(anchors.get(i), anchors.get(i + 1));
        }

        anchorManager.draw(getDrawingContext());
    }
}
