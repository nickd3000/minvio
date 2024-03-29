package com.physmo.minvio;

import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

public class TestBasicDisplay {

    @Test
    public void testBasicDisplay() {
        BasicDisplayAwt bd = new BasicDisplayAwt(100, 100);
        DrawingContext dc = bd.getDrawingContext();
        dc.cls(new Color(0, 0, 0));
        Color expectingBlack = dc.getColorAtPoint(1, 1);
        dc.setDrawColor(new Color(0xff, 0xff, 0xff));
        dc.drawFilledRect(0, 0, 100, 100);
        Color expectingWhite = dc.getColorAtPoint(1, 1);

        assertEquals(expectingBlack.getRed(), 0);
        assertEquals(expectingBlack.getGreen(), 0);
        assertEquals(expectingBlack.getBlue(), 0);
        assertEquals(expectingWhite.getRed(), 0xff);
        assertEquals(expectingWhite.getGreen(), 0xff);
        assertEquals(expectingWhite.getBlue(), 0xff);

        bd.close();
    }

    @Test
    public void testDrawFiledPolygon() {
        BasicDisplayAwt bd = new BasicDisplayAwt(200, 200);
        DrawingContext dc = bd.getDrawingContext();
        int[] xPoints = {100, 200, 0};
        int[] yPoints = {0, 200, 200};
        int numPoints = 3;

        dc.cls(new Color(0, 0, 0));
        Color expectingBlack = dc.getColorAtPoint(1, 1);
        dc.setDrawColor(new Color(0xff, 0xff, 0xff));
        dc.drawFilledPolygon(xPoints, yPoints, numPoints);
        bd.repaint();
        Color expectingWhite = dc.getColorAtPoint(1, 1);

        bd.close();
    }

    @Test
    public void testClamp() {
        Assert.assertEquals(0.0, Utils.clamp(0.0, 1.0, 0.0), 0.001);
        Assert.assertEquals(1.0, Utils.clamp(0.0, 1.0, 1.0), 0.001);
        Assert.assertEquals(0.5, Utils.clamp(0.0, 1.0, 0.5), 0.001);
        Assert.assertEquals(0.0, Utils.clamp(0.0, 1.0, -1.0), 0.001);
        Assert.assertEquals(1.0, Utils.clamp(0.0, 1.0, 5.0), 0.001);


    }
}
