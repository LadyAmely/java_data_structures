package set;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A minimal LinkedHashSet implementation using hash buckets and a doubly-linked list
 * to preserve insertion order and ensure uniqueness.
 *
 * @param <E> the type of elements maintained by this set
 */
class LinkedHashSetImplementation<E> implements Iterable<E> {

    private static class Node<E> {
        E item;
        Node<E> nextBucket;
        Node<E> prevOrder;
        Node<E> nextOrder;

        Node(E item) {
            this.item = item;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<E>[] buckets;
    private int size;

    private Node<E> head;
    private Node<E> tail;

    @SuppressWarnings("unchecked")
    public LinkedHashSetImplementation() {
        buckets = (Node<E>[]) new Node[DEFAULT_CAPACITY];
    }

    /**
     * Adds the specified element if not already present.
     *
     * @param e the element to add
     * @return true if added, false if already present
     */
    public boolean add(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        int index = indexFor(e);
        Node<E> node = buckets[index];

        while (node != null) {
            if (node.item.equals(e)) return false;
            node = node.nextBucket;
        }

        Node<E> newNode = new Node<>(e);
        newNode.nextBucket = buckets[index];
        buckets[index] = newNode;

        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.nextOrder = newNode;
            newNode.prevOrder = tail;
            tail = newNode;
        }

        size++;
        if (size > buckets.length * LOAD_FACTOR) resize();
        return true;
    }

    /**
     * Removes the specified element if present.
     *
     * @param e the element to remove
     * @return true if removed, false if not found
     */
    public boolean remove(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        int index = indexFor(e);
        Node<E> node = buckets[index];
        Node<E> prev = null;

        while (node != null) {
            if (node.item.equals(e)) {
                if (prev == null) {
                    buckets[index] = node.nextBucket;
                } else {
                    prev.nextBucket = node.nextBucket;
                }

                if (node.prevOrder != null) {
                    node.prevOrder.nextOrder = node.nextOrder;
                } else {
                    head = node.nextOrder;
                }
                if (node.nextOrder != null) {
                    node.nextOrder.prevOrder = node.prevOrder;
                } else {
                    tail = node.prevOrder;
                }

                size--;
                return true;
            }
            prev = node;
            node = node.nextBucket;
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
            if (node.item.equals(e)) return true;
            node = node.nextBucket;
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
        head = tail = null;
        size = 0;
    }

    /**
     * Returns an iterator over the elements in insertion order.
     *
     * @return an iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> current = head;

            public boolean hasNext() {
                return current != null;
            }

            public E next() {
                if (current == null) throw new NoSuchElementException();
                E item = current.item;
                current = current.nextOrder;
                return item;
            }
        };
    }

    private int indexFor(E e) {
        return (e.hashCode() & 0x7fffffff) % buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<E>[] oldBuckets = buckets;
        buckets = (Node<E>[]) new Node[oldBuckets.length * 2];
        Node<E> current = head;
        head = tail = null;
        size = 0;

        while (current != null) {
            add(current.item);
            current = current.nextOrder;
        }
    }
}
