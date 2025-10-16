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
        double fractionalZoom = wZoom - iZoom;

        // scaledTileSize: how many screen pixels one tile occupies (with fractional zoom)
        double scaledTileSize = Math.pow(2.0, fractionalZoom) * Tile.tileWidth;

        // At integer zoom level, each tile represents this many world units
        // zoom 0: 1 tile = 1.0 world unit
        // zoom 1: 1 tile = 0.5 world units  
        // zoom 2: 1 tile = 0.25 world units
        double worldUnitsPerTile = 1.0 / Math.pow(2.0, iZoom);

        // How many screen pixels equal one world unit (at current zoom)
        double pixelsPerWorldUnit = scaledTileSize / worldUnitsPerTile;

        // Convert half the window size to world coordinates
        double halfWorldWidth = (windowWidth / 2.0) / pixelsPerWorldUnit;
        double halfWorldHeight = (windowHeight / 2.0) / pixelsPerWorldUnit;

        // The world coords of the top-left corner of the window
        double worldLeft = scrollX - halfWorldWidth;
        double worldTop = scrollY - halfWorldHeight;

        // Which tile indices contain the top-left corner
        int firstCol = (int) Math.floor(worldLeft / worldUnitsPerTile);
        int firstRow = (int) Math.floor(worldTop / worldUnitsPerTile);

        // How far into the first tile (in world units) is the top-left corner
        double offsetX = worldLeft - firstCol * worldUnitsPerTile;
        double offsetY = worldTop - firstRow * worldUnitsPerTile;

        // How many tiles we need to cover the window
        int columns = (int) Math.ceil(windowWidth / scaledTileSize) + 1;
        int rows = (int) Math.ceil(windowHeight / scaledTileSize) + 1;

        TileWindow activeWindow = new TileWindow(iZoom, firstCol, firstRow, columns, rows);

        if (!draw) return activeWindow;

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                int tileX = firstCol + col;
                int tileY = firstRow + row;

                Tile tile = tileManager.getTile(iZoom, tileX, tileY);

                // Position in screen pixels
                // Each tile starts at col * scaledTileSize, minus the offset (converted to pixels)
                double pixelLeft = col * scaledTileSize - (offsetX * pixelsPerWorldUnit);
                double pixelTop = row * scaledTileSize - (offsetY * pixelsPerWorldUnit);

                int drawX = (int) Math.round(pixelLeft);
                int drawY = (int) Math.round(pixelTop);
                int drawW = (int) Math.round(pixelLeft + scaledTileSize) - drawX;
                int drawH = (int) Math.round(pixelTop + scaledTileSize) - drawY;

                dc.drawImage(tile.bufferedImage, drawX, drawY, drawW, drawH);
            }
        }
        return activeWindow;
    }

}
