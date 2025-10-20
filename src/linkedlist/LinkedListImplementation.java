package linkedlist;

/**
 * A minimal doubly-linked list implementation supporting basic operations such as
 * adding/removing elements from both ends, retrieving elements, clearing the list,
 * and checking its size or emptiness.
 *
 * <p>This implementation is not thread-safe and does not support indexed access or iteration.
 *
 * @param <E> the type of elements held in this list
 */
class LinkedListImplementation<E> {

    /**
     * Node represents an element in the doubly-linked list.
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    /**
     * Inserts the specified element at the beginning of the list.
     *
     * @param e the element to add
     */
    public void addFirst(E e) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    /**
     * Appends the specified element to the end of the list.
     *
     * @param e the element to add
     */
    public void addLast(E e) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the first element from the list.
     *
     * @return the removed element
     * @throws IllegalStateException if the list is empty
     */
    public E removeFirst() {
        if (first == null) {
            throw new IllegalStateException("List is empty");
        }
        Node<E> f = first;
        E item = f.item;
        Node<E> next = f.next;

        f.item = null;
        f.next = null;
        first = next;

        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }

        size--;
        return item;
    }

    /**
     * Removes and returns the last element from the list.
     *
     * @return the removed element
     * @throws IllegalStateException if the list is empty
     */
    public E removeLast() {
        if (last == null) {
            throw new IllegalStateException("List is empty");
        }
        Node<E> l = last;
        E item = l.item;
        Node<E> prev = l.prev;

        l.item = null;
        l.prev = null;
        last = prev;

        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }

        size--;
        return item;
    }

    /**
     * Retrieves, but does not remove, the first element of the list.
     *
     * @return the first element
     * @throws IllegalStateException if the list is empty
     */
    public E getFirst() {
        if (first == null) {
            throw new IllegalStateException("List is empty");
        }
        return first.item;
    }

    /**
     * Retrieves, but does not remove, the last element of the list.
     *
     * @return the last element
     * @throws IllegalStateException if the list is empty
     */
    public E getLast() {
        if (last == null) {
            throw new IllegalStateException("List is empty");
        }
        return last.item;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if the list contains no elements.
     *
     * @return {@code true} if the list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the list.
     * After this call, the list will be empty.
     */
    public void clear() {
        Node<E> x = first;
        while (x != null) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }
}
