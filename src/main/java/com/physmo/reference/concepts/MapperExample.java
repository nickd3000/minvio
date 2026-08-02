package com.physmo.reference.concepts;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.BasicUtils;

import java.awt.Color;

class MapperExample extends MinvioApp {

    private static final Color backgroundColour = new Color(20, 63, 88);
    private static final Color foregroundColour = new Color(200, 218, 105);

    public static void main(String... args) {
        MinvioApp app = new MapperExample();
        app.start(400, 400, "Mapper Example", 60);
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
