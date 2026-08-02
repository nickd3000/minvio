package com.physmo.minvio.utils.gui.support;

/**
 * Message kinds routed to GUI containers.
 */
public enum GuiMessage {
    /**
     * Mouse entered a container; currently reserved and not emitted by {@code GuiContext}.
     */
    MOUSE_ENTER,
    /**
     * Mouse left a container; currently reserved and not emitted by {@code GuiContext}.
     */
    MOUSE_LEAVE,
    /**
     * Mouse moved; payload is {@link MouseMessageData}.
     */
    MOUSE_MOVE,
    /**
     * Mouse button pressed; payload is {@link MouseMessageData}.
     */
    MOUSE_BUTTON_DOWN,
    /**
     * Mouse button released; payload is {@link MouseMessageData}.
     */
    MOUSE_BUTTON_UP
}
