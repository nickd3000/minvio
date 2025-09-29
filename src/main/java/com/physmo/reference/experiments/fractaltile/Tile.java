package com.physmo.reference.experiments.fractaltile;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

    BufferedImage bufferedImage;

    int zoomLevel;

    double x, y;

    double span;

    Tile parentTile;

    public static int tileWidth = 128;
    public static int tileHeight = 128;

    Tile[] subTiles = new Tile[]{null, null, null, null};

    boolean dirty;

    public Tile(int zoom, int x, int y) {
        bufferedImage = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.YELLOW);
        graphics.drawString("z:" + zoom + "  x:" + x + "  y:" + y, 20, 20);
        graphics.drawRect(0, 0, tileWidth - 1, tileHeight - 1);
    }
}
