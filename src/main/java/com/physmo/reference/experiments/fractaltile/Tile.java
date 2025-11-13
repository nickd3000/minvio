package com.physmo.reference.experiments.fractaltile;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
    public static int tileWidth = 256;
    public static int tileHeight = tileWidth;
    public TileState tileState = TileState.UNINITIALIZED;
    BufferedImage bufferedImage;
    int zoom;
    int x, y;

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
