package com.physmo.reference.experiments.fractaltile;

import com.physmo.minvio.DrawingContext;

public class Renderer {

    public ActiveWindow render(TileManager tileManager, DrawingContext dc,
                       double wZoom, int windowWidth, int windowHeight,
                       double scrollX, double scrollY, boolean draw) {

        int iZoom = (int) wZoom;//- 1;
        double scaledTileSize = Math.pow(2.0, (wZoom - iZoom)) * Tile.tileWidth;

        int columns = (int) (windowWidth / scaledTileSize);
        int rows = (int) (windowHeight / scaledTileSize);
        int firstCol = (int) (scrollX / scaledTileSize);
        int firstRow = (int) (scrollY / scaledTileSize);

        double tileWrappedOffsetX = (scrollX % scaledTileSize);
        double tileWrappedOffsetY = (scrollY % scaledTileSize);

        // Integer version of scaledTileSize
        int stzi = (int) scaledTileSize;

        ActiveWindow activeWindow = new ActiveWindow(iZoom, -1 - firstCol, -1 - firstRow, columns, rows);

        for (int col = -1; col < columns + 2; col++) {

            for (int row = -1; row < rows + 2; row++) {
                Tile tile = tileManager.getTile(iZoom, col - firstCol, row - firstRow);

                if (!draw) continue;
                dc.drawImage(tile.bufferedImage,
                        (int) (col * stzi + tileWrappedOffsetX),
                        (int) (row * stzi + tileWrappedOffsetY),
                        stzi, stzi);
            }

        }

        return activeWindow;

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
