package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

// TODO: add draw point if we don't already have one.
class SimpleExample2 {

    public static void main(String ... args) {
        BasicDisplay bd = new BasicDisplayAwt(200,200);

        bd.setTitle("Simple Example");

        double x=0,y=0,x2=0,y2=0,a=0,r=40;

        bd.cls(Color.lightGray);

        while (true)
        {

            a+=0.02;

            bd.setDrawColor(Color.WHITE);
            x=(Math.sin(a)*r);
            y=(Math.cos(a*3)*r);
            x2=(Math.sin(a*6)*20);
            y2=(Math.cos(a*3)*20);

            bd.drawFilledRect(100+(int)(x+x2),100+(int)(y+y2),2, 2);

            bd.refresh(30);
        }
    }

}
