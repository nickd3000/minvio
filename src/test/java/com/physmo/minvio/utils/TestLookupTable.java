package com.physmo.minvio.utils;

import org.junit.Assert;
import org.junit.Test;

public class TestLookupTable {

    @Test
    public void t1() {
        LookupTable lookupTable = new LookupTable(0.0, 11.0, 1000, operand -> Math.sin(operand));

        Assert.assertEquals(Math.sin(0), lookupTable.getValue(0), 0.01);
        Assert.assertEquals(Math.sin(0.5), lookupTable.getValue(0.5), 0.01);
        Assert.assertEquals(Math.sin(10), lookupTable.getValue(10), 0.01);
    }

}
