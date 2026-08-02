package com.physmo.minvio;

import com.physmo.minvio.utils.gui.support.MouseConnector;

import java.awt.Image;
import java.awt.image.BufferedImage;

public final class TestBasicDisplay extends BasicDisplay {
    private final BufferedImage buffer;
    private final DrawingContext drawingContext;
    private final int[] keyState;
    private final int[] previousKeyState;
    private int width;
    private int height;
    private int mouseX;
    private int mouseY;
    private boolean leftButton;
    private boolean middleButton;
    private boolean rightButton;
    private boolean visible = true;
    private String title = "test";

    public TestBasicDisplay(int width, int height) {
        this(width, height, 256);
    }

    public TestBasicDisplay(int width, int height, int keyStateSize) {
        this.width = width;
        this.height = height;
        keyState = new int[keyStateSize];
        previousKeyState = new int[keyStateSize];
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawingContext = new DrawingContextAwt(buffer);
    }

    public void setMouse(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    public void setLeftButton(boolean pressed) {
        leftButton = pressed;
    }

    public void fireMouseMoved(int x, int y) {
        setMouse(x, y);
        for (MouseConnector connector : mouseConnectors) {
            connector.onMouseMoved(x, y);
        }
    }

    public void fireButtonDown(int x, int y, int button) {
        setMouse(x, y);
        for (MouseConnector connector : mouseConnectors) {
            connector.onButtonDown(x, y, button);
        }
    }

    public void fireButtonUp(int x, int y, int button) {
        setMouse(x, y);
        for (MouseConnector connector : mouseConnectors) {
            connector.onButtonUp(x, y, button);
        }
    }

    @Override
    public DrawingContext getDrawingContext() {
        return drawingContext;
    }

    @Override
    public void reset() {
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void close() {
        visible = false;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void repaint() {
    }

    @Override
    public void tickInput() {
        System.arraycopy(keyState, 0, previousKeyState, 0, keyState.length);
    }

    @Override
    public int[] getKeyState() {
        return keyState;
    }

    @Override
    public int[] getKeyStatePrevious() {
        return previousKeyState;
    }

    @Override
    public int getMouseX() {
        return mouseX;
    }

    @Override
    public int getMouseY() {
        return mouseY;
    }

    @Override
    public boolean getMouseButtonLeft() {
        return leftButton;
    }

    @Override
    public boolean getMouseButtonMiddle() {
        return middleButton;
    }

    @Override
    public boolean getMouseButtonRight() {
        return rightButton;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String str) {
        title = str;
    }

    @Override
    public Image getDrawBuffer() {
        return buffer;
    }

    @Override
    public void resizeIfRequested() {
    }
}
