package com.physmo.minvio.utils.gui.support;

public interface MouseConnector {
    void onMouseMoved(int x, int y);

    void onButtonDown(int x, int y, int buttonId);

    void onButtonUp(int x, int y, int buttonId);
}
