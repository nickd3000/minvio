package com.physmo.minvioexamples.gallery;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.Point;
import com.physmo.minvio.utils.AnchorManager;

import java.awt.Color;
import java.util.List;


public class Gasket2 extends MinvioApp {

    AnchorManager anchorManager;
    Color background = new Color(218, 132, 78);
    Point floatingPoint = new Point(0, 0);

    public static void main(String[] args) {
        MinvioApp app = new Gasket2();
        app.start(new BasicDisplayAwt(250, 250), "Anchor Example", 60);
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
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(background);
        anchorManager.update(bd);

        bd.setDrawColor(new Color(0, 0, 0));

        for (int i = 0; i < 15000; i++) {
            int ii = (int) (Math.random() * (double) anchorManager.getAnchors().size());
            Point p2 = anchorManager.getAnchors().get(ii);

            floatingPoint.x = (floatingPoint.x + p2.x) / 2;
            floatingPoint.y = (floatingPoint.y + p2.y) / 2;

            bd.drawFilledRect((int) floatingPoint.x, (int) floatingPoint.y, 2, 2);
        }

        bd.setDrawColor(new Color(225, 209, 118));
        List<Point> anchors = anchorManager.getAnchors();
        for (int i = 0; i < anchors.size() - 1; i++) {
            bd.drawLine(anchors.get(i), anchors.get(i + 1));
        }

        anchorManager.draw(bd);
    }
}
