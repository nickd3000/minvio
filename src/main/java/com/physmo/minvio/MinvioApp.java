package com.physmo.minvio;

import com.physmo.minvio.utils.RollingAverage;

import com.physmo.minvio.utils.ecs.Entity;
import com.physmo.minvio.utils.ecs.EntitySystem;
import java.awt.Color;

import com.physmo.minvio.utils.MinvioLogger;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class MinvioApp implements DrawingContext {

    final RollingAverage tickRollingAverage = new RollingAverage(30);
    final Font fpsFont = new Font("Verdana", Font.PLAIN, 12);
    BasicDisplay bd = null;
    boolean running = true;
    int targetFps = 60;
    boolean displayFps = false;
    boolean debugMode = false;
    EntitySystem debugEntitySystem = null;
    private int screenshotKey = KeyEvent.VK_F12;
    private boolean screenshotEnabled = true;
    private DrawingContext drawingContext;

    public BasicDisplay getBasicDisplay() {
        return bd;
    }

    /**
     * Stop the application.
     */
    // TODO: This should call a user implemented destroy method.
    public void stop() {
        running = false;
    }


    /**
     * Start the application - creates the app window and starts the main draw loop running.
     *
     * @param bd    an instance of BasicDisplay.
     * @param title Title of the window
     * @param fps   Frames-per-second of the draw loop.
     */
    public void start(BasicDisplay bd, String title, int fps) {
        this.bd = bd;
        bd.setTitle(title);
        bd.getDrawingContext().cls();
        this.targetFps = fps;
        start(bd);
    }

    /**
     * Starts the MinvioApp by creating the application window and starting the main draw loop running.
     *
     * @param width The width of the application window.
     * @param height The height of the application window.
     * @return The MinvioApp instance.
     */
    public MinvioApp start(int width, int height) {
        start(width, height, "Minvio App", 60);
        return this;
    }


    /**
     * Start the application - creates the application window and starts the main draw loop running.
     *
     * @param width  The width of the application window.
     * @param height The height of the application window.
     * @param title  The title of the application window.
     * @param fps    The desired frames per second for the draw loop.
     * @return The MinvioApp instance.
     */
    public MinvioApp start(int width, int height, String title, int fps) {
        BasicDisplayAwt bd = new BasicDisplayAwt(width, height);
        bd.setTitle(title);
        bd.getDrawingContext().cls();
        this.targetFps = fps;
        start(bd);
        return this;
    }

    /**
     * Start the application - creates the app window and starts the main draw loop running.
     *
     * @param bd an instance of BasicDisplay.
     */
    public void start(BasicDisplay bd) {
        this.bd = bd;
        this.drawingContext = bd.getDrawingContext();

        // Call init() once only.
        init(bd);

        long lastUpdateTime = System.nanoTime();
        long lastDrawTime = System.nanoTime();

        int msPerFrame = 1000 / targetFps; // e.g.g 33.3 for 30fps
        double delta;

        while (running) {
            // Check for system-level triggers (like screenshots)
            handleSystemInputs();

            // Synchronize keyboard/mouse state for the current frame
            bd.tickInput();

            while (bd.getElapsedTime() < msPerFrame) {
                int remainingTime = (int) (msPerFrame - bd.getElapsedTime());

                try {
                    if (remainingTime < 10) {
                        if (remainingTime > 0) Thread.sleep(remainingTime);
                        continue;
                    }

                    Thread.sleep(5);
                    delta = (double) (System.nanoTime() - lastUpdateTime);
                    lastUpdateTime = System.nanoTime();
                    update(bd, (delta) / 1_000_000_000.0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long lDelta = System.nanoTime() - lastDrawTime;
            delta = (double) lDelta;

            tickRollingAverage.add(lDelta / (double) 1000_000);
            lastDrawTime = System.nanoTime();
            BasicDisplay.repaintTimerStart = System.nanoTime();
            draw((delta) / 1_000_000_000.0);

            if (displayFps) drawFps();
            if (debugMode) drawDebugInfo();
            bd.repaint();

            bd.resizeIfRequested();
        }


    }

    /**
     * Skeleton init function - override this to perform one-time setup operations for your app.
     * You may override this in your own app: it will be called only one time,
     * and before draw or update is called.
     *
     * @param bd the instance of BasicDisplay.
     */
    public void init(BasicDisplay bd) {
    }

    /**
     * This may be optionally overridden, for example, if you want to do logic multiple times per frame.
     *
     * @param bd    Basic Display object
     * @param delta time in seconds since the last UPDATE call, e.g. 1.0 = 1 second.
     */
    public void update(BasicDisplay bd, double delta) {
    }


    /**
     * The main draw function of your app, you must override this in your MinvioApp based
     * program.  It is called once per frame (according to the FPS value), after it has
     * been called, the display will be refreshed automatically.
     *
     * @param delta time in seconds since the last DRAW call, e.g. 1.0 = 1 second.
     */
    public void draw(double delta) {
    }


    private void drawFps() {
        drawTextWithShadow(String.format("FPS: %.2f", 1000.0 / tickRollingAverage.getAverage()), 10, 15);
    }

    private void drawDebugInfo() {
        DrawingContext dc = bd.getDrawingContext();
        Font currentFont = dc.getFont();
        Color currentColor = dc.getDrawColor();
        dc.setFont(fpsFont);

        String fpsText = String.format("FPS: %.2f", 1000.0 / tickRollingAverage.getAverage());
        String mouseText = String.format("Mouse: %d, %d", getMouseX(), getMouseY());

        drawTextWithShadow(fpsText, 10, 15);
        drawTextWithShadow(mouseText, 10, 30);

        if (debugEntitySystem != null) {
            drawEntityDebug();
        }

        dc.setFont(currentFont);
        dc.setDrawColor(currentColor);
    }

    private void drawTextWithShadow(String text, int x, int y) {
        DrawingContext dc = bd.getDrawingContext();
        Font currentFont = dc.getFont();
        Color currentColor = dc.getDrawColor();

        dc.setDrawColor(Color.BLACK);
        dc.drawText(text, x + 1, y + 1);
        dc.setDrawColor(Color.WHITE);
        dc.drawText(text, x, y);

        dc.setDrawColor(currentColor);
    }

    private void drawEntityDebug() {
        if (debugEntitySystem == null) return;
        DrawingContext dc = bd.getDrawingContext();
        Color currentColor = dc.getDrawColor();

        dc.setDrawColor(Color.RED);
        for (Entity entity : debugEntitySystem.getEntities()) {
            dc.drawRect(entity.position.x - 5, entity.position.y - 5, 10, 10);
        }

        dc.setDrawColor(currentColor);
    }

    /**
     * Set the target frames per second.
     *
     * @param targetFps integer frames per second target.
     */
    public void setFpsTarget(int targetFps) {
        this.targetFps = targetFps;
    }

    /**
     * Return the average FPS (Frames per second)
     *
     * @return double representing Frames Per Second.
     */
    public double getFps() {
        return 1000.0 / tickRollingAverage.getAverage();
    }

    /**
     * Enable or disable built-in fps display.
     *
     * @param set Boolean value representing desired draw state.
     */
    public void setDisplayFps(boolean set) {
        displayFps = set;
    }

    /**
     * Enable or disable debug mode.
     * When enabled, displays FPS and mouse coordinates.
     *
     * @param set Boolean value representing desired debug state.
     */
    public void setDebugMode(boolean set) {
        debugMode = set;
    }

    /**
     * Returns whether debug mode is enabled.
     *
     * @return true if debug mode is enabled.
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Attach an EntitySystem for debug visualization.
     * When debug mode is enabled, it will draw markers for entities.
     *
     * @param entitySystem The EntitySystem to monitor.
     */
    public void setDebugEntitySystem(EntitySystem entitySystem) {
        this.debugEntitySystem = entitySystem;
    }

    public DrawingContext getDrawingContext() {
        return drawingContext;
    }

    public int getMouseX() {
        return bd.getMouseX();
    }

    public int getMouseY() {
        return bd.getMouseY();
    }

    @Override
    public void cls(Color c) {
        drawingContext.cls(c);
    }

    @Override
    public void cls() {
        drawingContext.cls();
    }

    @Override
    public void setImageBuffer(BufferedImage image) {
        drawingContext.setImageBuffer(image);
    }

    @Override
    public Color setDrawColor(Color newCol) {
        return drawingContext.setDrawColor(newCol);
    }

    @Override
    public Color setBackgroundColor(Color newCol) {
        return drawingContext.setBackgroundColor(newCol);
    }

    @Override
    public void drawImage(BufferedImage sourceImage, int x, int y) {
        drawingContext.drawImage(sourceImage, x, y);
    }

    /**
     * Draw an image to the display.
     * Coordinates are cast to integers.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     */
    @Override
    public void drawImage(BufferedImage sourceImage, double x, double y) {
        drawingContext.drawImage(sourceImage, x, y);
    }

    @Override
    public void drawImage(BufferedImage sourceImage, int x, int y, int w, int h) {
        drawingContext.drawImage(sourceImage, x, y, w, h);
    }

    /**
     * Draw an image to the display.
     * Coordinates are cast to integers.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param w           width
     * @param h           height
     */
    @Override
    public void drawImage(BufferedImage sourceImage, double x, double y, double w, double h) {
        drawingContext.drawImage(sourceImage, x, y, w, h);
    }

    @Override
    public int getRGBAtPoint(int x, int y) {
        return drawingContext.getRGBAtPoint(x, y);
    }

    /**
     * Get the color in RGB packed integer format at the defined position.
     * <p>
     * Format in hex: 0xAARRGGBB
     * Coordinates are cast to integers.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return integer RGB value
     */
    @Override
    public int getRGBAtPoint(double x, double y) {
        return drawingContext.getRGBAtPoint(x, y);
    }

    @Override
    public void drawPoint(int x, int y) {
        drawingContext.drawPoint(x, y);
    }

    /**
     * Drawing function - Draw a pixel using current draw color.
     * Coordinates are cast to integers.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    @Override
    public void drawPoint(double x, double y) {
        drawingContext.drawPoint(x, y);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        drawingContext.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2, double thickness) {
        drawingContext.drawLine(x1, y1, x2, y2, thickness);
    }

    @Override
    public void drawFilledRect(int x, int y, int width, int height) {
        drawingContext.drawFilledRect(x, y, width, height);
    }

    /**
     * Drawing function - draw a filled rectangle
     * Coordinates are cast to integers.
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    @Override
    public void drawFilledRect(double x, double y, double width, double height) {
        drawingContext.drawFilledRect(x, y, width, height);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        drawingContext.drawRect(x, y, width, height);
    }

    /**
     * Drawing function - draw an unfilled rectangle
     * Coordinates are cast to integers.
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    @Override
    public void drawRect(double x, double y, double width, double height) {
        drawingContext.drawRect(x, y, width, height);
    }

    @Override
    public void drawFilledPolygon(int[] xPoints, int[] yPoints, int numPoints) {
        drawingContext.drawFilledPolygon(xPoints, yPoints, numPoints);
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        drawingContext.drawCircle(x, y, r);
    }

    @Override
    public void drawFilledCircle(double x, double y, double r) {
        drawingContext.drawFilledCircle(x, y, r);
    }

    @Override
    public void drawText(String str, int x, int y) {
        drawingContext.drawText(str, x, y);
    }

    /**
     * Draw the supplied string using the active font.
     * Coordinates are cast to integers.
     *
     * @param str text to draw
     * @param x   x-coordinate
     * @param y   y-coordinate
     */
    @Override
    public void drawText(String str, double x, double y) {
        drawingContext.drawText(str, x, y);
    }

    @Override
    public void setFont(Font font) {
        drawingContext.setFont(font);
    }

    @Override
    public Font getFont() {
        return drawingContext.getFont();
    }

    @Override
    public void setFont(int size) {
        drawingContext.setFont(size);
    }

    @Override
    public int[] getTextSize(String str) {
        return drawingContext.getTextSize(str);
    }

    @Override
    public Color getDrawColor() {
        return drawingContext.getDrawColor();
    }

    @Override
    public Color getBackgroundColor() {
        return drawingContext.getBackgroundColor();
    }

    @Override
    public Image getDrawBuffer() {
        return drawingContext.getDrawBuffer();
    }

    public int getWidth() {
        return bd.getWidth();
    }

    public int getHeight() {
        return bd.getHeight();
    }

    /**
     * Saves the current transformation state onto a stack.
     */
    @Override
    public void pushMatrix() {
        drawingContext.pushMatrix();
    }

    /**
     * Restores the last saved transformation state from the stack.
     */
    @Override
    public void popMatrix() {
        drawingContext.popMatrix();
    }

    /**
     * Moves the origin of the coordinate system.
     *
     * @param x The distance to move along the x-axis.
     * @param y The distance to move along the y-axis.
     */
    @Override
    public void translate(double x, double y) {
        drawingContext.translate(x, y);
    }

    /**
     * Rotates the coordinate system.
     *
     * @param angle The angle of rotation in radians.
     */
    @Override
    public void rotate(double angle) {
        drawingContext.rotate(angle);
    }

    /**
     * Scales the coordinate system uniformly.
     *
     * @param s The scale factor.
     */
    @Override
    public void scale(double s) {
        drawingContext.scale(s);
    }

    /**
     * Scales the coordinate system non-uniformly.
     *
     * @param x The scale factor along the x-axis.
     * @param y The scale factor along the y-axis.
     */
    @Override
    public void scale(double x, double y) {
        drawingContext.scale(x, y);
    }

    public String getTitle() {
        return bd.getTitle();
    }

    public void saveScreenshot(String path) {
        bd.saveScreenshot(path);
    }

    private void handleSystemInputs() {
        if (!screenshotEnabled) return;

        int[] keyState = bd.getKeyState();
        int[] keyStatePrevious = bd.getKeyStatePrevious();

        // Detect "just pressed" state for the screenshot key
        if (keyState[screenshotKey] != 0 && keyStatePrevious[screenshotKey] == 0) {
            takeScreenshot();
        }
    }

    private void takeScreenshot() {
        String title = getTitle().replaceAll("\\s+", ""); // Remove spaces
        if (title.isEmpty()) title = "screenshot";

        String fileName = title + ".png";
        File file = new File(fileName);
        int count = 1;

        // Increment number until we find a name that doesn't exist
        while (file.exists()) {
            fileName = title + "_" + count + ".png";
            file = new File(fileName);
            count++;
        }

        saveScreenshot(file.getAbsolutePath());
        MinvioLogger.info("Screenshot saved: " + file.getAbsolutePath());
    }
}
