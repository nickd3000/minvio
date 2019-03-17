package com.physmo.toolbox;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface BasicDisplay {

    int getWidth();

    int getHeight();

    void close();

    void refresh();

    // Refresh variant that delays to keep refresh rate at fps frames per second.
    void refresh(int fps);

    void setTitle(String str);

    void cls(Color c);

    Color setDrawColor(Color newCol);



    Image getDrawBuffer();

    void drawImage(BufferedImage sourceImage, int x, int y);

    Color getColorAtPoint(int x, int y);

    int getRGBAtPoint(int x, int y);

    void drawLine(int x1, int y1, int x2, int y2);

    void drawLine(float x1, float y1, float x2, float y2);

    void drawLine(double x1, double y1, double x2, double y2, double thickness);

    void drawFilledRect(int x, int y, int width, int height);

    void drawRect(int x1, int y1, int x2, int y2);

    void drawFilledCircle(double x, double y, double r);

    void drawCircle(double x, double y, double r);

    void drawText(String str, int x, int y);

    void startTimer();

    // Returns milliseconds since startTimr() was called.
    long getEllapsedTime();

    // Returns a new distinct colour for each supplied index.
    Color getDistinctColor(int index, float saturation);

    // Input and output.
    // Update previous keys with current keys so we can tell what changed next time.
    void tickInput();

    int[] getKeyState();

    int[] getKeyStatePrevious();

    int mouseX();

    int mouseY();


}
