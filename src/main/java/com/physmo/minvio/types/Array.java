package com.physmo.minvio.types;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A generic dynamic array that supports adding elements, resizing, sorting,
 * and clearing. It also provides an iterator for traversing its elements.
 * This class is specifically designed to avoid allocations which will
 * contribute to garbage collection.
 *
 * @param <T> the type of elements stored in the array
 */
public class Array<T> implements Iterable<T> {

    /**
     * @deprecated Direct backing-array access bypasses this collection's
     * invariants. This field will become private in a future major release.
     */
    @Deprecated
    public T[] array;

    /**
     * @deprecated Use {@link #size()} instead. This field will become private
     * in a future major release.
     */
    @Deprecated
    public int size;

    /**
     * Constructs an Array with a specified initial capacity. The array is initialized
     * to hold elements of type T, and the initial size of the array is set to 0.
     *
     * @param capacity the initial capacity of the array
     *                 (i.e., the maximum number of elements it can hold before resizing).
     */
    public Array(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        array = (T[]) new Object[capacity];
        size = 0;
    }

    /**
     * Returns the current capacity of the underlying array.
     *
     * @return the total number of elements the array can hold.
     */
    public int getCapacity() {
        return array.length;
    }

    private void doubleArrayCapacity() {
        int newCapacity = Math.max(1, array.length * 2);
        T[] newArray = (T[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    /**
     * Removes all elements from the array. After calling this method, the array will
     * be empty, and its size will be reset to zero. The elements in the array will
     * be set to null.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    /**
     * Removes all elements from the array that satisfy the given predicate.
     *
     * @param filter a predicate used to determine which elements should be removed.
     *               The predicate is applied to each element, and elements for which
     *               the predicate returns true are removed.
     * @return true if any elements were removed as a result of the operation,
     * false otherwise.
     */
    public boolean removeIf(Predicate<? super T> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;

        int writePos = 0;
        for (int i = 0; i < size; i++) {
            if (filter.test(array[i])) {
                removed = true;
                continue;
            }
            array[writePos++] = array[i];
        }
        Arrays.fill(array, writePos, size, null);
        size = writePos;
        return removed;
    }

    /**
     * Adds all elements from the specified list to the array.
     *
     * @param list the list containing elements to be added to this array
     * @throws NullPointerException if the specified list is null
     */
    public void addAll(List<T> list) {
        Objects.requireNonNull(list, "List cannot be null");
        for (T t : list) add(t);
    }

    public void addAll(Array<T> list) {
        Objects.requireNonNull(list, "Array cannot be null");
        for (int i = 0; i < list.size(); i++) add(list.get(i));
    }

    /**
     * Adds the specified element to the array. If the underlying array's capacity
     * is reached, it is automatically doubled before adding the element.
     *
     * @param element the element to be added to the array
     */
    public void add(T element) {
        if (size == array.length) doubleArrayCapacity();
        array[size++] = element;
    }

    /**
     * Returns the current number of elements stored in the array.
     *
     * @return the number of elements currently present in the array.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the array is empty.
     *
     * @return true if the array contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Sorts the elements in the array using the specified comparator.
     * The sorting is performed on the internal array from the beginning
     * of the array up to the current size of the array.
     *
     * @param comparator the comparator to determine the order of the array.
     *                   A {@code null} comparator indicates that the elements'
     *                   natural ordering should be used.
     */
    public void sort(Comparator<T> comparator) {
        Arrays.sort(array, 0, size, comparator);
    }

    /**
     * Checks whether the specified element is present in the array.
     *
     * @param element the element to check for presence in the array. Must be of type T.
     * @return true if the element is found in the array, false otherwise.
     */
    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], element)) return true;
        }
        return false;
    }

    /**
     * Retrieves the index of the specified element in the array.
     *
     * @param element the element to search for in the array. Must be of type T.
     * @return the zero-based index of the element if found; -1 otherwise.
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], element)) return i;
        }
        return -1;
    }


    /**
     * Retrieves the element at the specified index from the array.
     *
     * @param index the position of the element to retrieve, zero-based.
     *              Must be within the range `0` to `size - 1`, where `size` is
     *              the number of elements currently stored in the array.
     * @return the element of type T stored at the specified index.
     * @throws ArrayIndexOutOfBoundsException if the index is out of bounds
     *                                        (i.e., less than 0 or greater than or equal to the current size
     *                                        of the array).
     */
    public T get(int index) {
        checkIndex(index);
        return array[index];
    }

    /**
     * Sets the specified element at the given index in the array.
     *
     * @param index   the zero-based index where the element should be set.
     *                Must be within the range `0` to `size - 1`.
     * @param element the element of type T to set at the specified index.
     * @throws ArrayIndexOutOfBoundsException if the index is out of bounds
     *                                        (i.e., less than 0 or greater than or equal to the current size
     *                                        of the array).
     */
    public void setAt(int index, T element) {
        checkIndex(index);
        array[index] = element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(
                    "Index " + index + " out of bounds for size " + size);
        }
    }

    /**
     * Returns an iterator over elements of type T in the array.
     * The iterator allows sequential access to the elements stored in the array.
     *
     * @return an Iterator of type T that allows traversal of the array's elements.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                return array[index++];
            }
        };
    }

}
