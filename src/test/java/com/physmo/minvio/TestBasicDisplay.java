package com.physmo.minvio;

import org.junit.Test;

public class TestBasicDisplay {

    @Test
    public void testBasicDisplay() {
        BasicDisplayAwt bd = new BasicDisplayAwt();
        bd.drawFilledRect(10, 10, 10, 10);
        bd.refresh();
        System.out.println("Hello");
    }
}
