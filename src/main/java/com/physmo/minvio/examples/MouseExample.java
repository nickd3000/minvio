package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

// TODO: mouse clicks... is this supported yet?
public class MouseExample {


    public static void main(String ... args) {
        int width = 400;
        int height = 400;
        BasicDisplay bd = new BasicDisplayAwt(width,height);

        bd.setTitle("Mouse Example");

        // Clear the screen to dark gray.
        bd.cls(Color.black);
        bd.setDrawColor(Color.BLUE);

        int prevX = bd.mouseX();
        int prevY = bd.mouseY();

        // Loop forever.
        while (true)
        {
            bd.refresh(30);
            int dst;
            dst = dist(prevX,prevY,bd.mouseX(),bd.mouseY());

            // Draw the point.
            bd.drawFilledCircle(bd.mouseX(),bd.mouseY(), dst/2);

            // Chose a random distinct colour every so often.
            bd.setDrawColor(bd.getDistinctColor((int)(Math.random()*100), 0.7));

            prevX=bd.mouseX();
            prevY=bd.mouseY();

            // Fade
            bd.cls(new Color(0,0,0,10));

        }
    }

    // TODO: add distance functions to utilities
    static int dist(int x1, int y1, int x2, int y2) {
        int dx = x2-x1;
        int dy = y2-y1;
        int dist = (int)Math.sqrt(((dx*dx)+(dy*dy)));
        return dist;
    }
}
