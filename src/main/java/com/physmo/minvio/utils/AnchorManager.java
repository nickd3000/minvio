package com.physmo.minvio.utils;


import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A basic system that provides a set of draggable points.
 */
public class AnchorManager {

    final List<Point> anchors;
    final double anchorRadius;
    boolean prevMouseButtonState = false;
    boolean grabActive = false;
    int grabbedId = 0;
    int mouseOverId = -1;
    AnchorDrawDelegate anchorDrawDelegate = null;
    boolean constrainToScreen = true;

    public AnchorManager(double anchorRadius) {
        this.anchorRadius = anchorRadius;
        anchors = new ArrayList<>();

        setAnchorDrawDelegate((dc, point, radius, mouseOver, grabbed) -> {

            if (grabbed) {
                dc.setDrawColor(new Color(255, 228, 94));
                dc.drawFilledCircle(point, radius);
                dc.setDrawColor(new Color(1, 1, 1));
                dc.drawCircle(point, radius);
            } else if (mouseOver) {
                dc.setDrawColor(new Color(1, 1, 1));
                dc.drawFilledCircle(point, radius);
                dc.setDrawColor(new Color(245, 5, 5));
                dc.drawCircle(point, radius);
                dc.drawCircle(point, radius + 0.5);
            } else {
                dc.setDrawColor(new Color(1, 1, 1));
                dc.drawFilledCircle(point, radius);
            }

        });
    }

    public void setAnchorDrawDelegate(AnchorDrawDelegate anchorDrawDelegate) {
        this.anchorDrawDelegate = anchorDrawDelegate;
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

    public List<Point> getAnchors() {
        return anchors;
    }

    public void update(BasicDisplay bd) {
        boolean mouseButtonState = bd.getMouseButtonLeft();

        if (mouseButtonState && !prevMouseButtonState) {
            int id = findCloseAnchor(bd.getMouseX(), bd.getMouseY(), anchorRadius);
            if (id != -1) {
                grabbedId = id;
                grabActive = true;
            }
        } else if (!mouseButtonState && prevMouseButtonState) {
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
        int anchorId;
        Point targetPoint = new Point(x, y);

        anchorId = BasicUtils.findClosestPointInList(anchors, targetPoint, threshHold);

        return anchorId;
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

    public void draw(DrawingContext dc) {
        for (int i = 0; i < anchors.size(); i++) {
            drawAnchor(dc, i);
        }
    }

    public void drawAnchor(DrawingContext dc, int index) {
        if (anchorDrawDelegate == null) {

            dc.drawFilledCircle(anchors.get(index), anchorRadius);
        } else {
            anchorDrawDelegate.draw(dc,
                    anchors.get(index),
                    anchorRadius,
                    index == mouseOverId,
                    (index == grabbedId) && grabActive);
        }
    }

}
