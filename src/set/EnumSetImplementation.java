package set;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A minimal EnumSet implementation using a bitmask.
 * Supports fast operations for enums with up to 64 constants.
 *
 * @param <E> the enum type maintained by this set
 */
class EnumSetImplementation<E extends Enum<E>> implements Iterable<E> {

    private final Class<E> enumType;
    private final E[] universe;
    private long elements;

    /**
     * Creates an empty EnumSet for the specified enum type.
     *
     * @param enumType the class of the enum
     */
    @SuppressWarnings("unchecked")
    public EnumSetImplementation(Class<E> enumType) {
        this.enumType = Objects.requireNonNull(enumType, "Enum type cannot be null");
        this.universe = enumType.getEnumConstants();
        if (universe.length > 64) {
            throw new IllegalArgumentException("EnumSet supports up to 64 enum constants");
        }
        this.elements = 0L;
    }

    /**
     * Adds the specified enum constant to the set.
     *
     * @param e the enum constant to add
     * @return true if added, false if already present
     */
    public boolean add(E e) {
        Objects.requireNonNull(e, "Enum constant cannot be null");
        int ordinal = e.ordinal();
        long mask = 1L << ordinal;
        boolean wasPresent = (elements & mask) != 0;
        elements |= mask;
        return !wasPresent;
    }

    /**
     * Removes the specified enum constant from the set.
     *
     * @param e the enum constant to remove
     * @return true if removed, false if not present
     */
    public boolean remove(E e) {
        Objects.requireNonNull(e, "Enum constant cannot be null");
        int ordinal = e.ordinal();
        long mask = 1L << ordinal;
        boolean wasPresent = (elements & mask) != 0;
        elements &= ~mask;
        return wasPresent;
    }

    /**
     * Returns true if the set contains the specified enum constant.
     *
     * @param e the enum constant to check
     * @return true if present, false otherwise
     */
    public boolean contains(E e) {
        Objects.requireNonNull(e, "Enum constant cannot be null");
        int ordinal = e.ordinal();
        long mask = 1L << ordinal;
        return (elements & mask) != 0;
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the size of the set
     */
    public int size() {
        return Long.bitCount(elements);
    }

    /**
     * Returns true if the set contains no elements.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return elements == 0L;
    }

    /**
     * Removes all elements from the set.
     */
    public void clear() {
        elements = 0L;
    }

    /**
     * Returns an iterator over the enum constants in the set.
     *
     * @return an iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = 0;

            public boolean hasNext() {
                while (index < universe.length) {
                    if ((elements & (1L << index)) != 0) return true;
                    index++;
                }
                return false;
            }

            public E next() {
                while (index < universe.length) {
                    if ((elements & (1L << index)) != 0) {
                        return universe[index++];
                    }
                    index++;
                }
                throw new NoSuchElementException();
            }
        };
    }
}
