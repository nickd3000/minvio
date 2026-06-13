package com.physmo.minvio;

import org.junit.jupiter.api.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private DrawingContextAwt context() {
        return new DrawingContextAwt(new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB));
    }
}
