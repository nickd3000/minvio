package com.physmo.minvio;

import java.awt.BasicStroke;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The DrawingContextAwt class is an implementation of the DrawingContext interface
 * that provides drawing capabilities using the AWT (Abstract Window Toolkit) graphics framework.
 * It allows for drawing shapes, text, and images on a BufferedImage. This class supports both
 * basic drawing operations and advanced rendering features such as antialiasing.
 */
public class DrawingContextAwt implements DrawingContext {

    BufferedImage buffer;
    Graphics g;
    Graphics2D g2d;
    int width = 0;
    int height = 0;
    private Color drawColor;
    private Color backgroundColor;
    final Map<Integer, Font> builtInFonts = new HashMap<>();
    private final Deque<AffineTransform> transformStack = new ArrayDeque<>();
    private final Deque<StyleState> styleStack = new ArrayDeque<>();

    /**
     * Creates a Java2D context that draws directly into the supplied image.
     *
     * <p>The image remains caller-accessible and is not copied. This context
     * owns and manages the {@link Graphics2D} instances it creates for the
     * image, but it does not dispose of or otherwise own the image itself.</p>
     *
     * @param buffer non-null image that receives drawing
     * @throws NullPointerException if {@code buffer} is {@code null}
     */
    public DrawingContextAwt(BufferedImage buffer) {
        setImageBuffer(buffer);
    }

    @Override
    public void setImageBuffer(BufferedImage image) {
        Objects.requireNonNull(image, "Image buffer cannot be null");

        Color previousColor = null;
        Font previousFont = null;
        Stroke previousStroke = null;
        AffineTransform previousTransform = null;
        Shape previousClip = null;
        Composite previousComposite = null;
        Paint previousPaint = null;
        Color previousGraphicsBackground = null;
        RenderingHints previousRenderingHints = null;

        if (g2d != null) {
            previousColor = g2d.getColor();
            previousFont = g2d.getFont();
            previousStroke = g2d.getStroke();
            previousTransform = g2d.getTransform();
            previousClip = g2d.getClip();
            previousComposite = g2d.getComposite();
            previousPaint = g2d.getPaint();
            previousGraphicsBackground = g2d.getBackground();
            previousRenderingHints = g2d.getRenderingHints();
            g2d.dispose();
        }

        this.buffer = image;
        g2d = buffer.createGraphics();
        g = g2d;

        if (previousRenderingHints == null) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setFont(new Font("window", Font.BOLD, 20));
        } else {
            g2d.setRenderingHints(previousRenderingHints);
            g2d.setColor(previousColor);
            g2d.setFont(previousFont);
            g2d.setStroke(previousStroke);
            g2d.setTransform(previousTransform);
            g2d.setClip(previousClip);
            g2d.setComposite(previousComposite);
            g2d.setPaint(previousPaint);
            g2d.setBackground(previousGraphicsBackground);
        }

