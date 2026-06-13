package com.physmo.minvio.types;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayTest {

    @Test
    void addingElementsIncreasesSizeAndElementsCanBeRetrieved() {
        Array<String> array = new Array<>(10);

        array.add("a");
        array.add("b");
        array.add("c");

        assertEquals(3, array.size());
        assertFalse(array.isEmpty());
        assertEquals("a", array.get(0));
        assertEquals("b", array.get(1));
        assertEquals("c", array.get(2));
    }

    @Test
    void clearRemovesAllElements() {
        Array<String> array = new Array<>(10);
        array.add("a");
        array.add("b");

        array.clear();

        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
    }

    @Test
    void removeIfRemovesElementsBasedOnPredicate() {
        Array<Integer> array = new Array<>(10);
        array.addAll(List.of(1, 2, 3, 4));

        boolean removed = array.removeIf(value -> value % 2 == 0);

        assertTrue(removed);
        assertEquals(2, array.size());
        assertEquals(1, array.get(0));
        assertEquals(3, array.get(1));
    }

    @Test
    void arrayResizesWhenCapacityIsReached() {
        Array<String> array = new Array<>(2);

        array.add("a");
        array.add("b");
        array.add("c");

        assertEquals(3, array.size());
        assertEquals(4, array.getCapacity());
        assertEquals("a", array.get(0));
        assertEquals("b", array.get(1));
        assertEquals("c", array.get(2));
    }

    @Test
    void zeroCapacityArrayCanGrow() {
        Array<String> array = new Array<>(0);

        array.add("a");

        assertEquals(1, array.getCapacity());
        assertEquals("a", array.get(0));
    }

    @Test
    void constructorRejectsNegativeCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new Array<>(-1));
    }

    @Test
    void sortOrdersElementsCorrectly() {
        Array<Integer> array = new Array<>(5);
        array.addAll(List.of(3, 1, 4, 2, 5));

        array.sort(Comparator.naturalOrder());

        assertEquals(List.of(1, 2, 3, 4, 5), toList(array));
    }

    @Test
    void addAllAddsAllElementsFromList() {
        Array<String> array = new Array<>(5);
        array.add("a");

        array.addAll(List.of("b", "c"));

        assertEquals(List.of("a", "b", "c"), toList(array));
    }

    @Test
    void iteratorIsFunctional() {
        Array<String> array = new Array<>(5);
        array.addAll(List.of("a", "b", "c"));

        assertEquals(List.of("a", "b", "c"), toList(array));
    }

    @Test
    void addAllWithArrayAddsAllElements() {
        Array<String> array = new Array<>(5);
        array.add("a");
        Array<String> otherArray = new Array<>(2);
        otherArray.addAll(List.of("b", "c"));

        array.addAll(otherArray);

        assertEquals(List.of("a", "b", "c"), toList(array));
    }

    @ParameterizedTest(name = "contains({0}) is {1}")
    @MethodSource("elementPresenceCases")
    void containsReturnsExpectedValue(String element, boolean expected) {
        Array<String> array = stringArray();

        assertEquals(expected, array.contains(element));
    }

    @ParameterizedTest(name = "indexOf({0}) is {1}")
    @MethodSource("elementIndexCases")
    void indexOfReturnsExpectedIndex(String element, int expected) {
        Array<String> array = stringArray();

        assertEquals(expected, array.indexOf(element));
    }

    @Test
    void setAtUpdatesElementAtGivenIndex() {
        Array<String> array = stringArray();

        array.setAt(1, "c");

        assertEquals(2, array.size());
        assertEquals("a", array.get(0));
        assertEquals("c", array.get(1));
    }

    @Test
    void nullElementsAreHandledConsistently() {
        Array<String> array = new Array<>(2);
        array.add(null);
        array.add("value");

        assertTrue(array.contains(null));
        assertEquals(0, array.indexOf(null));

        array.setAt(1, null);
        assertTrue(array.contains(null));
    }

    @ParameterizedTest(name = "setAt({0}, {1}) throws {2}")
    @MethodSource("invalidSetCases")
    void setAtThrowsForInvalidArguments(int index, String element, Class<? extends Throwable> exceptionType) {
        Array<String> array = new Array<>(5);
        array.add("a");

        assertThrows(exceptionType, () -> array.setAt(index, element));
    }

    @Test
    void removeIfReturnsFalseWhenNoElementsAreRemoved() {
        Array<Integer> array = new Array<>(10);
        array.addAll(List.of(1, 3));

        boolean removed = array.removeIf(value -> value % 2 == 0);

        assertFalse(removed);
        assertEquals(2, array.size());
    }

    @Test
    void iteratorNextThrowsWhenNoMoreElements() {
        Array<String> array = new Array<>(1);
        array.add("a");
        Iterator<String> iterator = array.iterator();

        assertEquals("a", iterator.next());
        assertThrows(NoSuchElementException.class, iterator::next);
        assertFalse(iterator.hasNext());
    }

    @Test
    void getRejectsIndexesOutsideLogicalSize() {
        Array<String> array = new Array<>(5);
        array.add("a");

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(1));
    }

    @Test
    void removeIfClearsDiscardedBackingReferences() throws ReflectiveOperationException {
        Array<String> array = new Array<>(4);
        array.addAll(List.of("a", "b", "c", "d"));

        array.removeIf(value -> value.equals("b") || value.equals("d"));

        Object[] backingArray = (Object[]) Array.class.getField("array").get(array);
        assertEquals(List.of("a", "c"), toList(array));
        assertNull(backingArray[2]);
        assertNull(backingArray[3]);
    }

    private static Array<String> stringArray() {
        Array<String> array = new Array<>(5);
        array.addAll(List.of("a", "b"));
        return array;
    }

    private static Stream<Arguments> elementPresenceCases() {
        return Stream.of(
                Arguments.of("a", true),
                Arguments.of("b", true),
                Arguments.of("c", false)
        );
    }

    private static Stream<Arguments> elementIndexCases() {
        return Stream.of(
                Arguments.of("a", 0),
                Arguments.of("b", 1),
                Arguments.of("c", -1)
        );
    }

    private static Stream<Arguments> invalidSetCases() {
        return Stream.of(
                Arguments.of(1, "c", ArrayIndexOutOfBoundsException.class),
                Arguments.of(-1, "c", ArrayIndexOutOfBoundsException.class)
        );
    }

    private static <T> List<T> toList(Array<T> array) {
        List<T> values = new ArrayList<>();
        for (T value : array) {
            values.add(value);
        }
        return values;
    }
}
