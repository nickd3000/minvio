package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

class DistinctColorExample {
    public static void main(String ... args) {

        int width = 400;
        int height = 400;

        BasicDisplay bd = new BasicDisplayAwt(width, height);

        bd.setTitle("Distinct Color Example");

        int numRows = 5;
        int space = width/numRows;
        int halfSpace = space/2;

        for (int y=0;y<numRows;y++) {
            for (int x=0;x<numRows;x++) {
                bd.setDrawColor(bd.getDistinctColor(x+(y*numRows), 0.5f));
                bd.drawFilledCircle(halfSpace+x*space,halfSpace+y*space,halfSpace);
            }
        }

        while (true)
        {

            bd.refresh(30);
        }
    }
}
