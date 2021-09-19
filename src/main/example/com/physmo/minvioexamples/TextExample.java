package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

class TextExample {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 200);

        bd.setTitle("Text Example");

        Font font10 = new Font("Verdana", Font.PLAIN, 10);
        Font font20 = new Font("Verdana", Font.PLAIN, 20);

        while (true) {
            bd.cls(Color.lightGray);
            bd.setDrawColor(Color.BLACK);

            // We can make use of user-defined fonts:
            bd.setFont(font10);
            bd.drawText("User defined Verdana 10", 20, 40);
            bd.setFont(font20);
            bd.drawText("User defined Verdana 20", 20, 60);

            bd.setDrawColor(Color.BLUE);

            // We can also make use of simpler built in fonts by simmply calling setFont with an
            // integer size.
            bd.setFont(15);
            bd.drawText("Built-in font 15", 20, 90);

            bd.setFont(25);
            bd.drawText("Built-in font 25", 20, 120);

            // Measure some text and draw a yellow box around it using the measurements.
            bd.setFont(30);
            bd.drawText("getTextSize", 20, 160);
            int[] textSize = bd.getTextSize("getTextSize");
            bd.setDrawColor(Color.yellow);
            int textSizeWidth = textSize[BasicDisplay.TEXT_SIZE_WIDTH];
            int textSizeAscent = textSize[BasicDisplay.TEXT_SIZE_ASCENT];
            int textSizeDescent = textSize[BasicDisplay.TEXT_SIZE_DESCENT];

            bd.drawRect(
                    20, 160 + textSizeDescent,
                    20 + textSizeWidth, 160 - textSizeAscent);

            bd.repaint(30);
        }
    }

}
