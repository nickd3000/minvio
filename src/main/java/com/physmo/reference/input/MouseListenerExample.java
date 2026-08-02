package com.physmo.reference.input;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.gui.support.MouseConnector;

import java.awt.Color;

class MouseListenerExample extends MinvioApp {

    private int lastX;
    private int lastY;
    private int lastButton;
    private String lastEvent = "Move the mouse or click";

    public static void main(String... args) {
        MinvioApp app = new MouseListenerExample();
        app.start(300, 200, "Mouse Listener Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        bd.addMouseConnector(new MouseConnector() {
            @Override
            public void onMouseMoved(int x, int y) {
                lastX = x;
                lastY = y;
                lastEvent = "Moved";
            }

            @Override
            public void onButtonDown(int x, int y, int buttonId) {
                lastX = x;
                lastY = y;
                lastButton = buttonId;
                lastEvent = "Pressed";
            }

            @Override
            public void onButtonUp(int x, int y, int buttonId) {
                lastX = x;
                lastY = y;
                lastButton = buttonId;
                lastEvent = "Released";
            }
        });
    }

    @Override
    public void draw(double delta) {
        cls(Color.DARK_GRAY);
        setDrawColor(Color.WHITE);
        drawText(lastEvent + " at " + lastX + ", " + lastY, 20, 40);
        drawText("Button: " + lastButton, 20, 70);

        setDrawColor(Color.ORANGE);
        drawFilledCircle(lastX, lastY, 8);
    }
}
