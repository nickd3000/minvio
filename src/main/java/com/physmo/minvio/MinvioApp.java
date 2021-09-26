package com.physmo.minvio;

import com.physmo.minvio.utils.RollingAverage;

import java.awt.Color;
import java.awt.Font;

public abstract class MinvioApp {

    BasicDisplay bd = null;
    boolean running = true;

    final RollingAverage tickRollingAverage = new RollingAverage(30);
    int targetFps = 60;
    boolean displayFps = false;
    final Font fpsFont = new Font("Verdana", Font.PLAIN, 12);


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
        bd.setTitle(title);
        bd.cls();
        this.targetFps = fps;
        start(bd);
    }

    /**
     * Start the application - creates the app window and starts the main draw loop running.
     *
     * @param bd an instance of BasicDisplay.
     */
    public void start(BasicDisplay bd) {
        this.bd = bd;

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
                    update((delta) / 1_000_000_000.0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long lDelta = System.nanoTime() - lastDrawTime;
            delta = (double) lDelta;

            tickRollingAverage.add(lDelta / (double) 1000_000);
            lastDrawTime = System.nanoTime();
            BasicDisplay.repaintTimerStart = System.nanoTime();
            draw(bd, (delta) / 1_000_000_000.0);

            if (displayFps) drawFps();
            bd.repaint();

            //bd.repaintTimerStart = System.nanoTime();
        }


    }

    /**
     * Skeleton init function - override this to perform one-time setup operations for your app.
     * You may override this in your own app and it will be called only one time,
     * and before draw or update is called.
     *
     * @param bd the instance of BasicDisplay.
     */
    public void init(BasicDisplay bd) {
    }

    /**
     * This may be optionally overridden, for example if you want to do logic multiple times per frame.
     *
     * @param delta time in seconds since the last UPDATE call, e.g. 1.0 = 1 second.
     */
    public void update(double delta) {
    }


    /**
     * The main draw function of your app, you must override this in your MinvioApp based
     * program.  It is called once per fram (according to the FPS value), after it has
     * been called, the display will be refreshed automatically.
     *
     * @param bd    the instance of BasicDisplay.
     * @param delta time in seconds since the last DRAW call, e.g. 1.0 = 1 second.
     */
    public abstract void draw(BasicDisplay bd, double delta);

    private void drawFps() {
        Font currentFont = bd.getFont();
        Color currentColor = bd.getDrawColor();
        bd.setFont(fpsFont);
        String fpsText = String.format("FPS: %.2f", 1000.0 / tickRollingAverage.getAverage());
        bd.setDrawColor(Color.BLACK);
        bd.drawText(fpsText, 11, 16);
        bd.setDrawColor(Color.WHITE);
        bd.drawText(fpsText, 10, 15);
        bd.setFont(currentFont);
        bd.setDrawColor(currentColor);
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

}
