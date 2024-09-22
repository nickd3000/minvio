package com.physmo.minvio.utils;

import com.physmo.minvio.types.Point;
import org.junit.Assert;
import org.junit.Test;

public class TestBasicUtils {

    @Test
    public void testCreateRandomPointInCircle() {
        Point midPoint = new Point(100, 100);
        double radius = 100;
        int numTests = 1000;
        int validCount = 0;

        for (int i = 0; i < 1000; i++) {
            Point p = BasicUtils.createRandomPointInCircle(midPoint.x, midPoint.y, radius);
            double dist = midPoint.distance(p);
            if (dist <= radius) {
                validCount++;
            }
        }

        Assert.assertEquals(numTests, validCount);
    }

    @Test
    public void test() {
    }
}
