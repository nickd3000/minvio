package com.physmo.minvio;

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
}
