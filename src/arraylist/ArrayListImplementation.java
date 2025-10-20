package arraylist;

import java.util.Arrays;
import java.util.Objects;

/**
 * A simplified generic implementation of a dynamic array-based list.
 * This class mimics core behaviors of {@link java.util.ArrayList}, including automatic resizing,
 * indexed access, and basic mutation operations.
 *
 * @param <E> the type of elements stored in the list
 */
class ArrayListImplementation<E> {

    /** Internal array used to store elements. */
    private Object[] elementData;

    /** Current number of elements in the list. */
    private int size;

    /** Default initial capacity of the internal array. */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Constructs an empty list with default initial capacity.
     */
    public ArrayListImplementation() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Appends the specified element to the end of the list.
     *
     * @param element the element to be added
     */
    public void add(E element) {
        ensureCapacity(size + 1);
        elementData[size++] = element;
    }

    /**
     * Removes the element at the specified index, shifting subsequent elements to the left.
     *
     * @param index the index of the element to remove
     * @return the element that was removed
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public E removeAt(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E removed = (E) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return removed;
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public E get(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E value = (E) elementData[index];
        return value;
    }

    /**
     * Replaces the element at the specified index with the given element.
     *
     * @param index the index of the element to replace
     * @param element the new element to store
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void set(int index, E element) {
        checkIndex(index);
        elementData[index] = element;
    }

    /**
     * Returns {@code true} if the list contains the specified element.
     *
     * @param target the element to search for
     * @return {@code true} if the element is found; {@code false} otherwise
     */
    public boolean contains(E target) {
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E current = (E) elementData[i];
            if (Objects.equals(current, target)) return true;
        }
        return false;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the current size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if the list contains no elements.
     *
     * @return {@code true} if the list is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the list, resetting its size to zero.
     */
    public void clear() {
        Arrays.fill(elementData, 0, size, null);
        size = 0;
    }

    /**
     * Ensures that the internal array has enough capacity to store the specified number of elements.
     * If not, the array is resized to accommodate.
     *
     * @param minCapacity the minimum required capacity
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {
            int newCapacity = Math.max(elementData.length * 2, minCapacity);
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    /**
     * Validates that the given index is within the bounds of the list.
     *
     * @param index the index to validate
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
    }
}
