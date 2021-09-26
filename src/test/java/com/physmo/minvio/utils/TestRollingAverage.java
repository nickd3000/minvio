package com.physmo.minvio.utils;

import org.junit.Assert;
import org.junit.Test;

public class TestRollingAverage {

    @Test
    public void testAverageValueBasic() {
        RollingAverage rollingAverage = new RollingAverage(10);

        for (int i = 0; i < 10; i++) {
            rollingAverage.add(100.0);
        }

        Assert.assertEquals(100.0, rollingAverage.getAverage(), 0.01);
        Assert.assertEquals(1000.0, rollingAverage.getSum(), 0.01);
    }

    @Test
    public void testAverageValueWithTwoSetsOfNumbers() {
        RollingAverage rollingAverage = new RollingAverage(10);

        for (int i = 0; i < 10; i++) {
            rollingAverage.add(200.0);
        }
        for (int i = 0; i < 10; i++) {
            rollingAverage.add(100.0);
        }

        Assert.assertEquals(100.0, rollingAverage.getAverage(), 0.01);
        Assert.assertEquals(1000.0, rollingAverage.getSum(), 0.01);
    }

}
