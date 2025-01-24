package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.BasicUtils;

import java.awt.Color;

class MapperTest extends MinvioApp {

    private static final Color backgroundColour = new Color(20, 63, 88);
    private static final Color foregroundColour = new Color(200, 218, 105);

    public static void main(String... args) {
        MinvioApp app = new MapperTest();
        app.start(400, 400, "Mapper Test", 60);
    }

    @Override
    public void draw(double delta) {
        double x = getMouseX();
        double y = getMouseY();

        double mappedX = BasicUtils.mapper(x, 0, 400, 100, 300);
        double mappedY = BasicUtils.mapper(y, 0, 400, 100, 300);

        cls(backgroundColour);
        setDrawColor(foregroundColour);
        drawRect(0, 0, 400 - 1, 400 - 1);
        drawRect(100, 100, 200, 200);
        drawLine(200, 200, mappedX, mappedY, 2);
        drawCircle(mappedX, mappedY, 10);

    }
}
