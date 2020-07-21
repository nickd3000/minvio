package com.physmo.minvio;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TestBasicDisplay {

    @Test
    public void testBasicDisplay() {
        BasicDisplayAwt bd = new BasicDisplayAwt(100,100);

        bd.cls(new Color(0,0,0));
        Color expectingBlack = bd.getColorAtPoint(1,1);
        bd.setDrawColor(new Color(0xff,0xff,0xff));
        bd.drawFilledRect(0, 0, 100, 100);
        Color expectingWhite = bd.getColorAtPoint(1,1);

        assertEquals(expectingBlack.getRed(),0);
        assertEquals(expectingBlack.getGreen(),0);
        assertEquals(expectingBlack.getBlue(),0);
        assertEquals(expectingWhite.getRed(),0xff);
        assertEquals(expectingWhite.getGreen(),0xff);
        assertEquals(expectingWhite.getBlue(),0xff);

    }

    @Test
    public void testDrawFiledPolygon() {
        BasicDisplayAwt bd = new BasicDisplayAwt(200,200);

        int[] xPoints = {100, 200, 0};
        int[] yPoints = {0, 200, 200};
        int numPoints = 3;

        bd.cls(new Color(0,0,0));
        Color expectingBlack = bd.getColorAtPoint(1,1);
        bd.setDrawColor(new Color(0xff,0xff,0xff));
        bd.drawFilledPolygon(xPoints,yPoints,numPoints);
        bd.repaint();
        Color expectingWhite = bd.getColorAtPoint(1,1);


    }
}
