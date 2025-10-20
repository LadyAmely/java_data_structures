package stack;

import java.util.NoSuchElementException;

/**
 * A minimal, production-ready stack implementation using singly-linked nodes.
 * Supports standard LIFO operations with O(1) time complexity.
 *
 * @param <E> the type of elements held in this stack
 */
class StackImplementation<E> {

    /**
     * Node represents an element in the stack.
     */
    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    private Node<E> top;
    private int size;

    /**
     * Pushes an element onto the top of the stack.
     *
     * @param e the element to push
     */
    public void push(E e) {
        top = new Node<>(e, top);
        size++;
    }

    /**
     * Removes and returns the top element of the stack.
     *
     * @return the popped element
     * @throws NoSuchElementException if the stack is empty
     */
    public E pop() {
        if (top == null) {
            throw new NoSuchElementException("Stack is empty");
        }
        E item = top.item;
        top = top.next;
        size--;
        return item;
    }

    /**
     * Returns the top element without removing it.
     *
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
    public E peek() {
        if (top == null) {
            throw new NoSuchElementException("Stack is empty");
        }
        return top.item;
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the size of the stack
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if the stack contains no elements.
     *
     * @return {@code true} if the stack is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the stack.
     */
    public void clear() {
        while (top != null) {
            Node<E> next = top.next;
            top.item = null;
            top.next = null;
            top = next;
        }
        size = 0;
    }

    /**
     * Checks whether the stack contains the specified element.
     *
     * @param e the element to search for
     * @return true if found, false otherwise
     */
    public boolean contains(E e) {
        for (Node<E> x = top; x != null; x = x.next) {
            if (e == null ? x.item == null : e.equals(x.item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the 0-based position of the element from the top of the stack.
     * Returns -1 if not found.
     *
     * @param e the element to search for
     * @return position from top, or -1
     */
    public int search(E e) {
        int index = 0;
        for (Node<E> x = top; x != null; x = x.next) {
            if (e == null ? x.item == null : e.equals(x.item)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Returns an array containing all elements in LIFO order.
     *
     * @return array of elements
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = top; x != null; x = x.next) {
            result[i++] = x.item;
        }
        return result;
    }
}
