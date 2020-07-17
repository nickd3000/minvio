package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

class RefreshExample {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);

        bd.setTitle("Refresh Example");
        bd.setFont(10);

        double previousTime = (double)System.nanoTime()/1_000_000_000.0;
        double scroll=0;

        while (true) {

            double newTime = (double)System.nanoTime()/1_000_000_000.0;
            double deltaTime = newTime-previousTime;
            previousTime = newTime;

            scroll += deltaTime;

            bd.cls(Color.lightGray);

            int rows = 5;
            for (int y=0;y<rows;y++) {
                for (int x=0;x<rows;x++) {
                    int span = bd.getWidth()/rows;

                    bd.drawFilledCircle(x*span+((scroll*60)%span),y*span, 5);
                }
            }

            bd.refresh(30);
        }
    }

}