        width = buffer.getWidth();
        height = buffer.getHeight();
    }

    /**
     * Clears the drawing area by filling it with the current background color.
     * <p>
     * The method temporarily sets the drawing color to the background color,
     * fills the entire drawing area with a rectangle of the background color,
     * and restores the original drawing color afterward.
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
        validatePositiveFinite(thickness, "Line thickness");
        Stroke previousStroke = g2d.getStroke();
        try {
            g2d.setStroke(new BasicStroke((float) thickness));
            g2d.draw(new java.awt.geom.Line2D.Double(x1, y1, x2, y2));
        } finally {
            g2d.setStroke(previousStroke);
        }
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
        validatePolygon(xPoints, yPoints, numPoints);
        g2d.fillPolygon(xPoints, yPoints, numPoints);
    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int numPoints) {
        validatePolygon(xPoints, yPoints, numPoints);
        g2d.drawPolygon(xPoints, yPoints, numPoints);
    }

    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int numPoints) {
        validatePolygon(xPoints, yPoints, numPoints);
        g2d.drawPolyline(xPoints, yPoints, numPoints);
    }

    @Override
    public void drawEllipse(double x, double y, double width, double height) {
        validateBounds(width, height);
        g2d.draw(new Ellipse2D.Double(x, y, width, height));
    }

    @Override
    public void drawFilledEllipse(double x, double y, double width, double height) {
        validateBounds(width, height);
        g2d.fill(new Ellipse2D.Double(x, y, width, height));
    }

    @Override
    public void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        g2d.draw(createTriangle(x1, y1, x2, y2, x3, y3));
    }

    @Override
    public void drawFilledTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        g2d.fill(createTriangle(x1, y1, x2, y2, x3, y3));
    }

    @Override
    public void drawArc(double x, double y, double width, double height, double startAngle, double arcAngle) {
        validateBounds(width, height);
        validateFinite(startAngle, "Start angle");
        validateFinite(arcAngle, "Arc angle");
        g2d.draw(new Arc2D.Double(
                x,
                y,
                width,
                height,
                -Math.toDegrees(startAngle),
                -Math.toDegrees(arcAngle),
                Arc2D.OPEN));
    }

    @Override
    public void drawShape(Shape shape) {
        g2d.draw(Objects.requireNonNull(shape, "Shape cannot be null"));
    }

    @Override
    public void drawFilledShape(Shape shape) {
        g2d.fill(Objects.requireNonNull(shape, "Shape cannot be null"));
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

    @Override
    public double setStrokeWidth(double width) {
        validatePositiveFinite(width, "Stroke width");
        double previousWidth = getStrokeWidth();
        g2d.setStroke(new BasicStroke((float) width));
        return previousWidth;
    }

    @Override
    public double getStrokeWidth() {
        if (g2d.getStroke() instanceof BasicStroke basicStroke) {
            return basicStroke.getLineWidth();
        }
        throw new IllegalStateException("Current stroke does not expose a BasicStroke width");
    }

    @Override
    public void setAlpha(double alpha) {
        if (!Double.isFinite(alpha) || alpha < 0.0 || alpha > 1.0) {
            throw new IllegalArgumentException("Alpha must be finite and between 0.0 and 1.0");
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
    }

    @Override
    public double getAlpha() {
        if (g2d.getComposite() instanceof AlphaComposite alphaComposite) {
            return alphaComposite.getAlpha();
        }
        throw new IllegalStateException("Current composite does not expose an alpha value");
    }

    @Override
    public Composite setComposite(Composite composite) {
        Objects.requireNonNull(composite, "Composite cannot be null");
        Composite previousComposite = g2d.getComposite();
        g2d.setComposite(composite);
        return previousComposite;
    }

    @Override
    public Composite getComposite() {
        return g2d.getComposite();
    }

    @Override
    public void setClip(double x, double y, double width, double height) {
        validateBounds(width, height);
        g2d.setClip(new Rectangle2D.Double(x, y, width, height));
    }

    @Override
    public void clearClip() {
        g2d.setClip(null);
    }

    @Override
    public Shape getClip() {
        return copyShape(g2d.getClip());
    }

    @Override
    public void pushStyle() {
        styleStack.push(new StyleState(
                getDrawColor(),
                backgroundColor,
                getFont(),
                g2d.getStroke(),
                g2d.getComposite(),
                copyShape(g2d.getClip())));
    }

    @Override
    public void popStyle() {
        if (styleStack.isEmpty()) {
            throw new IllegalStateException("Style stack is empty");
        }

        StyleState style = styleStack.pop();
        setDrawColor(style.drawColor());
        setBackgroundColor(style.backgroundColor());
        setFont(style.font());
        g2d.setStroke(style.stroke());
        g2d.setComposite(style.composite());
        g2d.setClip(copyShape(style.clip()));
    }

    @Override
    public void pushMatrix() {
        transformStack.push(g2d.getTransform());
    }

    @Override
    public void popMatrix() {
        if (!transformStack.isEmpty()) {
            g2d.setTransform(transformStack.pop());
        }
    }

    @Override
    public void translate(double x, double y) {
        g2d.translate(x, y);
    }

    @Override
    public void rotate(double angle) {
        g2d.rotate(angle);
    }

    @Override
    public void scale(double s) {
        g2d.scale(s, s);
    }

    @Override
    public void scale(double x, double y) {
        g2d.scale(x, y);
    }

    private static Path2D createTriangle(
            double x1, double y1, double x2, double y2, double x3, double y3) {
        Path2D path = new Path2D.Double();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.closePath();
        return path;
    }

    private static void validatePolygon(int[] xPoints, int[] yPoints, int numPoints) {
        Objects.requireNonNull(xPoints, "X-points cannot be null");
        Objects.requireNonNull(yPoints, "Y-points cannot be null");
        if (numPoints < 0 || numPoints > xPoints.length || numPoints > yPoints.length) {
            throw new IllegalArgumentException("Point count must fit both coordinate arrays");
        }
    }

    private static void validateBounds(double width, double height) {
        if (!Double.isFinite(width) || !Double.isFinite(height) || width < 0.0 || height < 0.0) {
            throw new IllegalArgumentException("Width and height must be finite and non-negative");
        }
    }

    private static void validatePositiveFinite(double value, String name) {
        if (!Double.isFinite(value) || value <= 0.0) {
            throw new IllegalArgumentException(name + " must be finite and greater than zero");
        }
    }

    private static void validateFinite(double value, String name) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
    }

    private static Shape copyShape(Shape shape) {
        return shape == null ? null : new Path2D.Double(shape);
    }

    private record StyleState(
            Color drawColor,
            Color backgroundColor,
            Font font,
            Stroke stroke,
            Composite composite,
            Shape clip) {
    }
}
