package com.physmo.minvio.types;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointIntTest {

    private static final double DELTA = 1e-10;

    @Test
    void constructorInitializesCoordinates() {
        PointInt point = new PointInt(10, -20);

        assertEquals(10, point.x);
        assertEquals(-20, point.y);
    }

    @Test
    void addPointModifiesPointInPlace() {
        PointInt point = new PointInt(5, 10);

        point.add(new PointInt(3, -4));

        assertEquals(8, point.x);
        assertEquals(6, point.y);
    }

    @Test
    void addCoordinatesModifiesPointInPlace() {
        PointInt point = new PointInt(10, 20);

        point.add(5, -15);

        assertEquals(15, point.x);
        assertEquals(5, point.y);
    }

    @ParameterizedTest(name = "distance between {0} and {1} is {2}")
    @MethodSource("distanceCases")
    void staticDistanceIsCalculated(PointInt first, PointInt second, double expected) {
        assertEquals(expected, PointInt.distance(first, second), DELTA);
    }

    @Test
    void toStringProducesFormattedString() {
        assertEquals("[5,42]", new PointInt(5, 42).toString());
    }

    private static Stream<Arguments> distanceCases() {
        return Stream.of(
                Arguments.of(new PointInt(0, 0), new PointInt(3, 4), 5.0),
                Arguments.of(new PointInt(10, 10), new PointInt(10, 10), 0.0),
                Arguments.of(new PointInt(-1, -2), new PointInt(2, 2), 5.0)
        );
    }
}
