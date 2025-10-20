package set;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A minimal TreeSet implementation using a binary search tree.
 * Stores unique elements in sorted order with customizable comparator or natural ordering.
 *
 * @param <E> the type of elements maintained by this set
 */
class TreeSetImplementation<E> {

    /**
     * Node represents a single element in the tree.
     */
    private static class Node<E> {
        E item;
        Node<E> left, right;

        Node(E item) {
            this.item = item;
        }
    }

    private Node<E> root;
    private int size;
    private final Comparator<? super E> comparator;

    /**
     * Constructs an empty TreeSet with natural ordering.
     * Elements must implement Comparable.
     */
    public TreeSetImplementation() {
        this.comparator = null;
    }

    /**
     * Constructs an empty TreeSet with a custom comparator.
     *
     * @param comparator the comparator to determine element ordering
     */
    public TreeSetImplementation(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Adds the specified element to the set if not already present.
     *
     * @param e the element to add
     * @return true if the element was added, false if already present
     * @throws NullPointerException if the element is null
     */
    public boolean add(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        if (contains(e)) return false;
        root = insert(root, e);
        size++;
        return true;
    }

    private Node<E> insert(Node<E> node, E e) {
        if (node == null) return new Node<>(e);
        int cmp = compare(e, node.item);
        if (cmp < 0) node.left = insert(node.left, e);
        else if (cmp > 0) node.right = insert(node.right, e);
        return node;
    }

    /**
     * Removes the specified element from the set if present.
     *
     * @param e the element to remove
     * @return true if the element was removed, false if not found
     * @throws NullPointerException if the element is null
     */
    public boolean remove(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        if (!contains(e)) return false;
        root = delete(root, e);
        size--;
        return true;
    }

    private Node<E> delete(Node<E> node, E e) {
        if (node == null) return null;
        int cmp = compare(e, node.item);
        if (cmp < 0) node.left = delete(node.left, e);
        else if (cmp > 0) node.right = delete(node.right, e);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node<E> min = getMin(node.right);
            node.item = min.item;
            node.right = delete(node.right, min.item);
        }
        return node;
    }

    /**
     * Returns true if the set contains the specified element.
     *
     * @param e the element to check
     * @return true if present, false otherwise
     */
    public boolean contains(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(e, node.item);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return true;
        }
        return false;
    }

    /**
     * Returns the smallest element in the set.
     *
     * @return the first (minimum) element
     * @throws NoSuchElementException if the set is empty
     */
    public E first() {
        if (root == null) throw new NoSuchElementException("Set is empty");
        return getMin(root).item;
    }

    /**
     * Returns the largest element in the set.
     *
     * @return the last (maximum) element
     * @throws NoSuchElementException if the set is empty
     */
    public E last() {
        if (root == null) throw new NoSuchElementException("Set is empty");
        Node<E> node = root;
        while (node.right != null) node = node.right;
        return node.item;
    }

    private Node<E> getMin(Node<E> node) {
        while (node.left != null) node = node.left;
        return node;
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
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Compares two elements using the comparator or natural ordering.
     *
     * @param a the first element
     * @param b the second element
     * @return a negative value if a < b, zero if equal, positive if a > b
     */
    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {
        if (comparator != null) return comparator.compare(a, b);
        return ((Comparable<? super E>) a).compareTo(b);
    }
}
