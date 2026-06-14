package com.physmo.minvio;

import com.physmo.minvio.types.Point;
import com.physmo.minvio.types.Rect;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Image;
import java.awt.Shape;
import java.awt.image.BufferedImage;

/**
 * Mutable immediate-mode drawing state backed by an image buffer.
 *
 * <p>Drawing operations affect the active buffer immediately. Coordinates use
 * the Java2D screen convention: the origin is at the top left, x increases to
 * the right, and y increases downward. Implementations are not required to be
 * thread-safe.</p>
 *
 * <p>Methods with default implementations that throw
 * {@link UnsupportedOperationException} are optional extension points for
 * third-party contexts. Callers can rely on them only when supported by the
 * concrete implementation.</p>
 */
public interface DrawingContext {
    /**
     * Clear the display to the supplied color.
     *
     * @param c AWT Color object to clear the display to.
     */
    void cls(Color c);

    /**
     * Clears the complete active buffer using the current background color
     * while preserving the current draw color.
     */
    void cls();


    /**
     * Replaces the image that receives subsequent drawing.
     *
     * <p>The context retains and draws directly into the supplied mutable
     * image; it does not copy or take exclusive ownership of it. Concrete
     * implementations define which drawing state survives replacement.</p>
     *
     * @param image non-null image buffer to retain
     * @throws NullPointerException if {@code image} is {@code null}
     */
    void setImageBuffer(BufferedImage image);

    /**
     * Set the color to use for drawing operations.
     *
     * @param newCol The color that future draw operations will use.
     * @return The previous color.
     */
    Color setDrawColor(Color newCol);

    /**
     * Set the background color to use for drawing operations.
     *
     * @param newCol The color that future draw operations will use.
     * @return The previous color.
     */
    Color setBackgroundColor(Color newCol);

    /**
     * Draw an image to the display.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     */
    void drawImage(BufferedImage sourceImage, int x, int y);

    /**
     * Draw an image to the display.
     * Coordinates are cast to integers.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     */
    default void drawImage(BufferedImage sourceImage, double x, double y) {
        drawImage(sourceImage, (int) x, (int) y);
    }

    /**
     * Draw an image to the display.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param w           width
     * @param h           height
     */
    void drawImage(BufferedImage sourceImage, int x, int y, int w, int h);

    /**
     * Draw an image to the display.
     * Coordinates are cast to integers.
     *
     * @param sourceImage Source image as a Buffered Image
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param w           width
     * @param h           height
     */
    default void drawImage(BufferedImage sourceImage, double x, double y, double w, double h) {
        drawImage(sourceImage, (int) x, (int) y, (int) w, (int) h);
    }

    /**
     * Get the colour at the defined position.
     *
     * @param pos Position
     * @return Color value
     */
    default Color getColorAtPoint(Point pos) {
        return getColorAtPoint((int) pos.x, (int) pos.y);
    }

    /**
     * Get the colour at the defined position.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Color value
     */
    default Color getColorAtPoint(int x, int y) {
        int rgb = this.getRGBAtPoint(x, y);
        return new Color(rgb);
    }

    /**
     * Get the colour at the defined position.
     * Coordinates are cast to integers.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Color value
     */
    default Color getColorAtPoint(double x, double y) {
        return getColorAtPoint((int) x, (int) y);
    }

    /* LINE ---------------------------------------------------------------*/

    /**
     * Get the color in RGB packed integer format at the defined position.
     * <p>
     * Format in hex: 0xAARRGGBB
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return integer RGB value
     */
    int getRGBAtPoint(int x, int y);

    /**
     * Get the color in RGB packed integer format at the defined position.
     * <p>
     * Format in hex: 0xAARRGGBB
     * Coordinates are cast to integers.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return integer RGB value
     */
    default int getRGBAtPoint(double x, double y) {
        return getRGBAtPoint((int) x, (int) y);
    }

    /**
     * Drawing function - Draw a pixel using current draw color.
     *
     * @param pos position
     */
    default void drawPoint(Point pos) {
        drawPoint((int) pos.x, (int) pos.y);
    }

