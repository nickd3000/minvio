package com.physmo.reference.experiments.fractaltile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TileManager {

    Color[] palette = new Color[0xff];


    Map<Integer, Tile> tiles = new HashMap<>();
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 14, 1, TimeUnit.MINUTES,
            new PriorityBlockingQueue<>()
    );

    public TileManager() {
        for (int c = 0; c < 0xff; c++) {
            palette[c] = new Color(c, c, c);
        }
    }

    public Tile getTile(int zoom, int x, int y) {
        Integer encodedKey = encode(zoom, x, y);
        return tiles.computeIfAbsent(encodedKey, integer -> initTile(zoom, x, y));
    }

    public Tile initTile(int zoom, int x, int y) {
        double scale = Math.pow(2, zoom);

        double logicalWidth = 1 / scale;
        double logicalHeight = 1 / scale;

        double startX = x * logicalWidth;
        double startY = y * logicalHeight;

        Tile newTile = new Tile(zoom, x, y);


//        executor.submit(() -> {
//            BufferedImage newImage = new BufferedImage(newTile.bufferedImage.getWidth(), newTile.bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
//            renderTile(newImage, zoom, scale, startX, startY, 1);
//            newTile.bufferedImage = newImage;
//        });

        // very Low res
        executor.execute(new PrioritizedTask(1, activeTiles, zoom, x, y, () -> {
            BufferedImage newImage = new BufferedImage(newTile.bufferedImage.getWidth(), newTile.bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            renderTile(newImage, zoom, scale, startX, startY, 32);
            newTile.bufferedImage = newImage;
        }));

        // Low res
        executor.execute(new PrioritizedTask(5, activeTiles, zoom, x, y, () -> {
            BufferedImage newImage = new BufferedImage(newTile.bufferedImage.getWidth(), newTile.bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            renderTile(newImage, zoom, scale, startX, startY, 8);
            newTile.bufferedImage = newImage;
        }));

        // Hi-res
        executor.execute(new PrioritizedTask(10, activeTiles, zoom, x, y, () -> {
            BufferedImage newImage = new BufferedImage(newTile.bufferedImage.getWidth(), newTile.bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            renderTile(newImage, zoom, scale, startX, startY, 1);
            newTile.bufferedImage = newImage;
        }));


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


    public void renderTile(BufferedImage image, int zoom, double scale, double xStart, double yStart, int skip) {

        int prevC = -1;
        double z = (1.0 / Tile.tileHeight) / (scale);
        Graphics g = image.getGraphics();

        for (int y = 0; y < Tile.tileHeight; y += skip) {
            for (int x = 0; x < Tile.tileWidth; x += skip) {
                double xx = xStart + x * z;
                double yy = yStart + y * z;
                int c = ((functionMandelbrot(xx, yy)) % 255) & 0xff;
                if (c != prevC) {
                    prevC = c;
                    g.setColor(palette[c % 0xff]);
                }
                //image.setRGB(x, y, c << 16 | c << 8 | c);
                g.fillRect(x, y, skip, skip);
            }
        }


    }

    private int functionMandelbrot(double x, double y) {
        int MAX_ITERATIONS = 600;
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

    List<Integer[]> activeTiles = new CopyOnWriteArrayList<>();


    public void setActiveTiles(List<Integer[]> activeTiles) {
        this.activeTiles.addAll(activeTiles);
    }

    public int getPendingTaskCount() {
        return (int) (executor.getTaskCount() - executor.getCompletedTaskCount());
    }
}
