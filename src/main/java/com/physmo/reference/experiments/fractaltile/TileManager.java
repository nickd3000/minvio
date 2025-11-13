package com.physmo.reference.experiments.fractaltile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TileManager {

    Color[] palette = new Color[0xff];

    int numThreads = 4;

    Map<Integer, Tile> tiles = new HashMap<>();
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
    TileWindow activeWindow = new TileWindow(0, 0, 0, 0, 0);
    TileWindow nextWindow = new TileWindow(0, 0, 0, 0, 0);
    Color gridColor = new Color(0.0f, 1.0f, 1.0f, 0.1f);

    public TileManager() {
        for (int c = 0; c < 0xff; c++) {
            palette[c] = new Color(c, c, c);
        }
    }

    public void tick() {

        processWindow(activeWindow);
        processWindow(nextWindow);

    }

    public void processWindow(TileWindow currentWindow) {

        Tile[] windowTiles = getTilesWithinWindow(currentWindow);

        int activeThreads = executor.getActiveCount();
        int addedTasks = 0;
        if (activeThreads + addedTasks >= numThreads) return;

        double scale = Math.pow(2, currentWindow.zoom());
        double logicalWidth = 1 / scale;
        double logicalHeight = 1 / scale;


        for (Tile tile : windowTiles) {
            if (activeThreads + addedTasks >= numThreads) break;

            if (tile.tileState == TileState.UNINITIALIZED) {
                addedTasks++;
                tile.tileState = TileState.RENDERING_PREVIEW;
                executor.submit(() -> {
                    double startX = tile.x * logicalWidth;
                    double startY = tile.y * logicalHeight;
                    BufferedImage newImage = new BufferedImage(tile.bufferedImage.getWidth(), tile.bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                    renderTile(newImage, currentWindow.zoom(), scale, startX, startY, 8);
                    tile.bufferedImage = newImage;
                    tile.tileState = TileState.AWAITING_FULL_RENDER;
                });
            }
        }

        if (activeThreads + addedTasks >= numThreads) return;

        for (Tile tile : windowTiles) {
            if (activeThreads + addedTasks >= numThreads) break;

            if (tile.tileState == TileState.AWAITING_FULL_RENDER) {
                addedTasks++;
                tile.tileState = TileState.RENDERING_FULL;
                executor.submit(() -> {
                    double startX = tile.x * logicalWidth;
                    double startY = tile.y * logicalHeight;
                    BufferedImage newImage = new BufferedImage(tile.bufferedImage.getWidth(), tile.bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                    renderTile(newImage, activeWindow.zoom(), scale, startX, startY, 1);
                    tile.bufferedImage = newImage;
                    tile.tileState = TileState.AWAITING_AA_RENDER;
                });
            }
        }

        if (activeThreads + addedTasks >= numThreads) return;

        for (Tile tile : windowTiles) {
            if (activeThreads + addedTasks >= numThreads) break;

            if (tile.tileState == TileState.AWAITING_AA_RENDER) {
                addedTasks++;
                tile.tileState = TileState.RENDERING_AA;
                executor.submit(() -> {
                    double startX = tile.x * logicalWidth;
                    double startY = tile.y * logicalHeight;
                    BufferedImage newImage = new BufferedImage(tile.bufferedImage.getWidth(), tile.bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                    renderTileAA(newImage, activeWindow.zoom(), scale, startX, startY, 2);
                    tile.bufferedImage = newImage;
                    tile.tileState = TileState.COMPLETED;
                });
            }
        }

    }

    public Tile[] getTilesWithinWindow(TileWindow window) {
        int numTiles = (window.width() + 3) * (window.height() + 3);
        Tile[] windowTiles = new Tile[numTiles];

        for (int y = -1, count = 0; y < window.height() + 2; y++) {
            for (int x = -1; x < window.width() + 2; x++) {
                windowTiles[count++] = getTile(window.zoom(), window.x() + x, window.y() + y);
            }
        }
        return windowTiles;
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

        g.setColor(gridColor);
        g.drawRect(0, 0, Tile.tileWidth, Tile.tileHeight);
    }

    public void renderTileAA(BufferedImage image, int zoom, double scale, double xStart, double yStart, int subd) {

        int prevC = -1;
        double z = (1.0 / Tile.tileHeight) / (scale);
        Graphics g = image.getGraphics();

        double z2 = z / (double) subd;

        for (int y = 0; y < Tile.tileHeight; y++) {
            for (int x = 0; x < Tile.tileWidth; x++) {
                double _xx = xStart + x * z;
                double _yy = yStart + y * z;

//                int c = ((functionMandelbrot(xx, yy)) % 255) & 0xff;
//                c += ((functionMandelbrot(xx+z2, yy)) % 255) & 0xff;
//                c += ((functionMandelbrot(xx+z2, yy+z2)) % 255) & 0xff;
//                c += ((functionMandelbrot(xx, yy+z2)) % 255) & 0xff;
//                c=c/4;

                int c = 0;
                for (int yy = 0; yy < subd; yy++) {
                    for (int xx = 0; xx < subd; xx++) {
                        c += ((functionMandelbrot(_xx + (z2 * xx), _yy + (z2 * yy))) % 255) & 0xff;
                    }
                }
                c /= (subd * subd);


                if (c != prevC) {
                    prevC = c;
                    g.setColor(palette[c % 0xff]);
                }

                //image.setRGB(x, y, c << 16 | c << 8 | c);
                g.fillRect(x, y, 1, 1);
            }
        }

        g.setColor(gridColor);
        g.drawRect(0, 0, Tile.tileWidth, Tile.tileHeight);
    }

    public Tile getTile(int zoom, int x, int y) {
        return tiles.computeIfAbsent(encode(zoom, x, y), integer -> initTile(zoom, x, y));
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

    // Encode 3 input values into one integer.
    public Integer encode(int zoom, int x, int y) {
        // Use the layout: [zoom: 8 bits][x: 12 bits][y: 12 bits]
        return ((zoom & 0xFF) << 24) | ((x & 0xFFF) << 12) | (y & 0xFFF);
    }

    public Tile initTile(int zoom, int x, int y) {

        return new Tile(zoom, x, y);
    }

    public int[] decode(Integer encoded) {
        int zoom = (encoded >> 24) & 0xFF;
        int x = (encoded >> 12) & 0xFFF;
        int y = encoded & 0xFFF;
        return new int[]{zoom, x, y};
    }

    private int functionMandelbrot2(double x, double y) {
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

    public TileWindow getActiveWindow() {
        return activeWindow;
    }

    public void setActiveWindow(TileWindow window) {
        this.activeWindow = window;
    }

    public void setNextWindow(TileWindow window) {
        this.nextWindow = window;
    }

    public int getPendingTaskCount() {
        return (int) (executor.getTaskCount() - executor.getCompletedTaskCount());
    }
}
