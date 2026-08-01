package com.physmo.minvio.types;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Vec3Test {

    private static final double DELTA = 1e-10;

    @Test
    void constructorInitializesCoordinates() {
        Vec3 vector = new Vec3(1.0, 2.0, 3.0);

        assertEquals(1.0, vector.x);
        assertEquals(2.0, vector.y);
        assertEquals(3.0, vector.z);
    }

    @Test
    void copyConstructorInitializesCoordinates() {
        Vec3 original = new Vec3(1.0, 2.0, 3.0);

        Vec3 copy = new Vec3(original);

        assertEquals(original.x, copy.x);
        assertEquals(original.y, copy.y);
        assertEquals(original.z, copy.z);
    }

    @Test
    void copyConstructorRejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Vec3(null));
    }

    @Test
    void setUpdatesCoordinates() {
        Vec3 vector = new Vec3(1.0, 2.0, 3.0);

        vector.set(4.0, 5.0, 6.0);

        assertEquals(new Vec3(4.0, 5.0, 6.0), vector);
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @MethodSource("additionCases")
    void addReturnsNewVector(Vec3 first, Vec3 second, Vec3 expected) {
        Vec3 result = first.add(second);

        assertEquals(expected, result);
        assertEquals(new Vec3(1.0, 2.0, 3.0), first);
    }

    @ParameterizedTest(name = "{0}.addi({1}) becomes {2}")
    @MethodSource("additionCases")
    void addiModifiesVectorInPlace(Vec3 first, Vec3 second, Vec3 expected) {
        first.addi(second);

        assertEquals(expected, first);
    }

    @Test
    void scaleReturnsNewScaledVector() {
        Vec3 vector = new Vec3(1.0, 2.0, 3.0);

        Vec3 result = vector.scale(2.5);

        assertEquals(new Vec3(2.5, 5.0, 7.5), result);
        assertEquals(new Vec3(1.0, 2.0, 3.0), vector);
    }

    @Test
    void scaleiScalesVectorInPlace() {
        Vec3 vector = new Vec3(1.0, 2.0, 3.0);

        vector.scalei(2.0);

        assertEquals(new Vec3(2.0, 4.0, 6.0), vector);
    }

    @ParameterizedTest(name = "distance between {0} and {1} is {2}")
    @MethodSource("distanceCases")
    void distanceIsCalculated(Vec3 first, Vec3 second, double expected) {
        assertEquals(expected, first.distance(second), DELTA);
    }

    @Test
    void normaliseReturnsOriginalMagnitudeAndNormalisesVector() {
        Vec3 vector = new Vec3(3.0, 4.0, 0.0);

        double originalMagnitude = vector.normalise();
        double newMagnitude = Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);

        assertEquals(5.0, originalMagnitude);
        assertEquals(1.0, newMagnitude, DELTA);
        assertEquals(new Vec3(0.6, 0.8, 0.0), vector);
    }

    @Test
    void equalsAndHashCodeUseExactComponents() {
        Vec3 first = new Vec3(1.0, 2.0, 3.0);
        Vec3 equal = new Vec3(1.0, 2.0, 3.0);
        Vec3 withinEpsilon = new Vec3(1.0, 2.0, 3.00000000001);
        Vec3 different = new Vec3(4.0, 5.0, 6.0);

        assertEquals(first, first);
        assertEquals(first, equal);
        assertEquals(equal, first);
        assertEquals(first.hashCode(), equal.hashCode());
        assertNotEquals(first, withinEpsilon);
        assertNotEquals(first.hashCode(), withinEpsilon.hashCode());
        assertNotEquals(first, different);
        assertNotEquals(first, new Vec3(1.0, 5.0, 3.0));
        assertNotEquals(first.hashCode(), different.hashCode());
        assertFalse(first.equals(null));
        assertFalse(first.equals("a string"));
    }

    @Test
    void approximateEqualityUsesExplicitTolerance() {
        Vec3 vector = new Vec3(1.0, 2.0, 3.0);
        Vec3 close = new Vec3(1.0, 2.0, 3.0001);

        assertTrue(vector.approximatelyEquals(close, 0.00011));
        assertFalse(vector.approximatelyEquals(close, 0.00001));
        assertFalse(vector.approximatelyEquals(new Vec3(2.0, 2.0, 3.0), 0.1));
        assertFalse(vector.approximatelyEquals(new Vec3(1.0, 3.0, 3.0), 0.1));
        assertThrows(IllegalArgumentException.class, () -> vector.approximatelyEquals(close, -0.1));
        assertThrows(IllegalArgumentException.class, () -> vector.approximatelyEquals(close, Double.NaN));
        assertThrows(NullPointerException.class, () -> vector.approximatelyEquals(null, 0.1));
    }

    @Test
    void normalisingZeroVectorLeavesItUnchanged() {
        Vec3 vector = new Vec3(0.0, 0.0, 0.0);

        assertEquals(0.0, vector.normalise());
        assertEquals(new Vec3(0.0, 0.0, 0.0), vector);
    }

    @Test
    void toStringProducesFormattedString() {
        assertEquals("Vec3{x=1.23, y=5.68, z=9.00}", new Vec3(1.234, 5.678, 9.0).toString());
    }

    @Test
    void toStringUsesStableDecimalSeparatorAcrossLocales() {
        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.FRANCE);

            assertEquals("Vec3{x=1.23, y=5.68, z=9.00}", new Vec3(1.234, 5.678, 9.0).toString());
        } finally {
            Locale.setDefault(previous);
        }
    }

    @Test
    void dotProductIsCalculated() {
        assertEquals(32.0, new Vec3(1, 2, 3).dot(new Vec3(4, 5, 6)));
    }

    @Test
    void crossProductIsCalculated() {
        assertEquals(new Vec3(0, 0, 1), new Vec3(1, 0, 0).cross(new Vec3(0, 1, 0)));
    }

    @Test
    void lerpInterpolates() {
        Vec3 first = new Vec3(0, 0, 0);
        Vec3 second = new Vec3(10, 10, 10);

        assertEquals(new Vec3(5, 5, 5), first.lerp(second, 0.5));
        assertEquals(new Vec3(1, 1, 1), first.lerp(second, 0.1));
        assertEquals(first, first.lerp(second, 0.0));
        assertEquals(second, first.lerp(second, 1.0));
    }

    @Test
    void angleBetweenIsCalculated() {
        Vec3 first = new Vec3(1, 0, 0);
        Vec3 second = new Vec3(0, 1, 0);

        assertEquals(Math.PI / 2, first.angleBetween(second), DELTA);
        assertEquals(0.0, first.angleBetween(first), DELTA);
    }

    @Test
    void angleBetweenRejectsZeroLengthVector() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vec3(0, 0, 0).angleBetween(new Vec3(1, 0, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> new Vec3(1, 0, 0).angleBetween(new Vec3(0, 0, 0)));
    }

    private static Stream<Arguments> additionCases() {
        return Stream.of(
                Arguments.of(
                        new Vec3(1.0, 2.0, 3.0),
                        new Vec3(4.0, 5.0, 6.0),
                        new Vec3(5.0, 7.0, 9.0)),
                Arguments.of(
                        new Vec3(1.0, 2.0, 3.0),
                        new Vec3(-1.0, -2.0, -3.0),
                        new Vec3(0.0, 0.0, 0.0))
        );
    }

    private static Stream<Arguments> distanceCases() {
        return Stream.of(
                Arguments.of(new Vec3(0.0, 0.0, 0.0), new Vec3(3.0, 4.0, 0.0), 5.0),
                Arguments.of(new Vec3(1.0, 1.0, 1.0), new Vec3(1.0, 1.0, 1.0), 0.0),
                Arguments.of(
                        new Vec3(-1.0, -1.0, -1.0),
                        new Vec3(1.0, 1.0, 1.0),
                        Math.sqrt(12.0))
        );
    }
}
