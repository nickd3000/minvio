package com.physmo.minvio;

import com.physmo.minvio.utils.RollingAverage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class MinvioApp implements DrawingContext {

    final RollingAverage tickRollingAverage = new RollingAverage(30);
    final Font fpsFont = new Font("Verdana", Font.PLAIN, 12);
    BasicDisplay bd = null;
    boolean running = true;
    int targetFps = 60;
    boolean displayFps = false;
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
        BasicDisplayAwt bd = new BasicDisplayAwt(width, height);
        bd.setTitle("Minvio App");
        bd.getDrawingContext().cls();
        this.targetFps = 60;
        start(bd);
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
            while (bd.getElapsedTime() < msPerFrame) {
                int remainingTime = (int) (msPerFrame - bd.getElapsedTime());

                if (remainingTime < 10) continue;
                try {
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
            bd.repaint();

            //bd.repaintTimerStart = System.nanoTime();
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
     * This may be optionally overridden, for example if you want to do logic multiple times per frame.
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
        DrawingContext dc = bd.getDrawingContext();
        Font currentFont = dc.getFont();
        Color currentColor = dc.getDrawColor();
        dc.setFont(fpsFont);
        String fpsText = String.format("FPS: %.2f", 1000.0 / tickRollingAverage.getAverage());
        dc.setDrawColor(Color.BLACK);
        dc.drawText(fpsText, 11, 16);
        dc.setDrawColor(Color.WHITE);
        dc.drawText(fpsText, 10, 15);
        dc.setFont(currentFont);
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

    @Override
    public void drawImage(BufferedImage sourceImage, int x, int y, int w, int h) {
        drawingContext.drawImage(sourceImage, x, y, w, h);
    }

    @Override
    public int getRGBAtPoint(int x, int y) {
        return drawingContext.getRGBAtPoint(x, y);
    }

    @Override
    public void drawPoint(int x, int y) {
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

    @Override
    public void drawRect(int x, int y, int width, int height) {
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

    public String getTitle() {
        return bd.getTitle();
    }

    public void saveScreenshot(String path) {
        bd.saveScreenshot(path);
    }
}
