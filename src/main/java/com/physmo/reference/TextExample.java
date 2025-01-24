package com.physmo.reference;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;

import java.awt.Color;
import java.awt.Font;

class TextExample {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 200);
        DrawingContext dc = bd.getDrawingContext();

        bd.setTitle("Text Example");

        Font font10 = new Font("Verdana", Font.PLAIN, 10);
        Font font20 = new Font("Verdana", Font.PLAIN, 20);

        while (true) {
            dc.cls(Color.lightGray);
            dc.setDrawColor(Color.BLACK);

            // We can make use of user-defined fonts:
            dc.setFont(font10);
            dc.drawText("User defined Verdana 10", 20, 40);
            dc.setFont(font20);
            dc.drawText("User defined Verdana 20", 20, 60);

            dc.setDrawColor(Color.BLUE);

            // We can also make use of simpler built in fonts by simmply calling setFont with an
            // integer size.
            dc.setFont(15);
            dc.drawText("Built-in font 15", 20, 90);

            dc.setFont(25);
            dc.drawText("Built-in font 25", 20, 120);

            // Measure some text and draw a yellow box around it using the measurements.
            dc.setFont(30);
            dc.drawText("getTextSize", 20, 160);
            int[] textSize = dc.getTextSize("getTextSize");
            dc.setDrawColor(Color.yellow);
            int textSizeWidth = textSize[BasicDisplay.TEXT_SIZE_WIDTH];
            int textSizeAscent = textSize[BasicDisplay.TEXT_SIZE_ASCENT];
            int textSizeDescent = textSize[BasicDisplay.TEXT_SIZE_DESCENT];

            dc.drawRect(
                    20, 160 + textSizeDescent,
                    20 + textSizeWidth, 160 - textSizeAscent);

            bd.repaint(30);
        }
    }

}
