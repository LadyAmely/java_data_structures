package set;

import java.util.Objects;

/**
 * A minimal HashSet implementation using separate chaining.
 * Stores unique elements with constant-time average operations.
 *
 * @param <E> the type of elements maintained by this set
 */
class HashSetImplementation<E> {

    /**
     * Node represents a single element in the set.
     */
    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<E>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public HashSetImplementation() {
        buckets = (Node<E>[]) new Node[DEFAULT_CAPACITY];
    }

    /**
     * Adds the specified element to the set if not already present.
     *
     * @param e the element to add
     * @return true if the element was added, false if already present
     */
    public boolean add(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        int index = indexFor(e);
        Node<E> node = buckets[index];

        while (node != null) {
            if (node.item.equals(e)) {
                return false;
            }
            node = node.next;
        }

        buckets[index] = new Node<>(e, buckets[index]);
        size++;

        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }

        return true;
    }

    /**
     * Removes the specified element from the set if present.
     *
     * @param e the element to remove
     * @return true if the element was removed, false if not found
     */
    public boolean remove(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        int index = indexFor(e);
        Node<E> node = buckets[index];
        Node<E> prev = null;

        while (node != null) {
            if (node.item.equals(e)) {
                if (prev == null) {
                    buckets[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }

        return false;
    }

    /**
     * Returns true if the set contains the specified element.
     *
     * @param e the element to check
     * @return true if present, false otherwise
     */
    public boolean contains(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        int index = indexFor(e);
        Node<E> node = buckets[index];

        while (node != null) {
            if (node.item.equals(e)) {
                return true;
            }
            node = node.next;
        }

        return false;
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the size of the set
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the set contains no elements.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the set.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private int indexFor(E e) {
        return (e.hashCode() & 0x7fffffff) % buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<E>[] oldBuckets = buckets;
        buckets = (Node<E>[]) new Node[oldBuckets.length * 2];
        size = 0;

        for (Node<E> node : oldBuckets) {
            while (node != null) {
                add(node.item); // rehash
                node = node.next;
            }
        }
    }
}
