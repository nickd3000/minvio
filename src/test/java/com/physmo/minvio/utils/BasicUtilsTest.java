package com.physmo.minvio.utils;

import com.physmo.minvio.types.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicUtilsTest {
    private static final double DELTA = 1e-10;

    @Test
    void mapperConvertsBetweenRangesAndHandlesZeroOutputSpan() {
        assertEquals(50.0, BasicUtils.mapper(5.0, 0.0, 10.0, 0.0, 100.0), DELTA);
        assertEquals(0.0, BasicUtils.mapper(5.0, 0.0, 10.0, 7.0, 7.0), DELTA);
        assertEquals(-1.0, BasicUtils.mapper(0.0, 0.0, 10.0, -1.0, 1.0), DELTA);
    }

    @Test
    void distanceUsesIntegerResult() {
        assertEquals(5, BasicUtils.distance(0, 0, 3, 4));
        assertEquals(1, BasicUtils.distance(0, 0, 1, 1));
    }

    @Test
    void ringOfPointsProducesExpectedCardinalPoints() {
        List<Point> points = new ArrayList<>();

        BasicUtils.ringOfPoints(4, 10, 20, 5, 0, points::add);

        assertPoint(points.get(0), 10, 25);
        assertPoint(points.get(1), 15, 20);
        assertPoint(points.get(2), 10, 15);
        assertPoint(points.get(3), 5, 20);
    }

    @Test
    void closestPointHonorsThresholdDistanceAndFirstTie() {
        List<Point> points = List.of(new Point(0, 0), new Point(2, 0), new Point(4, 0));

        assertEquals(1, BasicUtils.findClosestPointInList(points, new Point(2.1, 0), 1.0));
        assertEquals(0, BasicUtils.findClosestPointInList(points, new Point(1, 0), 2.0));
        assertEquals(-1, BasicUtils.findClosestPointInList(points, new Point(10, 0), 1.0));
        assertEquals(-1, BasicUtils.findClosestPointInList(List.of(), new Point(), 1.0));
    }

    @Test
    void pointListProcessorVisitsEveryPointInOrder() {
        List<Point> visited = new ArrayList<>();
        List<Point> source = List.of(new Point(1, 2), new Point(3, 4));

        BasicUtils.pointListProcessor(null, source, (dc, point) -> visited.add(point));

        assertEquals(source, visited);
    }

    @Test
    void randomPointAlwaysFallsWithinRequestedCircle() {
        for (int i = 0; i < 1_000; i++) {
            Point point = BasicUtils.createRandomPointInCircle(10, 20, 5);
            assertTrue(Point.distance(new Point(10, 20), point) <= 5.0);
        }
    }

    private static void assertPoint(Point point, double x, double y) {
        assertEquals(x, point.x, DELTA);
        assertEquals(y, point.y, DELTA);
    }
}
