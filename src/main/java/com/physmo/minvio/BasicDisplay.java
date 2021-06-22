package com.physmo.minvio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

// Implements common functionality and alternative types methods for drawing operations.
public abstract class BasicDisplay {

    public static final int TEXTSIZE_WIDTH = 0;
    public static final int TEXTSIZE_ASCENT = 1;
    public static final int TEXTSIZE_DESCENT = 2;


    /* TIMING ---------------------------------------------------------------*/
    private static long repaintTimerStart = 0;

    /* COLOR ----------------------------------------------------------------*/
    // Returns a new distinct colour for each supplied index.
    public Color getDistinctColor(int index, double saturation) {
        float magicNumber = 0.6180339887f;
        return new Color(Color.HSBtoRGB(((float) index) * magicNumber, (float) saturation, 1.0f));
    }

    /**
     * @return The width and height of display as a Point object.
     */
    public Point getDisplaySize() {
        return new Point(getWidth(), getHeight());
    }

    /**
     * @return The width of the display.
     */
    public abstract int getWidth();

    /**
     * @return The height of the display.
     */
    public abstract int getHeight();

    /**
     * Close the window.
     */
    public abstract void close();

    /**
     * Update the display with drawing changes.
     * This variant delays execution to keep the refresh rate at fps frames per second.
     *
     * @param fps frames per second
     */
    public void repaint(int fps) {

        int msPerFrame = 1000 / fps; // e.g.g 33.3 for 30fps
        while (getEllapsedTime() < msPerFrame) {

            int remainingTime = (int) (msPerFrame - getEllapsedTime());

            if (remainingTime < 5) continue;
            try {
                Thread.sleep(5);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        repaint();

        repaintTimerStart = System.nanoTime();
    }

    // Returns milliseconds since startTimer() was called.
    public long getEllapsedTime() {
        return (System.nanoTime() - repaintTimerStart) / 1_000_000;
    }

    /**
     * Update the display with drawing changes.
     */
    public abstract void repaint();

    /**
     * @param str Text to set the window title.
     */
    public abstract void setTitle(String str);

    /**
     * Clear the display to the supplied color.
     *
     * @param c AWT color to clear the display to.
     */
    public abstract void cls(Color c);

    /**
     * Set the color to use with drawing operations.
     *
     * @param newCol The color that future draw operations will use.
     * @return The previous color.
     */
    public abstract Color setDrawColor(Color newCol);


    /**
     * Get the draw buffer for the display.
     *
     * @return Image representing the draw buffer.
     */
    public abstract Image getDrawBuffer();


    /**
     * Draw an image to the display.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x position
     * @param y           y position
     */
    public abstract void drawImage(BufferedImage sourceImage, int x, int y);

    /**
     * Draw an image to the display.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           position
     * @param y           position
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
     * @param x x position
     * @param y y position
     * @return Color value
     */
    public Color getColorAtPoint(int x, int y) {
        int rgb = this.getRGBAtPoint(x, y);
        return new Color(rgb);
    }

    /**
     * Get the color in RGB notation at the defined position.
     *
     * @param x x position
     * @param y y position
     * @return integer RGB value
     */
    public abstract int getRGBAtPoint(int x, int y);

    public void drawPoint(Point pos) {
        drawPoint((int) pos.x, (int) pos.y);
    }

    // Draw a single point using the current draw colour..
    public abstract void drawPoint(int x, int y);

    public void drawLine(double x1, double y1, double x2, double y2) {
        drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    /* LINE ---------------------------------------------------------------*/
    public abstract void drawLine(int x1, int y1, int x2, int y2);

    public void drawLine(Point pos1, Point pos2) {
        drawLine((int) pos1.x, (int) pos1.y, (int) pos2.x, (int) pos2.y);
    }

    public void drawLine(Point pos1, Point pos2, double thickness) {
        drawLine(pos1.x, pos1.y, pos2.x, pos2.y, thickness);
    }

    public abstract void drawLine(double x1, double y1, double x2, double y2, double thickness);

    /* RECT ---------------------------------------------------------------*/
    public abstract void drawFilledRect(int x, int y, int width, int height);

    public abstract void drawRect(int x, int y, int width, int height);

    /* POLYGON ---------------------------------------------------------------*/

    public abstract void drawFilledPolygon(int[] xPoints, int[] yPoints, int numPoints);

    public void drawCircle(Point pos, double r) {
        drawCircle(pos.x, pos.y, r);
    }

    /* CIRCLE ---------------------------------------------------------------*/
    public abstract void drawCircle(double x, double y, double r);

    public void drawFilledCircle(Point pos, double r) {
        drawFilledCircle(pos.x, pos.y, r);
    }

    public abstract void drawFilledCircle(double x, double y, double r);




    /* TEXT ---------------------------------------------------------------*/

    public abstract void drawText(String str, int x, int y);

    public abstract void setFont(Font font);

    // Use a built in font.
    public abstract void setFont(int size);

    // Returns 3 values representing the width, ascent and descent values of the font
    // Use the supplied index variables to access them:
    // TEXTSIZE_WIDTH
    // TEXTSIZE_ASCENT
    // TEXTSIZE_DESCENT
    public abstract int[] getTextSize(String str);


    // Input and output.
    // Update previous keys with current keys so we can tell what changed next time.
    public abstract void tickInput();

    public abstract int[] getKeyState();

    public abstract int[] getKeyStatePrevious();

    public Point getMousePoint() {
        return new Point(getMouseX(), getMouseY());
    }

    public abstract int getMouseX();

    public abstract int getMouseY();

    public abstract boolean getMouseButtonLeft();

    public abstract boolean getMouseButtonMiddle();

    public abstract boolean getMouseButtonRight();
}
