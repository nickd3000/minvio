package com.physmo.minvio;

import com.physmo.minvio.types.Point;
import com.physmo.minvio.types.Rect;

import java.awt.geom.Rectangle2D;

final class DrawingContextTestSupport {

    private DrawingContextTestSupport() {
    }

    static void exerciseAll(DrawingContext drawingContext) {
        exerciseLines(drawingContext);
        exerciseShapes(drawingContext);
    }

    private static void exerciseLines(DrawingContext drawingContext) {
        drawingContext.drawLine(new Point(10, 10), new Point(20, 20));
        drawingContext.drawLine(10, 10, 20, 20);
        drawingContext.drawLine(new Point(10, 10), new Point(20, 20), 5);
        drawingContext.drawLine(1.0, 1.0, 20.0, 20.0);
        drawingContext.drawLine(1.0, 1.0, 20.0, 20.0, 6);
    }

    private static void exerciseShapes(DrawingContext drawingContext) {
        drawingContext.drawCircle(1, 1, 1);
        drawingContext.drawCircle(new Point(1, 1), 1);
        drawingContext.drawFilledCircle(1, 1, 1);
        drawingContext.drawFilledCircle(new Point(1, 1), 1);

        drawingContext.drawPoint(1, 1);
        drawingContext.drawPoint(new Point(1, 1));

        drawingContext.drawRect(new Rect(1, 1, 10, 10));
        drawingContext.drawRect(1, 1, 10, 10);
        drawingContext.drawFilledRect(new Rect(1, 1, 10, 10));
        drawingContext.drawFilledRect(1, 1, 10, 10);

        int[] xPoints = {1, 10, 5};
        int[] yPoints = {1, 1, 10};
        drawingContext.drawPolygon(xPoints, yPoints, 3);
        drawingContext.drawFilledPolygon(xPoints, yPoints, 3);
        drawingContext.drawPolyline(xPoints, yPoints, 3);

        drawingContext.drawEllipse(1, 1, 10, 5);
        drawingContext.drawFilledEllipse(1, 1, 10, 5);
        drawingContext.drawTriangle(1, 1, 10, 1, 5, 10);
        drawingContext.drawFilledTriangle(1, 1, 10, 1, 5, 10);
        drawingContext.drawArc(1, 1, 10, 10, 0, Math.PI);
        drawingContext.drawShape(new Rectangle2D.Double(1, 1, 10, 10));
        drawingContext.drawFilledShape(new Rectangle2D.Double(1, 1, 10, 10));

        drawingContext.setStrokeWidth(2);
        drawingContext.getStrokeWidth();
        drawingContext.setAlpha(0.5);
        drawingContext.getAlpha();
        drawingContext.getComposite();
        drawingContext.setComposite(drawingContext.getComposite());
        drawingContext.setClip(0, 0, 10, 10);
        drawingContext.getClip();
        drawingContext.clearClip();
        drawingContext.pushStyle();
        drawingContext.popStyle();
    }
}
