package com.physmo.minvio.utils;


import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A basic system that provides a set of draggable points.
 */
public class AnchorManager {

    List<Point> anchors;
    double anchorRadius;
    boolean prevMouseButtonState = false;
    boolean grabActive = false;
    int grabbedId = 0;
    int mouseOverId = -1;
    AnchorDrawDelegate anchorDrawDelegate = null;
    boolean constrainToScreen = true;

    public AnchorManager(double anchorRadius) {
        this.anchorRadius = anchorRadius;
        anchors = new ArrayList<>();

        setAnchorDrawDelegate((bd, point, radius, mouseOver, grabbed) -> {

            if (grabbed) {
                bd.setDrawColor(new Color(255, 228, 94));
                bd.drawFilledCircle(point, radius);
                bd.setDrawColor(new Color(1, 1, 1));
                bd.drawCircle(point, radius);
            } else if (mouseOver) {
                bd.setDrawColor(new Color(1, 1, 1));
                bd.drawFilledCircle(point, radius);
                bd.setDrawColor(new Color(245, 5, 5));
                bd.drawCircle(point, radius);
                bd.drawCircle(point, radius + 0.5);
            } else {
                bd.setDrawColor(new Color(1, 1, 1));
                bd.drawFilledCircle(point, radius);
            }

        });
    }

    public boolean getConstrainToScreen() {
        return constrainToScreen;
    }

    public void setConstrainToScreen(boolean constrainToScreen) {
        this.constrainToScreen = constrainToScreen;
    }

    public void add(double x, double y) {
        anchors.add(new Point(x, y));
    }

    public void setAnchorDrawDelegate(AnchorDrawDelegate anchorDrawDelegate) {
        this.anchorDrawDelegate = anchorDrawDelegate;
    }

    public List<Point> getAnchors() {
        return anchors;
    }

    public void update(BasicDisplay bd) {
        boolean mouseButtonState = bd.getMouseButtonLeft();

        if (mouseButtonState == true && prevMouseButtonState == false) {
            int id = findCloseAnchor(bd.getMouseX(), bd.getMouseY(), anchorRadius);
            if (id != -1) {
                grabbedId = id;
                grabActive = true;
            }
        } else if (mouseButtonState == false && prevMouseButtonState == true) {
            grabActive = false;
        }


        if (grabActive && grabbedId < anchors.size()) {
            Point mp = constrainMouseToScreen(bd);
            anchors.get(grabbedId).x = mp.x;
            anchors.get(grabbedId).y = mp.y;
        }

        mouseOverId = findCloseAnchor(bd.getMouseX(), bd.getMouseY(), anchorRadius);

        prevMouseButtonState = mouseButtonState;
    }

    // TODO: Add utility function to return closest point from list of points.
    public int findCloseAnchor(double x, double y, double threshHold) {
        double minDist = 10000;
        int minId = -1;
        Point targetPoint = new Point(x, y);
        for (int i = 0; i < anchors.size(); i++) {
            Point p = anchors.get(i);
            double dist = Point.distance(p, targetPoint);
            if (dist < threshHold && dist < minDist) {
                minDist = dist;
                minId = i;
            }
        }

        return minId;
    }

    public Point constrainMouseToScreen(BasicDisplay bd) {
        Point p = new Point(bd.getMouseX(), bd.getMouseY());
        if (!constrainToScreen) return p;

        if (p.x < 0) p.x = 0;
        if (p.y < 0) p.y = 0;
        if (p.x > bd.getWidth()) p.x = bd.getWidth();
        if (p.y > bd.getHeight()) p.y = bd.getHeight();
        return p;
    }

    public void draw(BasicDisplay bd) {
        for (int i = 0; i < anchors.size(); i++) {
            drawAnchor(bd, i);
        }
    }

    public void drawAnchor(BasicDisplay bd, int index) {
        if (anchorDrawDelegate == null) {
            bd.drawFilledCircle(anchors.get(index), anchorRadius);
        } else {
            anchorDrawDelegate.draw(bd,
                    anchors.get(index),
                    anchorRadius,
                    index == mouseOverId,
                    (index == grabbedId) && grabActive);
        }
    }

}
