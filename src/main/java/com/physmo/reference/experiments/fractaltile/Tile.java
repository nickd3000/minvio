package com.physmo.reference.experiments.fractaltile;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

    BufferedImage bufferedImage;

    int zoom;

    double x, y;


    public static int tileWidth = 128;
    public static int tileHeight = 128;

    public boolean locked = false;
    public int renderedLevel = 0;

    public Tile(int zoom, int x, int y) {
        this.zoom = zoom;
        this.x = x;
        this.y = y;

        bufferedImage = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_RGB);

        renderInfo();
    }

    public void renderInfo() {
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.YELLOW);
        graphics.drawString("z:" + zoom + "  x:" + x + "  y:" + y, 20, 20);
        graphics.drawRect(0, 0, tileWidth - 1, tileHeight - 1);
        graphics.dispose();
    }
}
