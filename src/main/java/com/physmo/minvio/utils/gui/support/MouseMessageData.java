package com.physmo.minvio.utils.gui.support;

/**
 * Mutable payload for GUI mouse messages using container-local coordinates.
 */
public class MouseMessageData {
    /** Local x-coordinate. */
    public int x = 0;
    /** Local y-coordinate. */
    public int y = 0;
    /** Mouse button identifier, or zero for movement messages. */
    public int button = 0;

    /**
     * Creates a mouse-message payload.
     *
     * @param x local x-coordinate
     * @param y local y-coordinate
     * @param button mouse button identifier
     */
    public MouseMessageData(int x, int y, int button) {
        this.x = x;
        this.y = y;
        this.button = button;
    }
}
