package tree;

import java.util.Objects;

/**
 * A minimal Binary Tree implementation supporting insertion, search, and in-order traversal.
 * Elements are ordered using their natural Comparable implementation.
 *
 * @param <E> the type of elements stored in the tree
 */
class BinaryTreeImplementation<E extends Comparable<E>> {

    /**
     * Node represents a single element in the binary tree.
     */
    private static class Node<E> {
        E item;
        Node<E> left;
        Node<E> right;

        Node(E item) {
            this.item = item;
        }
    }

    private Node<E> root;
    private int size;

    /**
     * Inserts the specified element into the tree.
     * Duplicate values are ignored.
     *
     * @param e the element to insert
     * @return true if inserted, false if already present
     * @throws NullPointerException if the element is null
     */
    public boolean insert(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        if (root == null) {
            root = new Node<>(e);
            size++;
            return true;
        }
        return insertRecursive(root, e);
    }

    private boolean insertRecursive(Node<E> node, E e) {
        int cmp = e.compareTo(node.item);
        if (cmp < 0) {
            if (node.left == null) {
                node.left = new Node<>(e);
                size++;
                return true;
            }
            return insertRecursive(node.left, e);
        } else if (cmp > 0) {
            if (node.right == null) {
                node.right = new Node<>(e);
                size++;
                return true;
            }
            return insertRecursive(node.right, e);
        }
        return false; // duplicate
    }

    /**
     * Returns true if the tree contains the specified element.
     *
     * @param e the element to search for
     * @return true if present, false otherwise
     */
    public boolean contains(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        return containsRecursive(root, e);
    }

    private boolean containsRecursive(Node<E> node, E e) {
        if (node == null) return false;
        int cmp = e.compareTo(node.item);
        if (cmp < 0) return containsRecursive(node.left, e);
        if (cmp > 0) return containsRecursive(node.right, e);
        return true;
    }

    /**
     * Performs in-order traversal and prints elements to standard output.
     */
    public void traverseInOrder() {
        traverseInOrderRecursive(root);
    }

    private void traverseInOrderRecursive(Node<E> node) {
        if (node != null) {
            traverseInOrderRecursive(node.left);
            System.out.println(node.item);
            traverseInOrderRecursive(node.right);
        }
    }

    /**
     * Returns the number of elements in the tree.
     *
     * @return the size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the tree contains no elements.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }
}
