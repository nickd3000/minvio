package com.physmo.minvio;

import org.junit.Test;

import java.awt.*;

public class TestBasicDisplay {

    @Test
    public void testBasicDisplay() {
        BasicDisplayAwt bd = new BasicDisplayAwt(100,100);

        bd.cls(new Color(0,0,0));
        Color expectingBlack = bd.getColorAtPoint(1,1);
        bd.setDrawColor(new Color(0xff,0xff,0xff));
        bd.drawFilledRect(0, 0, 100, 100);
        Color expectingWhite = bd.getColorAtPoint(1,1);

        org.junit.Assert.assertEquals(expectingBlack.getRed(),0);
        org.junit.Assert.assertEquals(expectingBlack.getGreen(),0);
        org.junit.Assert.assertEquals(expectingBlack.getBlue(),0);
        org.junit.Assert.assertEquals(expectingWhite.getRed(),0xff);
        org.junit.Assert.assertEquals(expectingWhite.getGreen(),0xff);
        org.junit.Assert.assertEquals(expectingWhite.getBlue(),0xff);

    }
}
