package com.physmo.reference.experiments.fractaltile;

import com.physmo.minvio.DrawingContext;

import java.awt.Color;

public class Renderer {

    public void render(TileManager tileManager, DrawingContext dc, double wZoom, int windowWidth, int windowHeight, double scrollX, double scrollY) {

        int iZoom = (int) wZoom;
        double scale = Math.pow(2, 0 + wZoom);


        double scaledTileSize = Math.pow(2.0, (wZoom - iZoom)) * Tile.tileWidth;

        int columns = (int) (windowWidth / scaledTileSize);
        int rows = (int) (windowHeight / scaledTileSize);

        dc.setDrawColor(Color.white);
        dc.drawText("stz:" + scaledTileSize, 10, 10);

        int firstCol = (int) (scrollX / scaledTileSize);
        int firstRow = (int) (scrollY / scaledTileSize);

        double tileWrappedOffsetX = (scrollX % scaledTileSize);
        double tileWrappedOffsetY = (scrollY % scaledTileSize);

        for (int col = -1; col < columns + 1; col++) {

            for (int row = -1; row < rows + 1; row++) {
                Tile tile = tileManager.getTile(iZoom, col - firstCol, row - firstRow);
                dc.drawImage(tile.bufferedImage,
                        (int) (col * scaledTileSize + tileWrappedOffsetX),
                        (int) (row * scaledTileSize + tileWrappedOffsetY),
                        (int) scaledTileSize, (int) scaledTileSize);
            }

        }

//        double pixelSize = tile.span/(double)tile.imageSize;
//        BufferedImage bufferedImage = new BufferedImage(tile.imageSize,tile.imageSize,BufferedImage.TYPE_INT_RGB);
//
//        var g = bufferedImage.getGraphics();
//        for (int x=0;x< tile.imageSize;x++) {
//            for (int y=0;y< tile.imageSize;y++) {
//
//                double noise = functionMandelbrot(tile.x+(x*pixelSize),tile.y+(y*pixelSize));
//                int c = (int) ((noise) % 255) & 0xff;
//                g.setColor(new Color(c, c, c));
//                g.fillRect(x, y, 1, 1);
//            }
//        }
//
//        tile.bufferedImage = bufferedImage;
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
