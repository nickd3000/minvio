package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.Point;
import com.physmo.minvio.utils.AnchorManager;

import java.awt.Color;
import java.util.List;


public class AnchorExample extends MinvioApp {

    AnchorManager anchorManager;
    Color background = new Color(218, 132, 78);

    public static void main(String[] args) {
        MinvioApp app = new AnchorExample();
        app.start(new BasicDisplayAwt(250, 250), "Anchor Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        anchorManager = new AnchorManager(6);
        anchorManager.add(20, 20);
        anchorManager.add(20, 180);
        anchorManager.add(180, 180);
        anchorManager.add(180, 20);

        // Optional custom anchor-drawing delegate.
        anchorManager.setAnchorDrawDelegate((bd1, point, radius, mouseOver, grabbed) -> {

            if (grabbed) {
                bd.setDrawColor(new Color(175, 236, 80));
                bd.drawFilledCircle(point, radius);
                bd.setDrawColor(new Color(1, 1, 1));
                bd.drawCircle(point, radius);
            } else if (mouseOver) {
                bd.setDrawColor(new Color(1, 1, 1));
                bd.drawFilledCircle(point, radius);
                bd.setDrawColor(new Color(236, 175, 80));
                bd.drawCircle(point, radius);
                bd.drawCircle(point, radius + 0.5);
            } else {
                bd.setDrawColor(new Color(1, 1, 1));
                bd.drawFilledCircle(point, radius);
            }

        });
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(background);
        anchorManager.update(bd);


        bd.setDrawColor(new Color(225, 209, 118));

        List<Point> anchors = anchorManager.getAnchors();
        for (int i = 0; i < anchors.size() - 1; i++) {
            bd.drawLine(anchors.get(i), anchors.get(i + 1));
        }

        anchorManager.draw(bd);
    }
}
