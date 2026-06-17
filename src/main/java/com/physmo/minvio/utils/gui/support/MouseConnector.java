package com.physmo.minvio.utils.gui.support;

/**
 * Callback interface used by displays to forward mouse events to GUI code.
 */
public interface MouseConnector {
    /**
     * Handles mouse movement in display coordinates.
     *
     * @param x display x-coordinate
     * @param y display y-coordinate
     */
    void onMouseMoved(int x, int y);

    /**
     * Handles a mouse-button press in display coordinates.
     *
     * @param x display x-coordinate
     * @param y display y-coordinate
     * @param buttonId AWT-style button identifier
     */
    void onButtonDown(int x, int y, int buttonId);

    /**
     * Handles a mouse-button release in display coordinates.
     *
     * @param x display x-coordinate
     * @param y display y-coordinate
     * @param buttonId AWT-style button identifier
     */
    void onButtonUp(int x, int y, int buttonId);
}
