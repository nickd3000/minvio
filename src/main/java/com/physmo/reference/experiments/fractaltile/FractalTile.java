package com.physmo.reference.experiments.fractaltile;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;

import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;

public class FractalTile extends MinvioApp {

    Renderer renderer = new Renderer();
    TileManager tileManager = new TileManager();

    double scrollX, scrollY;
    int mouseXPrev, mouseYPrev;
    boolean leftButtonHeld;
    double zoomLevel = 0;

    public static void main(String[] args) {
        MinvioApp app = new FractalTile();
        app.start(400, 400, "FractalTile", 30);
    }

    @Override
    public void init(BasicDisplay bd) {

//        Renderer renderer = new Renderer();
//        renderer.render(parentTile);

    }

    @Override
    public void update(BasicDisplay bd, double delta) {

        if (getBasicDisplay().getMouseButtonLeft()) {
            if (!leftButtonHeld) leftButtonHeld = true;
        } else {
            if (leftButtonHeld) leftButtonHeld = false;
        }

        if (leftButtonHeld) {
            int dx = getMouseX() - mouseXPrev;
            int dy = getMouseY() - mouseYPrev;
            double z = Math.pow(2, 1 + zoomLevel);
            scrollX += dx / z;
            scrollY += dy / z;
        }


        mouseXPrev = getMouseX();
        mouseYPrev = getMouseY();


        int[] keyStates = getBasicDisplay().getKeyState();

        if (keyStates[VK_1] != 0) {
            zoomLevel += 0.01;
        }
        if (keyStates[VK_2] != 0) {
            zoomLevel -= 0.01;
        }
    }

    @Override
    public void draw(double delta) {
//        getDrawingContext()
//        drawImage(tileManager.getTile(0,0,0).bufferedImage,0,0);
        renderer.render(tileManager, getDrawingContext(), zoomLevel, 400, 400, scrollX, scrollY);
    }

}
