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

        double scale = Math.pow(2.0, wZoom); // scale: world-units per pixel
        int iZoom = (int) wZoom;
        double scaledTileSize = Math.pow(2.0, (wZoom - iZoom)) * Tile.tileWidth;


        // The logical (world) coords of the top-left pixel:
        double worldLeft = scrollX - ((windowWidth / 2.0) / scale);
        double worldTop = scrollY - ((windowHeight / 2.0) / scale);

        // The index of the first (top-left) tile:
        int firstCol = (int) Math.floor(worldLeft / scaledTileSize);
        int firstRow = (int) Math.floor(worldTop / scaledTileSize);

        // How much the top-left pixel is offset into the first tile:
        double offsetX = worldLeft - firstCol * scaledTileSize;
        double offsetY = worldTop - firstRow * scaledTileSize;

        // Number of tiles to cover window (plus one for partial tiles on each border)
        int columns = (int) Math.ceil(windowWidth / scale / scaledTileSize) + 2;
        int rows = (int) Math.ceil(windowHeight / scale / scaledTileSize) + 2;

        TileWindow activeWindow = new TileWindow(iZoom, firstCol, firstRow, columns, rows);

        if (!draw) return activeWindow;

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                int tileX = firstCol + col;
                int tileY = firstRow + row;

                Tile tile = tileManager.getTile(iZoom, tileX, tileY);

                // Now scale tile size in *pixels* by 'scale':
                double pixelLeft = (col * scaledTileSize - offsetX) * scale;
                double pixelTop = (row * scaledTileSize - offsetY) * scale;
                double pixelRight = pixelLeft + scaledTileSize * scale;
                double pixelBottom = pixelTop + scaledTileSize * scale;

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
