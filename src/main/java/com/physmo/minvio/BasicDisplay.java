package com.physmo.minvio;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

// Implements common functionality and alternative types methods for drawing operations.
public abstract class BasicDisplay {

    public static final int TEXT_SIZE_WIDTH = 0;
    public static final int TEXT_SIZE_ASCENT = 1;
    public static final int TEXT_SIZE_DESCENT = 2;


    /* TIMING ---------------------------------------------------------------*/
    public static long repaintTimerStart = 0;

    /* COLOR ----------------------------------------------------------------*/

    public static String[] getAvailableFontNames() {
        return GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
    }

    public static BufferedImage loadImage(String name) throws IOException {
        URL file = BasicDisplay.class.getResource(name);
        BufferedImage image;

        image = ImageIO.read(file);

        return image;
    }

    public static Color lerp(Color c1, Color c2, double pos) {
        pos = clamp(0.0, 1.0, pos);
        int r = lerp(c1.getRed(), c2.getRed(), pos);
        int g = lerp(c1.getGreen(), c2.getGreen(), pos);
        int b = lerp(c1.getBlue(), c2.getBlue(), pos);
        return new Color(r, g, b);
    }

    public static int lerp(int v1, int v2, double pos) {
        int span = v2 - v1;
        return (int) (v1 + (int) (double) span * pos);
    }

    public static Point lerp(Point p1, Point p2, double pos) {
        return new Point(lerp(p1.x, p2.x, pos), lerp(p1.y, p2.y, pos));
    }

    public static double lerp(double v1, double v2, double pos) {
        double span = v2 - v1;
        return (v1 + span * pos);
    }

    public static double clamp(double min, double max, double value) {
        return value < min ? min : value > max ? max : value;
    }

    /**
     * Returns a new distinct colour for each supplied index
     * Colours will be the same for a given index each time it is called.
     *
     * @param index      integer representing the distinct colour
     * @param saturation 0..1 double value
     * @return A distinct color.
     */
    // TODO: Cache some of these
    public Color getDistinctColor(int index, double saturation) {
        float magicNumber = 0.6180339887f;
        return new Color(Color.HSBtoRGB(((float) index) * magicNumber, (float) saturation, 1.0f));
    }

    /**
     * Get the width and height of the application window as a Point
     *
     * @return The width and height of display as a Point object.
     */
    public Point getDisplaySize() {
        return new Point(getWidth(), getHeight());
    }

    /**
     * Get the width of the application window as an int
     *
     * @return The width of the display.
     */
    public abstract int getWidth();

    /**
     * * Get the height of the applicaton window as an int
     *
     * @return The height of the display.
     */
    public abstract int getHeight();

    /**
     * Close the application window.
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
        while (getElapsedTime() < msPerFrame) {

            int remainingTime = (int) (msPerFrame - getElapsedTime());

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

    /**
     * Returns milliseconds since startTimer() was called.
     *
     * @return long value representing number of milliseconds since the last repaint
     */
    public long getElapsedTime() {
        return (System.nanoTime() - repaintTimerStart) / 1_000_000;
    }

    /**
     * Update the display with visual changes that were applied since the last repaint.
     */
    public abstract void repaint();

    /**
     * Set the title of the application window.
     *
     * @param str Text representing the new window title.
     */
    public abstract void setTitle(String str);

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

    public void drawPoint(Point pos) {
        drawPoint((int) pos.x, (int) pos.y);
    }

    // Draw a single point using the current draw colour..
    public abstract void drawPoint(int x, int y);

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




    /* TEXT ---------------------------------------------------------------*/

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

    public abstract Color getDrawColor();

    public abstract Color getBackgroundColor();

    public void saveScreenshot() {
        String filePath = System.getProperty("user.home");
        filePath += File.separator + getTitle().replaceAll("\\s+", "") + ".png";
        saveScreenshot(filePath);
    }

    public abstract String getTitle();

    public void saveScreenshot(String fullPath) {
        try {
            BufferedImage bi = (BufferedImage) getDrawBuffer();
            File outputFile = new File(fullPath);
            ImageIO.write(bi, "png", outputFile);
        } catch (IOException e) {
            System.out.println("Error writing to file: ");
        }
    }

    /**
     * Get the draw buffer for the display as an Image object.
     *
     * @return Image representing the draw buffer.
     */
    public abstract Image getDrawBuffer();
}
