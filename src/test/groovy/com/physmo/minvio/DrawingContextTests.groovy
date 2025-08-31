package com.physmo.minvio

import com.physmo.minvio.types.Point
import com.physmo.minvio.types.Rect

class DrawingContextTests {

    static def testAll(DrawingContext dc) {
        testLine(dc)
        testShapes(dc)
    }

    static def testLine(DrawingContext dc) {
        dc.drawLine(new Point(10, 10), new Point(20, 20))
        dc.drawLine(10, 10, 20, 20)
        dc.drawLine(new Point(10, 10), new Point(20, 20), 5)
        dc.drawLine(1.0, 1.0, 20.0, 20.0)
        dc.drawLine(1.0, 1.0, 20.0, 20.0, 6)
    }

    static def testShapes(DrawingContext dc) {
        // Circle
        dc.drawCircle(1, 1, 1)
        dc.drawCircle(new Point(1, 1), 1)
        dc.drawFilledCircle(1, 1, 1)
        dc.drawFilledCircle(new Point(1, 1), 1)

        // Point
        dc.drawPoint(1, 1)
        dc.drawPoint(new Point(1, 1))

        // Rect
        dc.drawRect(new Rect(1, 1, 10, 10))
        dc.drawRect(1, 1, 10, 10)
        dc.drawFilledRect(new Rect(1, 1, 10, 10))
        dc.drawFilledRect(1, 1, 10, 10)
    }

}