    /**
     * Drawing function - Draw a pixel using current draw color.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    void drawPoint(int x, int y);

    /**
     * Drawing function - Draw a pixel using current draw color.
     * Coordinates are cast to integers.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    default void drawPoint(double x, double y) {
        drawPoint((int) x, (int) y);
    }

    /**
     * Drawing function - draw a line
     *
     * @param x1 x-coordinate (start)
     * @param y1 y-coordinate (start)
     * @param x2 x-coordinate (end)
     * @param y2 y-coordinate (end)
     */
    default void drawLine(double x1, double y1, double x2, double y2) {
        drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    /* RECT ---------------------------------------------------------------*/

    /**
     * Drawing function - draw a line
     *
     * @param x1 x-coordinate (start)
     * @param y1 y-coordinate (start)
     * @param x2 x-coordinate (end)
     * @param y2 y-coordinate (end)
     */
    void drawLine(int x1, int y1, int x2, int y2);

    /**
     * Drawing function - draw a line
     *
     * @param pos1 start Point
     * @param pos2 end Point
     */
    default void drawLine(Point pos1, Point pos2) {
        drawLine((int) pos1.x, (int) pos1.y, (int) pos2.x, (int) pos2.y);
    }

    /* POLYGON ---------------------------------------------------------------*/

    /**
     * Drawing function - draw a line
     *
     * @param pos1      start Point
     * @param pos2      end Point
     * @param thickness line thickness
     */
    default void drawLine(Point pos1, Point pos2, double thickness) {
        drawLine(pos1.x, pos1.y, pos2.x, pos2.y, thickness);
    }

    /**
     * Drawing function - draw a line
     *
     * @param x1        x-coordinate (start)
     * @param y1        y-coordinate (start)
     * @param x2        x-coordinate (end)
     * @param y2        y-coordinate (end)
     * @param thickness line thickness
     */
    void drawLine(double x1, double y1, double x2, double y2, double thickness);

    /* CIRCLE ---------------------------------------------------------------*/

    /**
     * Drawing function - draw a filled rectangle
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    void drawFilledRect(int x, int y, int width, int height);

    /**
     * Drawing function - draw a filled rectangle
     * Coordinates are cast to integers.
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    default void drawFilledRect(double x, double y, double width, double height) {
        drawFilledRect((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Draws a filled rectangle using the supplied bounds.
     *
     * <p>The default implementation delegates to the double-coordinate
     * overload, which ultimately truncates coordinates and dimensions when
     * using the standard context.</p>
     *
     * @param rect rectangle bounds
     */
    default void drawFilledRect(Rect rect) {
        this.drawFilledRect(rect.x, rect.y, rect.w, rect.h);
    }

    /**
     * Drawing function - draw an unfilled rectangle
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    void drawRect(int x, int y, int width, int height);

    /**
     * Drawing function - draw an unfilled rectangle
     * Coordinates are cast to integers.
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  width
     * @param height height
     */
    default void drawRect(double x, double y, double width, double height) {
        drawRect((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Draws a rectangle outline using the supplied bounds.
     *
     * <p>The default implementation delegates to the double-coordinate
     * overload, which ultimately truncates coordinates and dimensions when
     * using the standard context.</p>
     *
     * @param rect rectangle bounds
     */
    default void drawRect(Rect rect) {
        drawRect(rect.x, rect.y, rect.w, rect.h);
    }

    /**
     * Drawing function - draw a filled polygon
     *
     * @param xPoints   Array of x-coordinate
     * @param yPoints   Array of y-coordinate
     * @param numPoints number of points
     */
    void drawFilledPolygon(int[] xPoints, int[] yPoints, int numPoints);

    /**
     * Draw a polygon outline.
     *
     * @param xPoints   array of x-coordinates
     * @param yPoints   array of y-coordinates
     * @param numPoints number of points to use
     */
    default void drawPolygon(int[] xPoints, int[] yPoints, int numPoints) {
        throw unsupported("Polygon outlines");
    }

    /**
     * Draw connected line segments without closing the final segment.
     *
     * @param xPoints   array of x-coordinates
     * @param yPoints   array of y-coordinates
     * @param numPoints number of points to use
     */
    default void drawPolyline(int[] xPoints, int[] yPoints, int numPoints) {
        throw unsupported("Polylines");
    }

    /**
     * Draw an ellipse inside the supplied bounding box.
     *
     * @param x      bounding-box x-coordinate
     * @param y      bounding-box y-coordinate
     * @param width  bounding-box width
     * @param height bounding-box height
     */
    default void drawEllipse(double x, double y, double width, double height) {
        throw unsupported("Ellipses");
    }

    /**
     * Draw a filled ellipse inside the supplied bounding box.
     *
     * @param x      bounding-box x-coordinate
     * @param y      bounding-box y-coordinate
     * @param width  bounding-box width
     * @param height bounding-box height
     */
    default void drawFilledEllipse(double x, double y, double width, double height) {
        throw unsupported("Filled ellipses");
    }

    /**
     * Draw a triangle outline.
     *
     * @param x1 first vertex x-coordinate
     * @param y1 first vertex y-coordinate
     * @param x2 second vertex x-coordinate
     * @param y2 second vertex y-coordinate
     * @param x3 third vertex x-coordinate
     * @param y3 third vertex y-coordinate
     */
    default void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        throw unsupported("Triangles");
    }

    /**
     * Draw a filled triangle.
     *
     * @param x1 first vertex x-coordinate
     * @param y1 first vertex y-coordinate
     * @param x2 second vertex x-coordinate
     * @param y2 second vertex y-coordinate
     * @param x3 third vertex x-coordinate
     * @param y3 third vertex y-coordinate
     */
    default void drawFilledTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        throw unsupported("Filled triangles");
    }

    /**
     * Draw an open arc inside the supplied bounding box. Angles are measured in
     * radians from the positive x-axis, with positive angles following the same
     * screen-coordinate rotation direction as {@link #rotate(double)}.
     *
     * @param x          bounding-box x-coordinate
     * @param y          bounding-box y-coordinate
     * @param width      bounding-box width
     * @param height     bounding-box height
     * @param startAngle start angle in radians
     * @param arcAngle   angular extent in radians
     */
    default void drawArc(
            double x, double y, double width, double height, double startAngle, double arcAngle) {
        throw unsupported("Arcs");
    }

    /**
     * Draw an arbitrary Java2D shape.
     *
     * @param shape shape to draw
     */
    default void drawShape(Shape shape) {
        throw unsupported("Java2D shapes");
    }

    /**
     * Fill an arbitrary Java2D shape.
     *
     * @param shape shape to fill
     */
    default void drawFilledShape(Shape shape) {
        throw unsupported("Filled Java2D shapes");
    }


    /**
     * Drawing function - draw an unfilled circle
     *
     * @param pos Position
     * @param r   radius
     */
    default void drawCircle(Point pos, double r) {
        drawCircle(pos.x, pos.y, r);
    }

    /**
     * Drawing function - draw a filled circle
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param r radius
     */
    void drawCircle(double x, double y, double r);

    /**
     * Drawing function - draw a filled circle
     *
     * @param pos Position
     * @param r   radius
     */
    default void drawFilledCircle(Point pos, double r) {
        drawFilledCircle(pos.x, pos.y, r);
    }

    /**
     * Drawing function - draw a filled circle
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param r radius
     */
    void drawFilledCircle(double x, double y, double r);

    /**
     * Draw the supplied string using the active font.
     *
     * @param str text to draw
     * @param x   x-coordinate
     * @param y   y-coordinate
     */
    void drawText(String str, int x, int y);

    /**
     * Draw the supplied string using the active font.
     * Coordinates are cast to integers.
     *
     * @param str text to draw
     * @param x   x-coordinate
     * @param y   y-coordinate
     */
    default void drawText(String str, double x, double y) {
        drawText(str, (int) x, (int) y);
    }

    /**
     * Set current font to the specified font.
     * Example:
     * Font font = new Font("Verdana", Font.PLAIN, 10);
     *
     * @param font the user supplied font
     */
    void setFont(Font font);

    /**
     * Obtain the current font used by BasicDisplay
     *
     * @return the current font.
     */
    Font getFont();

    /**
     * Set current font to the built-in font at the specified size.
     *
     * @param size font size
     */
    void setFont(int size);

    /**
     * Retrieve font metrics of the supplied string in an int array
     * This is useful for accurate text layout.
     * <p>
     * 3 values representing the width, ascent and descent values of the font
     * The provided index variables can be used to access them:
     * TEXT_SIZE_WIDTH
     * TEXT_SIZE_ASCENT
     * TEXT_SIZE_DESCENT
     *
     * @param str string to retrieve metrics from
     * @return an integer array containing various measurements.
     */
    int[] getTextSize(String str);


    /**
     * Returns the color used by subsequent drawing operations.
     *
     * @return current draw color
     */
    Color getDrawColor();

    /**
     * Returns the color used by {@link #cls()}.
     *
     * @return current background color
     */
    Color getBackgroundColor();

    /**
     * Set the width used for subsequent outline drawing operations.
     *
     * @param width positive finite stroke width
     * @return the previous stroke width
     */
    default double setStrokeWidth(double width) {
        throw unsupported("Stroke width");
    }

    /**
     * Return the width of the current stroke.
     *
     * @return current stroke width
     */
    default double getStrokeWidth() {
        throw unsupported("Stroke width");
    }

    /**
     * Set the alpha used for subsequent drawing operations.
     *
     * @param alpha alpha value from 0.0 (transparent) to 1.0 (opaque)
     */
    default void setAlpha(double alpha) {
        throw unsupported("Alpha");
    }

    /**
     * Return the alpha of the current composite.
     *
     * @return current alpha value
     */
    default double getAlpha() {
        throw unsupported("Alpha");
    }

    /**
     * Set the Java2D composite used for subsequent drawing operations.
     *
     * @param composite composite to use
     * @return the previous composite
     */
    default Composite setComposite(Composite composite) {
        throw unsupported("Composites");
    }

    /**
     * Return the current Java2D composite.
     *
     * @return current composite
     */
    default Composite getComposite() {
        throw unsupported("Composites");
    }

    /**
     * Replace the current clip with the supplied rectangular clip.
     *
     * @param x      clip x-coordinate
     * @param y      clip y-coordinate
     * @param width  clip width
     * @param height clip height
     */
    default void setClip(double x, double y, double width, double height) {
        throw unsupported("Clipping");
    }

    /**
     * Clear the current clip.
     */
    default void clearClip() {
        throw unsupported("Clipping");
    }

    /**
     * Return a copy of the current clip, or {@code null} when clipping is disabled.
     *
     * @return current clip, or {@code null}
     */
    default Shape getClip() {
        throw unsupported("Clipping");
    }

    /**
     * Save color, background color, font, stroke, composite, and clip state.
     */
    default void pushStyle() {
        throw unsupported("Style stack");
    }

    /**
     * Restore the most recently saved style state.
     *
     * @throws IllegalStateException when no style state has been saved
     */
    default void popStyle() {
        throw unsupported("Style stack");
    }

    /**
     * Get the draw buffer for the display as an Image object.
     *
     * @return Image representing the draw buffer.
     */
    Image getDrawBuffer();

    /**
     * Returns the width of the active image buffer in pixels.
     *
     * @return active buffer width
     */
    int getWidth();

    /**
     * Returns the height of the active image buffer in pixels.
     *
     * @return active buffer height
     */
    int getHeight();

    /**
     * Saves the current transformation state onto a stack.
     */
    void pushMatrix();

    /**
     * Restores the last saved transformation state from the stack.
     */
    void popMatrix();

    /**
     * Moves the origin of the coordinate system.
     *
     * @param x The distance to move along the x-axis.
     * @param y The distance to move along the y-axis.
     */
    void translate(double x, double y);

    /**
     * Rotates the coordinate system.
     *
     * @param angle The angle of rotation in radians.
     */
    void rotate(double angle);

    /**
     * Scales the coordinate system uniformly.
     *
     * @param s The scale factor.
     */
    void scale(double s);

    /**
     * Scales the coordinate system non-uniformly.
     *
     * @param x The scale factor along the x-axis.
     * @param y The scale factor along the y-axis.
     */
    void scale(double x, double y);

    private static UnsupportedOperationException unsupported(String feature) {
        return new UnsupportedOperationException(feature + " are not supported by this drawing context");
    }
}
