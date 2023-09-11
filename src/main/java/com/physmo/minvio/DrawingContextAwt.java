package com.physmo.minvio;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class DrawingContextAwt extends DrawingContext {

    BufferedImage buffer;
    Graphics g;
    Graphics2D g2d;
    int width = 0;
    int height = 0;
    private Color drawColor;
    private Color backgroundColor;
    final Map<Integer, Font> builtInFonts = new HashMap<>();

    public DrawingContextAwt(BufferedImage buffer) {
        this.buffer = buffer;
        g = buffer.getGraphics();
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font fnt = new Font("window", Font.BOLD, 20);
        g.setFont(fnt);

        width = buffer.getWidth();
        height = buffer.getHeight();
    }


    /**
     * Clear the display to background color.
     */
    @Override
    public void cls() {
        Color colOld = this.setDrawColor(backgroundColor);

        g.fillRect(0, 0, width, height);

        this.setDrawColor(colOld);
    }

    @Override
    public Color setDrawColor(Color newCol) {
        Color oldCol = drawColor;
        drawColor = newCol;
        g.setColor(newCol);
        return oldCol;
    }

    @Override
    public Color setBackgroundColor(Color newCol) {
        Color oldCol = backgroundColor;
        backgroundColor = newCol;
        return oldCol;
    }


    /**
     * Clear the display to supplied color.
     *
     * @param c Color to fill display with.
     */
    @Override
    public void cls(Color c) {
        Color colOld = this.setDrawColor(c);

        g.fillRect(0, 0, width, height);

        this.setDrawColor(colOld);
    }

    @Override
    public Color getDrawColor() {
        return g.getColor();
    }

    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }


    @Override
    public Image getDrawBuffer() {
        return buffer;
    }

    @Override
    public int getWidth() {
        return buffer.getWidth();
    }

    @Override
    public int getHeight() {
        return buffer.getHeight();
    }

    @Override
    public void drawImage(BufferedImage sourceImage, int x, int y) {
        g.drawImage(sourceImage, x, y, null);
    }

    @Override
    public void drawImage(BufferedImage sourceImage, int x, int y, int w, int h) {
        g.drawImage(sourceImage, x, y, w, h, null);
    }

    @Override
    public int getRGBAtPoint(int x, int y) {
        return buffer.getRGB(x, y);
    }

    @Override
    public void drawPoint(int x, int y) {
        g.drawLine(x, y, x, y);
        //panel.g.drawLine(x1, y1, x2, y2);

    }

    /**
     * Draw Line
     *
     * @param x1 Start X
     * @param y1 Start Y
     * @param x2 End X
     * @param y2 End Y
     */
    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2, double thickness) {
        g2d.setStroke(new BasicStroke((float) thickness));
        g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    @Override
    public void drawFilledRect(int x, int y, int width, int height) {
        g.fillRect(x, y, width, height);
    }

    /**
     * Draw rectangle outline
     *
     * @param x      Start X
     * @param y      Start Y
     * @param width  Width
     * @param height Height
     */
    @Override
    public void drawRect(int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
    }

    /**
     * Draw a centered circle.
     *
     * @param x x position
     * @param y y position
     * @param r diameter
     */
    @Override
    public void drawFilledCircle(double x, double y, double r) {
        // This new method does correct sub-pixel float coords.
        g2d.fill(new Ellipse2D.Double(x - r, y - r, r * 2, r * 2));
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        g2d.draw(new Ellipse2D.Double(x - r, y - r, r * 2, r * 2));
    }

    @Override
    public void drawFilledPolygon(int[] xPoints, int[] yPoints, int numPoints) {
        g2d.fillPolygon(xPoints, yPoints, numPoints);
    }

    @Override
    public void drawText(String str, int x, int y) {

        g.drawString(str, x, y);

    }

    /* TEXT ---------------------------------------------------------------*/

    @Override
    public Font getFont() {
        return g.getFont();
    }

    @Override
    public void setFont(int size) {
        String builtInFontName = "Verdana";
        if (!builtInFonts.containsKey(size)) {
            Font newFont = new Font(builtInFontName, Font.PLAIN, size);
            builtInFonts.put(size, newFont);
        }

        if (builtInFonts.containsKey(size)) {
            setFont(builtInFonts.get(size));
        }
    }

    @Override
    public void setFont(Font font) {
        g.setFont(font);
    }

    @Override
    public int[] getTextSize(String str) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        return new int[]{metrics.stringWidth(str), metrics.getAscent(), metrics.getDescent()};

    }

}
