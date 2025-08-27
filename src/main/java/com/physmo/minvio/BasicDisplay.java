package com.physmo.minvio;

import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.gui.support.MouseConnector;

import javax.imageio.ImageIO;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

/**
 * The BasicDisplay class defines an abstract framework for creating and manipulating
 * a graphical display. This class includes support for rendering visual content,
 * retrieving input states, handling mouse events, and managing the display's lifecycle.
 * Implementing classes must provide concrete implementations of abstract methods
 * such as rendering and input-related functionalities.
 */
public abstract class BasicDisplay {

    public static final int TEXT_SIZE_WIDTH = 0;
    public static final int TEXT_SIZE_ASCENT = 1;
    public static final int TEXT_SIZE_DESCENT = 2;
    /* TIMING ---------------------------------------------------------------*/
    public static long repaintTimerStart = 0;
    List<MouseConnector> mouseConnectors;

    IntBinaryOperator resizeListener;

    public BasicDisplay() {
        mouseConnectors = new ArrayList<>();
    }

    /**
     * Get list of all available font names.
     *
     * @return list of all available font names.
     */
    public static String[] getAvailableFontNames() {
        return GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
    }

    /**
     * Load an image from storage and return a BufferedImage object
     *
     * @param name Path to file
     * @return BufferedImage object
     * @throws IOException on file error
     */
    public static BufferedImage loadImage(String name) throws IOException {
        URL file = BasicDisplay.class.getResource(name);
        BufferedImage image;

        image = ImageIO.read(file);

        return image;
    }

    /* COLOR ----------------------------------------------------------------*/

    public void addMouseConnector(MouseConnector mouseConnector) {
        mouseConnectors.add(mouseConnector);
    }

    public abstract DrawingContext getDrawingContext();

//    /**
//     * Returns a new distinct colour for each supplied index
//     * Colours will be the same for a given index each time it is called.
//     *
//     * @param index      integer representing the distinct colour
//     * @param saturation 0..1 double value
//     * @return A distinct color.
//     */
//    // TODO: Cache some of these
//    public Color getDistinctColor(int index, double saturation) {
//        float magicNumber = 0.6180339887f;
//        return new Color(Color.HSBtoRGB(((float) index) * magicNumber, (float) saturation, 1.0f));
//    }

    /**
     * Set all values to defaults.
     * e.g. Draw Colors, fonts.
     */
    public abstract void reset();

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

    // Input and output.
    // Update previous keys with current keys so we can tell what changed next time.
    public abstract void tickInput();

    public abstract int[] getKeyState();

    public abstract int[] getKeyStatePrevious();

    public Point getMousePoint() {
        return new Point(getMouseX(), getMouseY());
    }

    public abstract int getMouseX();

    public Point getMousePointNormalised() {
        return new Point((double) getMouseX() / getDisplaySize().x, (double) getMouseY() / getDisplaySize().y);
    }

    public abstract int getMouseY();

    public abstract boolean getMouseButtonLeft();

    public abstract boolean getMouseButtonMiddle();

    public abstract boolean getMouseButtonRight();

    /**
     * Write an image file of the current BasicDisplay window to the users home folder.
     */
    public void saveScreenshot() {
        String filePath = System.getProperty("user.home");
        filePath += File.separator + getTitle().replaceAll("\\s+", "") + ".png";
        saveScreenshot(filePath);
    }

    public abstract String getTitle();

    /**
     * Set the title of the application window.
     *
     * @param str Text representing the new window title.
     */
    public abstract void setTitle(String str);

    /**
     * Write an image file of the current BasicDisplay window to the supplied path
     *
     * @param fullPath Path to new image file.
     */
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

    public void addResizeListener(IntBinaryOperator resizeListener) {
        this.resizeListener = resizeListener;
    }

    // When the main window is resized, we don't respond until outside of the draw loop.
    public abstract void resizeIfRequested();
}
