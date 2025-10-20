package queue;

import java.util.NoSuchElementException;

/**
 * A classic FIFO queue implementation using singly-linked nodes.
 * Supports standard queue operations with O(1) time complexity.
 *
 * @param <E> the type of elements held in this queue
 */
class QueueImplementation<E> {

    /**
     * Node represents an element in the queue.
     */
    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item) {
            this.item = item;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    /**
     * Adds an element to the end of the queue.
     *
     * @param e the element to add
     * @throws NullPointerException if the element is null
     */
    public void add(E e) {
        if (e == null) {
            throw new NullPointerException("Null elements not allowed");
        }
        Node<E> newNode = new Node<>(e);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    /**
     * Removes and returns the front element of the queue.
     *
     * @return the removed element
     * @throws NoSuchElementException if the queue is empty
     */
    public E remove() {
        if (head == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        E item = head.item;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return item;
    }

    /**
     * Returns the front element without removing it.
     *
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    public E peek() {
        if (head == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        return head.item;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if the queue contains no elements.
     *
     * @return {@code true} if the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the queue.
     */
    public void clear() {
        while (head != null) {
            Node<E> next = head.next;
            head.item = null;
            head.next = null;
            head = next;
        }
        tail = null;
        size = 0;
    }
}
