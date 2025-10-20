package queue;

import java.util.NoSuchElementException;

/**
 * A production-grade double-ended queue (Deque) implementation using doubly-linked nodes.
 * Supports constant-time insertion and removal from both ends.
 *
 * @param <E> the type of elements held in this deque
 */
class DequeImplementation<E> {

    /**
     * Internal node class representing an element in the deque.
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(E item) {
            this.item = item;
        }
    }

    private Node<E> first;
    private Node<E> last;
    private int size;

    /**
     * Inserts the specified element at the front of this deque.
     *
     * @param e the element to add
     * @throws NullPointerException if the element is null
     */
    public void addFirst(E e) {
        if (e == null) {
            throw new NullPointerException("Null elements are not allowed");
        }
        Node<E> newNode = new Node<>(e);
        newNode.next = first;
        if (first != null) {
            first.prev = newNode;
        } else {
            last = newNode;
        }
        first = newNode;
        size++;
    }

    /**
     * Inserts the specified element at the end of this deque.
     *
     * @param e the element to add
     * @throws NullPointerException if the element is null
     */
    public void addLast(E e) {
        if (e == null) {
            throw new NullPointerException("Null elements are not allowed");
        }
        Node<E> newNode = new Node<>(e);
        newNode.prev = last;
        if (last != null) {
            last.next = newNode;
        } else {
            first = newNode;
        }
        last = newNode;
        size++;
    }

    /**
     * Removes and returns the first element of this deque.
     *
     * @return the removed element
     * @throws NoSuchElementException if the deque is empty
     */
    public E removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        E item = first.item;
        Node<E> next = first.next;
        first.item = null;
        first.next = null;
        if (next != null) {
            next.prev = null;
        } else {
            last = null;
        }
        first = next;
        size--;
        return item;
    }

    /**
     * Removes and returns the last element of this deque.
     *
     * @return the removed element
     * @throws NoSuchElementException if the deque is empty
     */
    public E removeLast() {
        if (last == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        E item = last.item;
        Node<E> prev = last.prev;
        last.item = null;
        last.prev = null;
        if (prev != null) {
            prev.next = null;
        } else {
            first = null;
        }
        last = prev;
        size--;
        return item;
    }

    /**
     * Retrieves, but does not remove, the first element of this deque.
     *
     * @return the first element
     * @throws NoSuchElementException if the deque is empty
     */
    public E peekFirst() {
        if (first == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        return first.item;
    }

    /**
     * Retrieves, but does not remove, the last element of this deque.
     *
     * @return the last element
     * @throws NoSuchElementException if the deque is empty
     */
    public E peekLast() {
        if (last == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        return last.item;
    }

    /**
     * Returns {@code true} if this deque contains no elements.
     *
     * @return {@code true} if this deque is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in this deque.
     *
     * @return the size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * Removes all elements from this deque.
     * After this call, the deque will be empty.
     */
    public void clear() {
        Node<E> current = first;
        while (current != null) {
            Node<E> next = current.next;
            current.item = null;
            current.next = null;
            current.prev = null;
            current = next;
        }
        first = last = null;
        size = 0;
    }
}
