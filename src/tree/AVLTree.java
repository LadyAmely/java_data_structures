package tree;

import java.util.Objects;

/**
 * A minimal AVL Tree implementation supporting insertion, search, and in-order traversal.
 * Automatically balances itself to maintain O(log n) operations.
 *
 * @param <E> the type of elements stored in the tree
 */
class AVLTree<E extends Comparable<E>> {

    /**
     * Node represents a single element in the AVL tree.
     */
    private static class Node<E> {
        E item;
        Node<E> left;
        Node<E> right;
        int height;

        Node(E item) {
            this.item = item;
            this.height = 1;
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
     */
    public boolean insert(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        if (!contains(e)) {
            root = insertRecursive(root, e);
            size++;
            return true;
        }
        return false;
    }

    private Node<E> insertRecursive(Node<E> node, E e) {
        if (node == null) return new Node<>(e);

        int cmp = e.compareTo(node.item);
        if (cmp < 0) {
            node.left = insertRecursive(node.left, e);
        } else if (cmp > 0) {
            node.right = insertRecursive(node.right, e);
        }

        updateHeight(node);
        return balance(node);
    }

    /**
     * Returns true if the tree contains the specified element.
     *
     * @param e the element to search for
     * @return true if present, false otherwise
     */
    public boolean contains(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        Node<E> node = root;
        while (node != null) {
            int cmp = e.compareTo(node.item);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return true;
        }
        return false;
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

    // AVL balancing helpers

    private void updateHeight(Node<E> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int height(Node<E> node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node<E> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node<E> balance(Node<E> node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left); // LR case
            }
            return rotateRight(node); // LL case
        }

        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right); // RL case
            }
            return rotateLeft(node); // RR case
        }

        return node;
    }

    private Node<E> rotateRight(Node<E> y) {
        Node<E> x = y.left;
        Node<E> T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node<E> rotateLeft(Node<E> x) {
        Node<E> y = x.right;
        Node<E> T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }
}
