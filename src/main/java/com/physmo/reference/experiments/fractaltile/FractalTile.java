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

    double scrollX = 0, scrollY = 0;  // Start at center of logical space (not 300,300)
    int mouseXPrev, mouseYPrev;
    boolean leftButtonHeld;
    double zoomLevel = 0;

    public static void main(String[] args) {
        MinvioApp app = new FractalTile();
        app.start(600, 600, "FractalTile", 30);
    }


    @Override
    public void update(BasicDisplay bd, double delta) {

        // Calculate pixels per world unit at current zoom
        int iZoom = (int) zoomLevel;
        double fractionalZoom = zoomLevel - iZoom;
        double scaledTileSize = Math.pow(2.0, fractionalZoom) * Tile.tileWidth;
        double worldUnitsPerTile = 1.0 / Math.pow(2.0, iZoom);
        double pixelsPerWorldUnit = scaledTileSize / worldUnitsPerTile;

        // Panning logic - dragging mouse changes center location
        if (getBasicDisplay().getMouseButtonLeft()) {
            if (!leftButtonHeld) leftButtonHeld = true;
        } else {
            if (leftButtonHeld) leftButtonHeld = false;
        }

        if (leftButtonHeld) {
            double dx = (getMouseX() - mouseXPrev) / pixelsPerWorldUnit;
            double dy = (getMouseY() - mouseYPrev) / pixelsPerWorldUnit;
            // Move logical center opposite direction to mouse drag (standard for pan)
            scrollX -= dx;
            scrollY -= dy;
        }
        mouseXPrev = getMouseX();
        mouseYPrev = getMouseY();

        int[] keyStates = getBasicDisplay().getKeyState();

        if (keyStates[VK_1] != 0 || keyStates[VK_2] != 0) {
            if (keyStates[VK_2] != 0) zoomLevel += 0.01; // Zoom in
            if (keyStates[VK_1] != 0) zoomLevel -= 0.01; // Zoom out
        }

        double arrowMoveSpeed = 2.0 / pixelsPerWorldUnit;

        if (keyStates[VK_LEFT] != 0) {
            scrollX -= arrowMoveSpeed;
        }
        if (keyStates[VK_RIGHT] != 0) {
            scrollX += arrowMoveSpeed;
        }
        if (keyStates[VK_UP] != 0) {
            scrollY -= arrowMoveSpeed;
        }
        if (keyStates[VK_DOWN] != 0) {
            scrollY += arrowMoveSpeed;
        }

        tileManager.tick();
    }

    @Override
    public void draw(double delta) {
        cls();

        TileWindow activeWindow = renderer.render(tileManager, getDrawingContext(), zoomLevel, getWidth(), getHeight(), scrollX, scrollY, true);
        TileWindow nextWindow = renderer.render(tileManager, getDrawingContext(), zoomLevel + 1, getWidth(), getHeight(), scrollX, scrollY, false);

        tileManager.setActiveWindow(activeWindow);
        tileManager.setNextWindow(nextWindow);

        setDrawColor(Color.ORANGE);
        for (int i = 0; i < tileManager.getPendingTaskCount(); i++) {
            drawFilledCircle(15 + (i * 10), 15, 5);
        }

        drawText("Z:" + (int) zoomLevel, 20, 40);


        int tileCount = tileManager.tiles.size();
        int mem = (tileCount * Tile.tileWidth * Tile.tileWidth) / 1024;
        drawText("Tiles:" + tileCount, 20, 60);
        drawText("Mem k:" + mem, 20, 80);

    }

}