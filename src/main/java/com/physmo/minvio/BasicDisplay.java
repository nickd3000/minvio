package com.physmo.minvio;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface BasicDisplay {

    /**
     * @return The width of the display.
     */
    int getWidth();

    /**
     * @return The height of the display.
     */
    int getHeight();

    /**
     * Close the window.
     */
    void close();

    /**
     * Update the display with drawing changes.
     */
    void refresh();

    /**
     * Update the display with drawing changes.
     * This variant delays execuition to keep the refresh rate at fps frames per second.
     */
    void refresh(int fps);

    /**
     * @param str   Text to set the window title.
     */
    void setTitle(String str);

    /**
     * Clear the display to the supplied color.
     * @param c     AWT color to clear the display to.
     */
    void cls(Color c);

    /**
     * Set the color to use with drawing operations.
     * @param newCol The color that future draw operations will use.
     * @return  The previous color.
     */
    Color setDrawColor(Color newCol);


    /**
     * Get the draw buffer for the display.
     * @return Image representing the draw buffer.
     */
    Image getDrawBuffer();


    /**
     * Draw an image to the display.
     * @param sourceImage
     * @param x
     * @param y
     */
    void drawImage(BufferedImage sourceImage, int x, int y);

    /**
     * Draw an image to the display.
     * @param sourceImage
     * @param x
     * @param y
     * @param w
     * @param h
     */
    void drawImage(BufferedImage sourceImage, int x, int y, int w, int h);

    /**
     * Get the colour at the defined position.
     * @param x
     * @param y
     * @return Color value
     */
    Color getColorAtPoint(int x, int y);

    /**
     * Get the color in RGB notation at the defined position.
     * @param x
     * @param y
     * @return
     */
    int getRGBAtPoint(int x, int y);

    /**
     * Draw a single point.
     * @param x
     * @param y
     */
    void drawPoint(int x, int y);

    /**
     * Draw a line.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    void drawLine(int x1, int y1, int x2, int y2);

    /**
     * Draw a line with more control, and non-integer position.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param thickness
     */
    void drawLine(double x1, double y1, double x2, double y2, double thickness);

    void drawFilledRect(int x, int y, int width, int height);

    void drawRect(int x1, int y1, int x2, int y2);

    void drawFilledPolygon(int[] xPoints, int[] yPoints, int numPoints);

    void drawFilledCircle(double x, double y, double r);

    /**
     * Draw a centred circle.
     * @param x x position of circle center
     * @param y y position of circle center
     * @param r radius
     */
    void drawCircle(double x, double y, double r);

    void drawText(String str, int x, int y);

    void setFont(Font font);

    void startTimer();

    // Returns milliseconds since startTimr() was called.
    long getEllapsedTime();

    // Returns a new distinct colour for each supplied index.
    Color getDistinctColor(int index, double saturation);

    // Input and output.
    // Update previous keys with current keys so we can tell what changed next time.
    void tickInput();

    int[] getKeyState();

    int[] getKeyStatePrevious();

    int getMouseX();

    int getMouseY();

    boolean getMouseButtonLeft();
    boolean getMouseButtonMiddle();
    boolean getMouseButtonRight();

}
