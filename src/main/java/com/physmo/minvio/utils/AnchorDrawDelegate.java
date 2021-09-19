package com.physmo.minvio.utils;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.Point;

public interface AnchorDrawDelegate {
    void draw(BasicDisplay bd, Point point, double radius, boolean mouseOver, boolean grabbed);
}
