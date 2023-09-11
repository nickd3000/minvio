package com.physmo.minvio.utils;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.Point;

public interface AnchorDrawDelegate {
    void draw(DrawingContext dc, Point point, double radius, boolean mouseOver, boolean grabbed);
}
