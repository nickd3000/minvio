package com.physmo.reference.experiments.fractaltile;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TileManager {
    Map<Integer, Tile> tiles = new HashMap<>();


    public Tile getTile(int zoom, int x, int y) {
        Integer encodedKey = encode(zoom, x, y);
        return tiles.computeIfAbsent(encodedKey, integer -> initTile(zoom, x, y));
    }

    public Tile initTile(int zoom, int x, int y) {
        double scale = Math.pow(2, 1 + zoom);

        double logicalWidth = 2 / scale;
        double logicalHeight = 2 / scale;

        double startX = x * logicalWidth;
        double startY = y * logicalHeight;

        Tile newTile = new Tile(zoom, x, y);
        renderImage(newTile.bufferedImage, zoom, scale, (int) startX, (int) startY);


        return newTile;
    }

    // Encode 3 input values into one integer.
    public Integer encode(int zoom, int x, int y) {
        // Use the layout: [zoom: 8 bits][x: 12 bits][y: 12 bits]
        return ((zoom & 0xFF) << 24) | ((x & 0xFFF) << 12) | (y & 0xFFF);
    }

    public int[] decode(Integer encoded) {
        int zoom = (encoded >> 24) & 0xFF;
        int x = (encoded >> 12) & 0xFFF;
        int y = encoded & 0xFFF;
        return new int[]{zoom, x, y};
    }


    public void renderImage(BufferedImage image, int zoom, double scale, double xStart, double yStart) {
        //Graphics graphics = image.getGraphics();


        double z = (1.0 / Tile.tileHeight) / (scale);

        for (int y = 0; y < Tile.tileHeight; y++) {
            for (int x = 0; x < Tile.tileWidth; x++) {
                double xx = xStart + x * z;
                double yy = yStart + y * z;
                int c = (int) ((functionMandelbrot(xx, yy)) % 255) & 0xff;

                //graphics.setColor(new java.awt.Color(c, c, c));
                //graphics.drawRect(x,y,2,2);
                image.setRGB(x, y, c << 16 | c << 8 | c);
            }
        }
    }

    private int functionMandelbrot(double x, double y) {
        int MAX_ITERATIONS = 1600;
        double xx = 0.0;
        double yy = 0.0;
        int iter = 0;
        while (xx * xx + yy * yy <= 4.0 && iter < MAX_ITERATIONS) {
            double temp = xx * xx - yy * yy + x;
            yy = 2.0 * xx * yy + y;
            xx = temp;
            iter++;
        }
        return iter;
    }


}
