package com.physmo.minvio;

import com.physmo.minvio.types.Point;
import com.physmo.minvio.types.Rect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

public interface DrawingContext {
    /**
     * Clear the display to the supplied color.
     *
     * @param c AWT Color object to clear the display to.
     */
    void cls(Color c);

    void cls();


    void setImageBuffer(BufferedImage image);

    /**
     * Set the color to use for drawing operations.
     *
     * @param newCol The color that future draw operations will use.
     * @return The previous color.
     */
    Color setDrawColor(Color newCol);

    /**
     * Set the background color to use for drawing operations.
     *
     * @param newCol The color that future draw operations will use.
     * @return The previous color.
     */
    Color setBackgroundColor(Color newCol);

    /**
     * Draw an image to the display.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     */
    void drawImage(BufferedImage sourceImage, int x, int y);

    /**
     * Draw an image to the display.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param w           width
     * @param h           height
     */
    void drawImage(BufferedImage sourceImage, int x, int y, int w, int h);

    /**
     * Get the colour at the defined position.
     *
     * @param pos Position
     * @return Color value
     */
    default Color getColorAtPoint(Point pos) {
        return getColorAtPoint((int) pos.x, (int) pos.y);
    }

    /**
     * Get the colour at the defined position.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Color value
     */
    default Color getColorAtPoint(int x, int y) {
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
    int getRGBAtPoint(int x, int y);

    /**
     * Drawing function - Draw a pixel using current draw color.
     *
     * @param pos position
     */
    default void drawPoint(Point pos) {
        drawPoint((int) pos.x, (int) pos.y);
    }

    /**
     * Drawing function - Draw a pixel using current draw color.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    void drawPoint(int x, int y);

    /**
     * Drawing function - draw a line
     *
     * @param x1 x-coordinate (start)
     * @param y1 y-coordinate (start)
     * @param x2 x-coordinate (end)
     * @param y2 y-coordinate (end)
     */
    default void drawLine(double x1, double y1, double x2, double y2) {
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
    void drawLine(int x1, int y1, int x2, int y2);

    /**
     * Drawing function - draw a line
     *
     * @param pos1 start Point
     * @param pos2 end Point
     */
    default void drawLine(Point pos1, Point pos2) {
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
    default void drawLine(Point pos1, Point pos2, double thickness) {
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
    void drawLine(double x1, double y1, double x2, double y2, double thickness);

    /* CIRCLE ---------------------------------------------------------------*/

    /**
     * Drawing function - draw a filled rectangle
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    void drawFilledRect(int x, int y, int width, int height);

    default void drawFilledRect(Rect rect) {
        this.drawFilledRect(rect.x, rect.y, rect.w, rect.h);
    }

    /**
     * Drawing function - draw an unfilled rectangle
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    void drawRect(int x, int y, int width, int height);

    default void drawRect(Rect rect) {
        drawRect(rect.x, rect.y, rect.w, rect.h);
    }

    /**
     * Drawing function - draw a filled polygon
     *
     * @param xPoints   Array of x-coordinate
     * @param yPoints   Array of y-coordinate
     * @param numPoints number of points
     */
    void drawFilledPolygon(int[] xPoints, int[] yPoints, int numPoints);


    /**
     * Drawing function - draw an unfilled circle
     *
     * @param pos Position
     * @param r   radius
     */
    default void drawCircle(Point pos, double r) {
        drawCircle(pos.x, pos.y, r);
    }

    /**
     * Drawing function - draw a filled circle
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param r radius
     */
    void drawCircle(double x, double y, double r);

    /**
     * Drawing function - draw a filled circle
     *
     * @param pos Position
     * @param r   radius
     */
    default void drawFilledCircle(Point pos, double r) {
        drawFilledCircle(pos.x, pos.y, r);
    }

    /**
     * Drawing function - draw a filled circle
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param r radius
     */
    void drawFilledCircle(double x, double y, double r);

    /**
     * Draw the supplied string using the active font.
     *
     * @param str text to draw
     * @param x   x-coordinate
     * @param y   y-coordinate
     */
    void drawText(String str, int x, int y);

    /**
     * Set current font to the specified font.
     * Example:
     * Font font = new Font("Verdana", Font.PLAIN, 10);
     *
     * @param font the user supplied font
     */
    void setFont(Font font);

    /**
     * Obtain the current font used by BasicDisplay
     *
     * @return the current font.
     */
    Font getFont();

    /**
     * Set current font to the built-in font at the specified size.
     *
     * @param size font size
     */
    void setFont(int size);

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
    int[] getTextSize(String str);


    Color getDrawColor();

    Color getBackgroundColor();

    /**
     * Get the draw buffer for the display as an Image object.
     *
     * @return Image representing the draw buffer.
     */
    Image getDrawBuffer();

    int getWidth();

    int getHeight();
}
