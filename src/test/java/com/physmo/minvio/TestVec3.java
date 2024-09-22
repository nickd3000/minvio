package com.physmo.minvio;

import com.physmo.minvio.types.Vec3;
import org.junit.Assert;
import org.junit.Test;

public class TestVec3 {
    @Test
    public void testInstantiation() {

        Vec3 v1 = new Vec3(1, 2, 3);

        Vec3 v2 = new Vec3(v1);

        Assert.assertEquals(v1.x, 1, 0.001);
        Assert.assertEquals(v1.y, 2, 0.001);
        Assert.assertEquals(v1.z, 3, 0.001);

        Assert.assertEquals(v1.x, v2.x, 0.001);
        Assert.assertEquals(v1.y, v2.y, 0.001);
        Assert.assertEquals(v1.z, v2.z, 0.001);

        Assert.assertNotSame(v1, v2);

    }

    @Test
    public void testAdd() {
        Vec3 v1 = new Vec3(1, 2, 3);
        Vec3 v2 = new Vec3(3, 3, 3);

        Vec3 v3 = v1.add(v2);

        Assert.assertEquals(v1.x, 1, 0.001);
        Assert.assertEquals(v1.y, 2, 0.001);
        Assert.assertEquals(v1.z, 3, 0.001);

        Assert.assertEquals(v2.x, 3, 0.001);
        Assert.assertEquals(v2.y, 3, 0.001);
        Assert.assertEquals(v2.z, 3, 0.001);

        Assert.assertEquals(v3.x, 4, 0.001);
        Assert.assertEquals(v3.y, 5, 0.001);
        Assert.assertEquals(v3.z, 6, 0.001);

        // Add in place - modifies object.
        v1.addi(v2);

        Assert.assertEquals(v1.x, 4, 0.001);
        Assert.assertEquals(v1.y, 5, 0.001);
        Assert.assertEquals(v1.z, 6, 0.001);
    }

    @Test
    public void testSet() {
        Vec3 v1 = new Vec3(1, 2, 3);
        v1.set(3, 2, 1);
    }

    @Test
    public void testToString() {
        Vec3 v1 = new Vec3(1, 2, 3);
        String toString = v1.toString();
        Assert.assertEquals("Vec3{x=1.00, y=2.00, z=3.00}", toString);
    }

    @Test
    public void testScale() {
        Vec3 v1 = new Vec3(1, 2, 3);
        v1 = v1.scale(1);
        Assert.assertEquals(v1.x, 1, 0.01);
        Assert.assertEquals(v1.y, 2, 0.01);
        Assert.assertEquals(v1.z, 3, 0.01);
        v1 = v1.scale(2);
        Assert.assertEquals(v1.x, 2, 0.01);
        Assert.assertEquals(v1.y, 4, 0.01);
        Assert.assertEquals(v1.z, 6, 0.01);


    }
}
