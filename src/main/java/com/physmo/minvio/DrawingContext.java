package com.physmo.minvio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class DrawingContext {
    /**
     * Clear the display to the supplied color.
     *
     * @param c AWT Color object to clear the display to.
     */
    public abstract void cls(Color c);

    public abstract void cls();


    /**
     * Set the color to use for drawing operations.
     *
     * @param newCol The color that future draw operations will use.
     * @return The previous color.
     */
    public abstract Color setDrawColor(Color newCol);

    /**
     * Set the background color to use for drawing operations.
     *
     * @param newCol The color that future draw operations will use.
     * @return The previous color.
     */
    public abstract Color setBackgroundColor(Color newCol);

    /**
     * Draw an image to the display.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     */
    public abstract void drawImage(BufferedImage sourceImage, int x, int y);

    /**
     * Draw an image to the display.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param w           width
     * @param h           height
     */
    public abstract void drawImage(BufferedImage sourceImage, int x, int y, int w, int h);

    /**
     * Get the colour at the defined position.
     *
     * @param pos Position
     * @return Color value
     */
    public Color getColorAtPoint(Point pos) {
        return getColorAtPoint((int) pos.x, (int) pos.y);
    }

    /**
     * Get the colour at the defined position.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Color value
     */
    public Color getColorAtPoint(int x, int y) {
        int rgb = this.getRGBAtPoint(x, y);
        return new Color(rgb);
    }

    /* LINE ---------------------------------------------------------------*/

    /**
     * Get the color in RGB packed integer format at the defined position.
     * <p>
     * Format in hex: 0xAARRGGBB
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return integer RGB value
     */
    public abstract int getRGBAtPoint(int x, int y);

    /**
     * Drawing function - Draw a pixel using current draw color.
     *
     * @param pos position
     */
    public void drawPoint(Point pos) {
        drawPoint((int) pos.x, (int) pos.y);
    }

    /**
     * Drawing function - Draw a pixel using current draw color.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public abstract void drawPoint(int x, int y);

    /**
     * Drawing function - draw a line
     *
     * @param x1 x-coordinate (start)
     * @param y1 y-coordinate (start)
     * @param x2 x-coordinate (end)
     * @param y2 y-coordinate (end)
     */
    public void drawLine(double x1, double y1, double x2, double y2) {
        drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    /* RECT ---------------------------------------------------------------*/

    /**
     * Drawing function - draw a line
     *
     * @param x1 x-coordinate (start)
     * @param y1 y-coordinate (start)
     * @param x2 x-coordinate (end)
     * @param y2 y-coordinate (end)
     */
    public abstract void drawLine(int x1, int y1, int x2, int y2);

    /**
     * Drawing function - draw a line
     *
     * @param pos1 start Point
     * @param pos2 end Point
     */
    public void drawLine(Point pos1, Point pos2) {
        drawLine((int) pos1.x, (int) pos1.y, (int) pos2.x, (int) pos2.y);
    }

    /* POLYGON ---------------------------------------------------------------*/

    /**
     * Drawing function - draw a line
     *
     * @param pos1      start Point
     * @param pos2      end Point
     * @param thickness line thickness
     */
    public void drawLine(Point pos1, Point pos2, double thickness) {
        drawLine(pos1.x, pos1.y, pos2.x, pos2.y, thickness);
    }

    /**
     * Drawing function - draw a line
     *
     * @param x1        x-coordinate (start)
     * @param y1        y-coordinate (start)
     * @param x2        x-coordinate (end)
     * @param y2        y-coordinate (end)
     * @param thickness line thickness
     */
    public abstract void drawLine(double x1, double y1, double x2, double y2, double thickness);

    /* CIRCLE ---------------------------------------------------------------*/

    /**
     * Drawing function - draw a filled rectangle
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    public abstract void drawFilledRect(int x, int y, int width, int height);

    /**
     * Drawing function - draw an unfilled rectangle
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    public abstract void drawRect(int x, int y, int width, int height);

    /**
     * Drawing function - draw a filled polygon
     *
     * @param xPoints   Array of x-coordinate
     * @param yPoints   Array of y-coordinate
     * @param numPoints number of points
     */
    public abstract void drawFilledPolygon(int[] xPoints, int[] yPoints, int numPoints);


    /**
     * Drawing function - draw an unfilled circle
     *
     * @param pos Position
     * @param r   radius
     */
    public void drawCircle(Point pos, double r) {
        drawCircle(pos.x, pos.y, r);
    }

    /**
     * Drawing function - draw a filled circle
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param r radius
     */
    public abstract void drawCircle(double x, double y, double r);

    /**
     * Drawing function - draw a filled circle
     *
     * @param pos Position
     * @param r   radius
     */
    public void drawFilledCircle(Point pos, double r) {
        drawFilledCircle(pos.x, pos.y, r);
    }

    /**
     * Drawing function - draw a filled circle
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param r radius
     */
    public abstract void drawFilledCircle(double x, double y, double r);

    /**
     * Draw the supplied string using the active font.
     *
     * @param str text to draw
     * @param x   x-coordinate
     * @param y   y-coordinate
     */
    public abstract void drawText(String str, int x, int y);

    /**
     * Set current font to the specified font.
     * Example:
     * Font font = new Font("Verdana", Font.PLAIN, 10);
     *
     * @param font the user supplied font
     */
    public abstract void setFont(Font font);

    /**
     * Obtain the current font used by BasicDisplay
     *
     * @return the current font.
     */
    public abstract Font getFont();

    /**
     * Set current font to the built-in font at the specified size.
     *
     * @param size font size
     */
    public abstract void setFont(int size);

    /**
     * Retrieve font metrics of the supplied string in an int array
     * This is useful for accurate text layout.
     * <p>
     * 3 values representing the width, ascent and descent values of the font
     * The provided index variables can be used to access them:
     * TEXT_SIZE_WIDTH
     * TEXT_SIZE_ASCENT
     * TEXT_SIZE_DESCENT
     *
     * @param str string to retrieve metrics from
     * @return an integer array containing various measurements.
     */
    public abstract int[] getTextSize(String str);


    public abstract Color getDrawColor();

    public abstract Color getBackgroundColor();

    /**
     * Get the draw buffer for the display as an Image object.
     *
     * @return Image representing the draw buffer.
     */
    public abstract Image getDrawBuffer();

    public abstract int getWidth();

    public abstract int getHeight();
}
