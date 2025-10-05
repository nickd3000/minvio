package com.physmo.reference.experiments.fractaltile;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

public class FractalTile extends MinvioApp {

    Renderer renderer = new Renderer();
    TileManager tileManager = new TileManager();

    double scrollX = 300, scrollY = 300;
    int mouseXPrev, mouseYPrev;
    boolean leftButtonHeld;
    double zoomLevel = 0;

    public static void main(String[] args) {
        MinvioApp app = new FractalTile();
        app.start(600, 600, "FractalTile", 30);
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
            double z = Math.pow(2, zoomLevel);
            scrollX += dx;// / z;
            scrollY += dy;// / z;
        }


        mouseXPrev = getMouseX();
        mouseYPrev = getMouseY();


        int[] keyStates = getBasicDisplay().getKeyState();

//        if (keyStates[VK_1] != 0) {
//            zoomLevel += 0.01;
//        }
//        if (keyStates[VK_2] != 0) {
//            zoomLevel -= 0.01;
//        }

        if (keyStates[VK_1] != 0) {  // Zoom in
            double oldZoom = Math.pow(2, zoomLevel);
            zoomLevel += 0.01;
            double newZoom = Math.pow(2, zoomLevel);

            // Get the center point of the window
            double centerX = getWidth() / 2.0;
            double centerY = getHeight() / 2.0;

            // Calculate how much the center point moves during zoom
            double dx = centerX - (centerX * (newZoom / oldZoom));
            double dy = centerY - (centerY * (newZoom / oldZoom));

            // Adjust scroll position based on zoom center
            scrollX = scrollX * (newZoom / oldZoom) + dx;
            scrollY = scrollY * (newZoom / oldZoom) + dy;
        }

        if (keyStates[VK_2] != 0) {  // Zoom out
            double oldZoom = Math.pow(2, zoomLevel);
            zoomLevel -= 0.01;
            double newZoom = Math.pow(2, zoomLevel);

            // Get the center point of the window
            double centerX = getWidth() / 2.0;
            double centerY = getHeight() / 2.0;

            // Calculate how much the center point moves during zoom
            double dx = centerX - (centerX * (newZoom / oldZoom));
            double dy = centerY - (centerY * (newZoom / oldZoom));

            // Adjust scroll position based on zoom center
            scrollX = scrollX * (newZoom / oldZoom) + dx;
            scrollY = scrollY * (newZoom / oldZoom) + dy;
        }

        double arrowMoveSpeed = 2.0;
        double scale = Math.pow(2, zoomLevel);
        if (keyStates[VK_LEFT] != 0) {
            scrollX += arrowMoveSpeed;
        }
        if (keyStates[VK_RIGHT] != 0) {
            scrollX -= arrowMoveSpeed;
        }
        if (keyStates[VK_UP] != 0) {
            scrollY += arrowMoveSpeed;
        }
        if (keyStates[VK_DOWN] != 0) {
            scrollY -= arrowMoveSpeed;
        }

    }

    @Override
    public void draw(double delta) {
        cls();
        //renderer.render(tileManager, getDrawingContext(), zoomLevel + 0.1, getWidth(), getHeight(), scrollX, scrollY, false);
        ActiveWindow activeWindow = renderer.render(tileManager, getDrawingContext(), zoomLevel, getWidth(), getHeight(), scrollX, scrollY, true);

        tileManager.setActiveWindow(activeWindow);

        setDrawColor(Color.ORANGE);
        for (int i = 0; i < tileManager.getPendingTaskCount(); i++) {
            drawFilledCircle(15 + (i * 10), 15, 5);
        }
    }

}