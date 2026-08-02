package com.physmo.minvio.types;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class RectTest {

    @Test
    void constructorInitializesValues() {
        Rect rect = new Rect(10, 20, 100, 200);

        assertRect(rect, 10, 20, 100, 200);
    }

    @Test
    void copyConstructorCreatesDistinctEqualInstance() {
        Rect original = new Rect(10, 20, 100, 200);

        Rect copy = new Rect(original);

        assertEquals(original, copy);
        assertNotSame(original, copy);
        assertRect(copy, original.x, original.y, original.w, original.h);
    }

    @ParameterizedTest(name = "{0}.isPointInside({1}, {2}) is {3}")
    @MethodSource("pointInsideCases")
    void isPointInsideHandlesBoundaries(Rect rect, int x, int y, boolean expected) {
        assertEquals(expected, rect.isPointInside(x, y));
    }

    @ParameterizedTest(name = "{0} intersects {1} is {2}")
    @MethodSource("intersectionCases")
    void intersectionIsSymmetric(Rect first, Rect second, boolean expected) {
        assertEquals(expected, first.intersects(second));
        assertEquals(expected, second.intersects(first));
    }

    @Test
    void copyMethodCreatesDistinctEqualInstance() {
        Rect original = new Rect(5, 5, 50, 50);

        Rect copy = original.copy();

        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test
    void getAreaReturnsArea() {
        assertEquals(200, new Rect(0, 0, 10, 20).getArea());
    }

    @ParameterizedTest(name = "{0} contains {1} is {2}")
    @MethodSource("containmentCases")
    void containsReturnsExpectedValue(Rect container, Rect other, boolean expected) {
        assertEquals(expected, container.contains(other));
    }

    @Test
    void centerUsesIntegerDivision() {
        Rect even = new Rect(10, 20, 100, 50);
        Rect odd = new Rect(10, 20, 101, 51);

        assertEquals(60, even.getCenterX());
        assertEquals(45, even.getCenterY());
        assertEquals(60, odd.getCenterX());
        assertEquals(45, odd.getCenterY());
    }

    @Test
    void setValuesUpdatesRect() {
        Rect rect = new Rect(0, 0, 0, 0);

        rect.set(5, 15, 55, 155);

        assertRect(rect, 5, 15, 55, 155);
    }

    @Test
    void setRectUpdatesRect() {
        Rect rect = new Rect(0, 0, 0, 0);

        rect.set(new Rect(5, 15, 55, 155));

        assertRect(rect, 5, 15, 55, 155);
    }

    @Test
    void toStringProducesFormattedString() {
        assertEquals("Rect{x=1, y=2, w=3, h=4}", new Rect(1, 2, 3, 4).toString());
    }

    @Test
    void equalsAndHashCodeBehavior() {
        Rect first = new Rect(10, 20, 30, 40);
        Rect equal = new Rect(10, 20, 30, 40);
        Rect different = new Rect(99, 20, 30, 40);

        assertEquals(first, first);
        assertEquals(first, equal);
        assertEquals(equal, first);
        assertNotEquals(first, different);
        assertFalse(first.equals(null));
        assertFalse(first.equals("a string"));
        assertEquals(first.hashCode(), equal.hashCode());
        assertNotEquals(first.hashCode(), different.hashCode());
    }

    private static void assertRect(Rect rect, int x, int y, int width, int height) {
        assertEquals(x, rect.x);
        assertEquals(y, rect.y);
        assertEquals(width, rect.w);
        assertEquals(height, rect.h);
    }

    private static Stream<Arguments> pointInsideCases() {
        Rect rect = new Rect(10, 20, 100, 50);
        return Stream.of(
                Arguments.of(rect, 50, 50, true),
                Arguments.of(rect, 10, 20, true),
                Arguments.of(rect, 110, 70, true),
                Arguments.of(rect, 10, 50, true),
                Arguments.of(rect, 50, 20, true),
                Arguments.of(rect, 9, 50, false),
                Arguments.of(rect, 111, 50, false),
                Arguments.of(rect, 50, 19, false),
                Arguments.of(rect, 50, 71, false)
        );
    }

    private static Stream<Arguments> intersectionCases() {
        return Stream.of(
                Arguments.of(new Rect(0, 0, 10, 10), new Rect(5, 5, 10, 10), true),
                Arguments.of(new Rect(0, 0, 10, 10), new Rect(10, 0, 10, 10), true),
                Arguments.of(new Rect(0, 0, 10, 10), new Rect(10, 10, 10, 10), true),
                Arguments.of(new Rect(0, 0, 10, 10), new Rect(11, 0, 10, 10), false),
                Arguments.of(new Rect(0, 0, 10, 10), new Rect(0, 11, 10, 10), false),
                Arguments.of(new Rect(0, 0, 10, 10), new Rect(2, 2, 6, 6), true),
                Arguments.of(new Rect(2, 2, 6, 6), new Rect(0, 0, 10, 10), true),
                Arguments.of(new Rect(0, 0, 10, 10), new Rect(0, 0, 10, 10), true)
        );
    }

    private static Stream<Arguments> containmentCases() {
        return Stream.of(
                Arguments.of(new Rect(0, 0, 100, 100), new Rect(10, 10, 80, 80), true),
                Arguments.of(new Rect(0, 0, 100, 100), new Rect(0, 0, 100, 100), true),
                Arguments.of(new Rect(0, 0, 100, 100), new Rect(-1, 10, 80, 80), false),
                Arguments.of(new Rect(0, 0, 100, 100), new Rect(90, 90, 20, 20), false),
                Arguments.of(new Rect(0, 0, 100, 100), new Rect(110, 110, 10, 10), false)
        );
    }
}
