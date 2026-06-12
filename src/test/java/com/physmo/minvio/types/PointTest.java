package com.physmo.minvio.types;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PointTest {

    private static final double DELTA = 1e-10;

    @Test
    void defaultConstructorInitializesPointAtOrigin() {
        Point point = new Point();

        assertEquals(0.0, point.x);
        assertEquals(0.0, point.y);
    }

    @Test
    void constructorInitializesCoordinates() {
        Point point = new Point(10.5, -5.5);

        assertEquals(10.5, point.x);
        assertEquals(-5.5, point.y);
    }

    @Test
    void copyConstructorCreatesDistinctCopy() {
        Point original = new Point(3.0, 4.0);

        Point copy = new Point(original);

        assertEquals(original.x, copy.x);
        assertEquals(original.y, copy.y);
        assertNotSame(original, copy);
    }

    @Test
    void copyConstructorRejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Point((Point) null));
    }

    @Test
    void addPointModifiesPointInPlace() {
        Point point = new Point(1.0, 2.0);

        point.add(new Point(3.0, 4.0));

        assertEquals(4.0, point.x);
        assertEquals(6.0, point.y);
    }

    @Test
    void addRejectsNullPoint() {
        Point point = new Point(1.0, 2.0);

        assertThrows(IllegalArgumentException.class, () -> point.add((Point) null));
    }

    @Test
    void addCoordinatesModifiesPointInPlace() {
        Point point = new Point(1.0, 2.0);

        point.add(5.5, -1.5);

        assertEquals(6.5, point.x);
        assertEquals(0.5, point.y);
    }

    @Test
    void multiplyScalesPointInPlace() {
        Point point = new Point(3.0, -4.0);

        point.multiply(2.5);

        assertEquals(7.5, point.x);
        assertEquals(-10.0, point.y);
    }

    @ParameterizedTest(name = "distance between {0} and {1} is {2}")
    @MethodSource("distanceCases")
    void staticDistanceIsCalculated(Point first, Point second, double expected) {
        assertEquals(expected, Point.distance(first, second), DELTA);
    }

    @Test
    void instanceDistanceIsCalculated() {
        Point first = new Point(3, 0);
        Point second = new Point(0, 4);

        assertEquals(5.0, first.distance(second), DELTA);
    }

    @Test
    void equalsAndHashCodeBehavior() {
        Point first = new Point(1.1, 2.2);
        Point equal = new Point(1.1, 2.2);
        Point different = new Point(3.3, 4.4);

        assertEquals(first, first);
        assertEquals(first, equal);
        assertEquals(equal, first);
        assertEquals(first.hashCode(), equal.hashCode());
        assertNotEquals(first, different);
        assertNotEquals(first.hashCode(), different.hashCode());
        assertNotEquals(null, first);
        assertNotEquals("a string", first);
    }

    @Test
    void toStringProducesFormattedString() {
        assertEquals("Point{x=1.23, y=5.68}", new Point(1.234, 5.678).toString());
    }

    private static Stream<Arguments> distanceCases() {
        return Stream.of(
                Arguments.of(new Point(0, 0), new Point(3, 4), 5.0),
                Arguments.of(new Point(1, 1), new Point(1, 1), 0.0),
                Arguments.of(new Point(-1, -1), new Point(1, 1), Math.sqrt(8.0))
        );
    }
}
