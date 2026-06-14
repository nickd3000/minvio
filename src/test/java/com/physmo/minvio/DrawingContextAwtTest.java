package com.physmo.minvio;

import org.junit.jupiter.api.Test;

import java.awt.BasicStroke;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DrawingContextAwtTest {

    @Test
    void thickLineRestoresPreviousStroke() {
        DrawingContextAwt context = context();
        Stroke originalStroke = new BasicStroke(2.0f);
        context.g2d.setStroke(originalStroke);

        context.drawLine(1, 1, 10, 10, 6);

        assertEquals(originalStroke, context.g2d.getStroke());
    }

    @Test
    void replacingBufferPreservesDrawingState() {
        DrawingContextAwt context = context();
        Font font = new Font("Dialog", Font.ITALIC, 17);
        BasicStroke stroke = new BasicStroke(3.0f);
        AffineTransform transform = AffineTransform.getTranslateInstance(4, 5);

        context.setDrawColor(Color.MAGENTA);
        context.setBackgroundColor(Color.BLUE);
        context.setFont(font);
        context.g2d.setStroke(stroke);
        context.g2d.setTransform(transform);
        context.g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        var previousGraphics = context.g2d;

        context.setImageBuffer(new BufferedImage(30, 40, BufferedImage.TYPE_INT_ARGB));

        assertNotSame(previousGraphics, context.g2d);
        assertEquals(Color.MAGENTA, context.getDrawColor());
        assertEquals(Color.BLUE, context.getBackgroundColor());
        assertEquals(font, context.getFont());
        assertEquals(stroke, context.g2d.getStroke());
        assertEquals(transform, context.g2d.getTransform());
        assertEquals(
                RenderingHints.VALUE_INTERPOLATION_BILINEAR,
                context.g2d.getRenderingHint(RenderingHints.KEY_INTERPOLATION));
        assertEquals(30, context.getWidth());
        assertEquals(40, context.getHeight());
    }

    @Test
    void replacingBufferKeepsTransformStackUsable() {
        DrawingContextAwt context = context();
        AffineTransform originalTransform = context.g2d.getTransform();
        context.pushMatrix();
        context.translate(10, 20);

        context.setImageBuffer(new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB));
        context.popMatrix();

        assertEquals(originalTransform, context.g2d.getTransform());
    }

    @Test
    void rejectsNullBuffer() {
        assertThrows(NullPointerException.class, () -> context().setImageBuffer(null));
    }

    @Test
    void drawsNewFilledPrimitivesAndJava2dShapes() {
        DrawingContextAwt context = context();
        context.g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        context.setDrawColor(Color.RED);

        context.drawFilledEllipse(1, 1, 8, 6);
        context.drawFilledTriangle(10, 1, 18, 1, 14, 8);
        context.drawFilledShape(new Rectangle2D.Double(1, 10, 8, 8));

        assertEquals(Color.RED.getRGB(), context.buffer.getRGB(5, 4));
        assertEquals(Color.RED.getRGB(), context.buffer.getRGB(14, 3));
        assertEquals(Color.RED.getRGB(), context.buffer.getRGB(4, 14));
    }

    @Test
    void drawsPolygonPolylineArcAndShapeOutlines() {
        DrawingContextAwt context = context();
        context.g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        context.setDrawColor(Color.GREEN);
        int[] xPoints = {1, 8, 8};
        int[] yPoints = {1, 1, 8};

        context.drawPolygon(xPoints, yPoints, 3);
        context.drawPolyline(new int[]{10, 18, 18}, new int[]{1, 1, 8}, 3);
        context.drawArc(1, 10, 8, 8, 0, Math.PI / 2);
        context.drawShape(new Rectangle2D.Double(10, 10, 8, 8));

        assertEquals(Color.GREEN.getRGB(), context.buffer.getRGB(4, 1));
        assertEquals(Color.GREEN.getRGB(), context.buffer.getRGB(14, 1));
        assertEquals(Color.GREEN.getRGB(), context.buffer.getRGB(18, 14));
        assertEquals(Color.GREEN.getRGB(), context.buffer.getRGB(10, 14));
    }

    @Test
    void clipRestrictsDrawingAndCanBeCleared() {
        DrawingContextAwt context = context();
        context.g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        context.setDrawColor(Color.BLUE);

        context.setClip(0, 0, 5, 5);
        assertNotNull(context.getClip());
        context.drawFilledRect(0, 0, 20, 20);

        assertEquals(Color.BLUE.getRGB(), context.buffer.getRGB(2, 2));
        assertNotEquals(Color.BLUE.getRGB(), context.buffer.getRGB(10, 10));

        context.clearClip();
        assertNull(context.getClip());
        context.drawFilledRect(10, 10, 2, 2);
        assertEquals(Color.BLUE.getRGB(), context.buffer.getRGB(10, 10));
    }

    @Test
    void styleStackRestoresAllSupportedStyleState() {
        DrawingContextAwt context = context();
        Font originalFont = new Font("Dialog", Font.PLAIN, 12);
        context.setDrawColor(Color.RED);
        context.setBackgroundColor(Color.BLACK);
        context.setFont(originalFont);
        context.setStrokeWidth(2);
        context.setAlpha(0.75);
        context.setClip(1, 2, 10, 11);
        Shape originalClip = context.getClip();

        context.pushStyle();
        context.setDrawColor(Color.BLUE);
        context.setBackgroundColor(Color.WHITE);
        context.setFont(new Font("Dialog", Font.BOLD, 18));
        context.setStrokeWidth(5);
        context.setAlpha(0.25);
        context.clearClip();
        context.popStyle();

        assertEquals(Color.RED, context.getDrawColor());
        assertEquals(Color.BLACK, context.getBackgroundColor());
        assertEquals(originalFont, context.getFont());
        assertEquals(2.0, context.getStrokeWidth());
        assertEquals(0.75, context.getAlpha(), 0.0001);
        assertEquals(originalClip.getBounds2D(), context.getClip().getBounds2D());
    }

    @Test
    void styleStackIsIndependentFromTransformStack() {
        DrawingContextAwt context = context();
        AffineTransform originalTransform = context.g2d.getTransform();
        context.setDrawColor(Color.RED);

        context.pushStyle();
        context.pushMatrix();
        context.setDrawColor(Color.BLUE);
        context.translate(4, 5);
        context.popStyle();

        assertEquals(Color.RED, context.getDrawColor());
        assertNotEquals(originalTransform, context.g2d.getTransform());

        context.popMatrix();
        assertEquals(originalTransform, context.g2d.getTransform());
    }

    @Test
    void styleStackSurvivesBufferReplacement() {
        DrawingContextAwt context = context();
        context.setDrawColor(Color.RED);
        context.setStrokeWidth(3);
        context.pushStyle();
        context.setDrawColor(Color.BLUE);
        context.setStrokeWidth(6);

        context.setImageBuffer(new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB));
        context.popStyle();

        assertEquals(Color.RED, context.getDrawColor());
        assertEquals(3.0, context.getStrokeWidth());
    }

    @Test
    void rejectsInvalidDrawingStateAndGeometry() {
        DrawingContextAwt context = context();

        assertThrows(IllegalArgumentException.class, () -> context.setStrokeWidth(0));
        assertThrows(IllegalArgumentException.class, () -> context.setStrokeWidth(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> context.setAlpha(-0.1));
        assertThrows(IllegalArgumentException.class, () -> context.setAlpha(1.1));
        assertThrows(NullPointerException.class, () -> context.setComposite(null));
        assertThrows(NullPointerException.class, () -> context.drawShape(null));
        assertThrows(NullPointerException.class, () -> context.drawFilledShape(null));
        assertThrows(IllegalArgumentException.class, () -> context.drawEllipse(0, 0, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> context.drawArc(0, 0, 1, 1, 0, Double.NaN));
        assertThrows(
                IllegalArgumentException.class,
                () -> context.drawPolygon(new int[]{1}, new int[]{1}, 2));
        assertThrows(IllegalStateException.class, context::popStyle);
    }

    @Test
    void exposesCompositeAndAlphaState() {
        DrawingContextAwt context = context();
        assertEquals(1.0, context.getAlpha());

        context.setAlpha(0.4);
        assertEquals(0.4, context.getAlpha(), 0.0001);

        assertInstanceOf(AlphaComposite.class, context.setComposite(AlphaComposite.Clear));
        assertEquals(AlphaComposite.Clear, context.getComposite());
    }

    private DrawingContextAwt context() {
        return new DrawingContextAwt(new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB));
    }
}
