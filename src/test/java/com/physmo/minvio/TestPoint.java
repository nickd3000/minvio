package com.physmo.minvio;

import org.junit.Assert;
import org.junit.Test;

public class TestPoint {

    static double delta = 0.01;

    @Test
    public void testConstructors() {
        Point p1 = new Point();
        Point p2 = new Point(1.23,3.21);
        Point p3 = new Point(p2);

        Assert.assertEquals(p1.x, 0, delta);
        Assert.assertEquals(p1.y, 0, delta);

        Assert.assertEquals(p2.x, 1.23, delta);
        Assert.assertEquals(p2.y, 3.21, delta);

        Assert.assertEquals(p3.x, 1.23, delta);
        Assert.assertEquals(p3.y, 3.21, delta);
    }

    @Test
    public void testAdd() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);

        p1.add(p2);

        Assert.assertEquals(p1.x, 4, delta);
        Assert.assertEquals(p1.y, 6, delta);
    }

    @Test
    public void testAddCoord() {
        Point p1 = new Point(1, 2);

        p1.add(3, 4);

        Assert.assertEquals(p1.x, 4, delta);
        Assert.assertEquals(p1.y, 6, delta);
    }

    @Test
    public void testDistance() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        double distance = p1.distance(p2);
        Assert.assertEquals(distance, 2.828, delta);
    }

    // add
    // multiply
    // to string


}
