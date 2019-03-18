package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

public class SimpleExample {

    public static void main(String ... args) {
        BasicDisplay bd = new BasicDisplayAwt(200,200);

        bd.setTitle("Simple Example");


        while (true)
        {
            bd.cls(Color.lightGray);
            bd.setDrawColor(Color.WHITE);
            bd.drawFilledRect(100-25,100-25,50, 50);
            bd.setDrawColor(Color.BLUE);
            bd.drawCircle(100,100,70);

            bd.drawText("X:"+bd.mouseX()+" Y:"+bd.mouseY(),10,190);
            bd.refresh(30);
        }
    }



}
