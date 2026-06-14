package com.physmo.minvio;

import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.gui.support.MouseConnector;

import com.physmo.minvio.utils.MinvioLogger;
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

    /**
     * Index of the text-width entry returned by {@link DrawingContext#getTextSize(String)}.
     */
    public static final int TEXT_SIZE_WIDTH = 0;
    /** Index of the font-ascent entry returned by {@link DrawingContext#getTextSize(String)}. */
    public static final int TEXT_SIZE_ASCENT = 1;
    /** Index of the font-descent entry returned by {@link DrawingContext#getTextSize(String)}. */
    public static final int TEXT_SIZE_DESCENT = 2;
    /* TIMING ---------------------------------------------------------------*/
    /**
     * Start time used by {@link #getElapsedTime()} and {@link #repaint(int)}.
     *
     * <p>This mutable field is shared by all display instances for historical
     * compatibility. Applications should normally leave it under display
     * control.</p>
     */
    public static long repaintTimerStart = 0;
    List<MouseConnector> mouseConnectors;

    IntBinaryOperator resizeListener;

    /**
     * Creates a display abstraction with an empty mouse-connector collection.
     */
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

    /**
     * Registers a connector that receives subsequent mouse movement and button
     * events from implementations that support connectors.
     *
     * <p>Connectors accumulate and this API does not provide removal. The
     * base class does not reject {@code null}; implementations may fail later
     * while dispatching an event if a null connector is registered.</p>
     *
     * @param mouseConnector connector to append
     */
    public void addMouseConnector(MouseConnector mouseConnector) {
        mouseConnectors.add(mouseConnector);
    }

    /**
     * Returns the drawing context associated with this display.
     *
     * <p>The returned context is owned by the display and normally remains
     * connected to its current draw buffer.</p>
     *
     * @return display drawing context
     */
    public abstract DrawingContext getDrawingContext();

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
     * Check if the display is still visible (not closed).
     *
     * @return true if the display is visible.
     */
    public abstract boolean isVisible();

    /**
     * Update the display with drawing changes.
     * This variant delays execution to keep the refresh rate at fps frames per second.
     *
     * @param fps frames per second
     */
    public void repaint(int fps) {
        if (fps <= 0) {
            throw new IllegalArgumentException("FPS must be greater than zero");
        }

        double msPerFrame = 1000.0 / fps;
        while (getElapsedTime() < msPerFrame) {

            int remainingTime = (int) (msPerFrame - getElapsedTime());

            if (remainingTime < 5) continue;
            try {
                Thread.sleep(5);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
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
     * Advances implementation-specific input history.
     *
     * <p>The AWT implementation copies the current key-state array into the
     * previous-state array. {@link MinvioApp} calls this once near the start of
     * each frame, after handling its own system input and before application
     * updates and drawing.</p>
     */
    public abstract void tickInput();

    /**
     * Returns current keyboard state indexed by AWT key code.
     *
     * <p>Implementations may return live mutable storage rather than a copy.
     * Callers must not modify the returned array and must check its length
     * before indexing it.</p>
     *
     * @return current key-state array, where a non-zero entry means pressed
     */
    public abstract int[] getKeyState();

    /**
     * Returns the keyboard state captured by the most recent
     * {@link #tickInput()} call.
     *
     * <p>Implementations may return live mutable storage rather than a copy.
     * Callers must not modify the returned array and must check its length
     * before indexing it.</p>
     *
     * @return previous key-state array, where a non-zero entry means pressed
     */
    public abstract int[] getKeyStatePrevious();

    /**
     * Returns the current mouse position in display pixel coordinates.
     *
     * @return a new mutable point containing the current mouse coordinates
     */
    public Point getMousePoint() {
        return new Point(getMouseX(), getMouseY());
    }

    /**
     * Returns the current horizontal mouse coordinate in display pixels.
     *
     * @return horizontal mouse coordinate
     */
    public abstract int getMouseX();

    /**
     * Returns the current mouse position divided by the display width and
     * height.
     *
     * <p>The result is not clamped. A zero display dimension follows Java
     * floating-point division rules and can therefore produce an infinite or
     * {@code NaN} coordinate.</p>
     *
     * @return a new mutable point containing normalized mouse coordinates
     */
    public Point getMousePointNormalised() {
        return new Point((double) getMouseX() / getDisplaySize().x, (double) getMouseY() / getDisplaySize().y);
    }

    /**
     * Returns the current vertical mouse coordinate in display pixels.
     *
     * @return vertical mouse coordinate
     */
    public abstract int getMouseY();

    /**
     * Reports whether the primary mouse button is currently pressed.
     *
     * @return {@code true} while the left mouse button is pressed
     */
    public abstract boolean getMouseButtonLeft();

    /**
     * Reports whether the middle mouse button is currently pressed.
     *
     * @return {@code true} while the middle mouse button is pressed
     */
    public abstract boolean getMouseButtonMiddle();

    /**
     * Reports whether the secondary mouse button is currently pressed.
     *
     * @return {@code true} while the right mouse button is pressed
     */
    public abstract boolean getMouseButtonRight();

    /**
     * Write an image file of the current BasicDisplay window to the users home folder.
     */
    public void saveScreenshot() {
        String filePath = System.getProperty("user.home");
        filePath += File.separator + getTitle().replaceAll("\\s+", "") + ".png";
        MinvioLogger.info("Saving screenshot to: " + filePath);
        saveScreenshot(filePath);
    }

    /**
     * Returns the display title.
     *
     * @return current title; implementations may return a synthetic title in
     * headless mode
     */
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
            MinvioLogger.error("Error writing to file: " + fullPath);
            e.printStackTrace();
        }
    }

    /**
     * Get the draw buffer for the display as an Image object.
     *
     * @return Image representing the draw buffer.
     */
    public abstract Image getDrawBuffer();

    /**
     * Sets the callback invoked after an implementation applies a deferred
     * resize.
     *
     * <p>Registration replaces any previous listener. The callback receives
     * the new width and height; its integer result is ignored. Passing
     * {@code null} clears the listener.</p>
     *
     * @param resizeListener replacement resize callback, or {@code null}
     */
    public void addResizeListener(IntBinaryOperator resizeListener) {
        this.resizeListener = resizeListener;
    }

    /**
     * Applies a pending native-window resize, if any.
     *
     * <p>Implementations may defer native resize events so buffer replacement
     * occurs outside the event callback. Calling this method when no resize is
     * pending has no effect.</p>
     */
    public abstract void resizeIfRequested();
}
