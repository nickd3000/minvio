package com.physmo.minvio.utils;


import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The AnchorManager class manages a collection of anchor points and provides
 * functionality for rendering, updating, and interacting with these anchors.
 * It supports custom drawing delegates for the anchors, mouse interactions,
 * and optional screen boundary constraints.
 */
public class AnchorManager {

    final List<Point> anchors;
    final double anchorRadius;
    double hitBoxMultiplier = 3.0;
    boolean prevMouseButtonState = false;
    boolean grabActive = false;
    int grabbedId = 0;
    int mouseOverId = -1;
    AnchorDrawDelegate anchorDrawDelegate = null;
    boolean constrainToScreen = true;

    /**
     * Creates an empty anchor collection with the supplied visual radius.
     *
     * <p>The radius is not validated and also contributes to the default hit
     * area.</p>
     *
     * @param anchorRadius radius supplied to the drawing delegate
     */
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

    /**
     * Replaces the anchor drawing callback.
     *
     * @param anchorDrawDelegate callback, or {@code null} to use the fallback
     *                           filled-circle drawing
     */
    public void setAnchorDrawDelegate(AnchorDrawDelegate anchorDrawDelegate) {
        this.anchorDrawDelegate = anchorDrawDelegate;
    }

    /** @return whether dragged anchors are constrained to display bounds */
    public boolean getConstrainToScreen() {
        return constrainToScreen;
    }

    /**
     * Enables or disables clamping dragged anchors to display bounds.
     *
     * @param constrainToScreen new constraint setting
     */
    public void setConstrainToScreen(boolean constrainToScreen) {
        this.constrainToScreen = constrainToScreen;
    }

    /**
     * Appends a mutable anchor point.
     *
     * @param x initial x-coordinate
     * @param y initial y-coordinate
     */
    public void add(double x, double y) {
        anchors.add(new Point(x, y));
    }

    /**
     * Returns the live mutable anchor list.
     *
     * <p>Changes to the list or contained points immediately affect this
     * manager.</p>
     *
     * @return internal mutable anchor list
     */
    public List<Point> getAnchors() {
        return anchors;
    }

    /** @return multiplier applied to the anchor radius for hit testing */
    public double getHitBoxMultiplier() {
        return hitBoxMultiplier;
    }

    /**
     * Sets the multiplier applied to the anchor radius for hit testing.
     *
     * @param hitBoxMultiplier multiplier; not validated
     */
    public void setHitBoxMultiplier(double hitBoxMultiplier) {
        this.hitBoxMultiplier = hitBoxMultiplier;
    }

    /**
     * Updates hover and drag state from the display's current mouse state.
     *
     * <p>A drag begins on a left-button transition from released to pressed and
     * ends on the reverse transition.</p>
     *
     * @param bd display providing mouse state and dimensions
     */
    public void update(BasicDisplay bd) {
        boolean mouseButtonState = bd.getMouseButtonLeft();

        if (mouseButtonState && !prevMouseButtonState) {
            int id = findCloseAnchor(bd.getMouseX(), bd.getMouseY(), anchorRadius * hitBoxMultiplier);
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

        mouseOverId = findCloseAnchor(bd.getMouseX(), bd.getMouseY(), anchorRadius * hitBoxMultiplier);

        prevMouseButtonState = mouseButtonState;
    }

    /**
     * Finds the closest anchor strictly nearer than the supplied threshold.
     *
     * @param x target x-coordinate
     * @param y target y-coordinate
     * @param threshHold exclusive distance threshold
     * @return anchor index, or {@code -1} when no anchor qualifies
     */
    public int findCloseAnchor(double x, double y, double threshHold) {
        int anchorId;
        Point targetPoint = new Point(x, y);

        anchorId = BasicUtils.findClosestPointInList(anchors, targetPoint, threshHold);

        return anchorId;
    }

    /**
     * Returns the mouse position, optionally clamped to inclusive coordinates
     * from zero through the display width and height.
     *
     * @param bd display providing mouse state and dimensions
     * @return new mutable point containing the resulting position
     */
    public Point constrainMouseToScreen(BasicDisplay bd) {
        Point p = new Point(bd.getMouseX(), bd.getMouseY());
        if (!constrainToScreen) return p;

        if (p.x < 0) p.x = 0;
        if (p.y < 0) p.y = 0;
        if (p.x > bd.getWidth()) p.x = bd.getWidth();
        if (p.y > bd.getHeight()) p.y = bd.getHeight();
        return p;
    }

    /**
     * Draws all anchors in list order.
     *
     * @param dc drawing context
     */
    public void draw(DrawingContext dc) {
        for (int i = 0; i < anchors.size(); i++) {
            drawAnchor(dc, i);
        }
    }

    /**
     * Draws one anchor by index.
     *
     * @param dc drawing context
     * @param index anchor index
     * @throws IndexOutOfBoundsException if the index is outside the anchor list
     */
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
