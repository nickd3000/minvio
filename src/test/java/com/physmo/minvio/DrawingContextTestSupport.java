package com.physmo.minvio;

import com.physmo.minvio.types.Point;
import com.physmo.minvio.types.Rect;

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
    }
}
