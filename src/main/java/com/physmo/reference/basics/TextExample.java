package com.physmo.reference.basics;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.awt.Font;

/**
 * Demonstrates fonts, text drawing, and text measurement.
 */
class TextExample extends MinvioApp {

    private final Font font10 = new Font("Verdana", Font.PLAIN, 10);
    private final Font font20 = new Font("Verdana", Font.PLAIN, 20);

    public static void main(String... args) {
        MinvioApp app = new TextExample();
        app.start(400, 200, "Text Example", 30);
    }

    @Override
    public void draw(double delta) {
        cls(Color.LIGHT_GRAY);
        setDrawColor(Color.BLACK);

        setFont(font10);
        drawText("User defined Verdana 10", 20, 40);
        setFont(font20);
        drawText("User defined Verdana 20", 20, 60);

        setDrawColor(Color.BLUE);
        setFont(15);
        drawText("Built-in font 15", 20, 90);

        setFont(25);
        drawText("Built-in font 25", 20, 120);

        drawMeasuredTextBox("getTextSize", 20, 160);
    }

    private void drawMeasuredTextBox(String text, int x, int baselineY) {
        setFont(30);
        setDrawColor(Color.BLUE);
        drawText(text, x, baselineY);

        int[] textSize = getTextSize(text);
        int width = textSize[BasicDisplay.TEXT_SIZE_WIDTH];
        int ascent = textSize[BasicDisplay.TEXT_SIZE_ASCENT];
        int descent = textSize[BasicDisplay.TEXT_SIZE_DESCENT];

        setDrawColor(Color.YELLOW);
        drawRect(x, baselineY - ascent, width, ascent + descent);
    }
}
