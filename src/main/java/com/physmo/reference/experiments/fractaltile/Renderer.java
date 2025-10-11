package com.physmo.reference.experiments.fractaltile;

import com.physmo.minvio.DrawingContext;

/**
 * The Renderer class is responsible for rendering a tiled graphical representation
 * of a specific region within a window. It integrates with a TileManager that provides
 * tile data and facilitates drawing them onto a specified DrawingContext. The rendering
 * is dynamically adjusted based on zoom levels, scrolling offsets, and window dimensions.
 */
public class Renderer {


    public TileWindow render(TileManager tileManager, DrawingContext dc,
                             double wZoom, int windowWidth, int windowHeight,
                             double scrollX, double scrollY, boolean draw) {

        int iZoom = (int) wZoom;
        double scaledTileSize = Math.pow(2.0, (wZoom - iZoom)) * Tile.tileWidth;

        int columns = ((int) Math.ceil(windowWidth / scaledTileSize)) + 2; // or +1 if more than enough
        int rows = ((int) Math.ceil(windowHeight / scaledTileSize)) + 2;
        int firstCol = (int) ((scrollX) / scaledTileSize);
        int firstRow = (int) ((scrollY) / scaledTileSize);

        double tileWrappedOffsetX = (scrollX % scaledTileSize);
        double tileWrappedOffsetY = (scrollY % scaledTileSize);

        TileWindow activeWindow = new TileWindow(iZoom, -1 - firstCol, -1 - firstRow, columns, rows);

        if (!draw) return activeWindow;

        for (int col = -1; col < columns + 2; col++) {

            for (int row = -1; row < rows + 2; row++) {
                Tile tile = tileManager.getTile(iZoom, col - firstCol, row - firstRow);

                // Calculate the exact pixel rectangle intended for this tile
                double pixelLeft = col * scaledTileSize + tileWrappedOffsetX;
                double pixelTop = row * scaledTileSize + tileWrappedOffsetY;
                double pixelRight = (col + 1) * scaledTileSize + tileWrappedOffsetX;
                double pixelBottom = (row + 1) * scaledTileSize + tileWrappedOffsetY;

                int drawX = (int) Math.round(pixelLeft);
                int drawY = (int) Math.round(pixelTop);
                int drawW = (int) Math.round(pixelRight) - drawX;
                int drawH = (int) Math.round(pixelBottom) - drawY;

                dc.drawImage(tile.bufferedImage, drawX, drawY, drawW, drawH);
            }

        }

        return activeWindow;

    }


}
