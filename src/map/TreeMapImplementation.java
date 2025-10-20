package map;

import java.util.Comparator;
import java.util.Objects;
import java.util.NoSuchElementException;

/**
 * A minimal TreeMap implementation using a binary search tree (BST).
 * Supports sorted key-value mappings with customizable comparator or natural ordering.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
class TreeMapImplementation<K, V> {

    /**
     * Node represents a single key-value pair in the tree.
     */
    private static class Node<K, V> {
        /** The key associated with this node. */
        K key;
        /** The value associated with this node. */
        V value;
        /** Reference to the left child node. */
        Node<K, V> left;
        /** Reference to the right child node. */
        Node<K, V> right;

        /**
         * Constructs a new node with the given key and value.
         *
         * @param key the key
         * @param value the value
         */
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /** The root node of the BST. */
    private Node<K, V> root;
    /** The number of key-value pairs in the map. */
    private int size;
    /** Comparator used to order the keys, or null for natural ordering. */
    private final Comparator<? super K> comparator;

    /**
     * Constructs an empty TreeMap with natural ordering.
     * Keys must implement {@link Comparable}.
     */
    public TreeMapImplementation() {
        this.comparator = null;
    }

    /**
     * Constructs an empty TreeMap with the specified comparator.
     *
     * @param comparator the comparator to determine key ordering
     */
    public TreeMapImplementation(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    /**
     * Associates the specified value with the specified key.
     * If the key already exists, its value is replaced.
     *
     * @param key the key to insert
     * @param value the value to associate
     * @return the inserted value
     * @throws NullPointerException if the key is null
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "Key cannot be null");
        root = insert(root, key, value);
        return value;
    }

    /**
     * Inserts a key-value pair into the subtree rooted at the given node.
     *
     * @param node the current subtree root
     * @param key the key to insert
     * @param value the value to insert
     * @return the updated subtree root
     */
    private Node<K, V> insert(Node<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }
        int cmp = compare(key, node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key to search for
     * @return the value, or null if not found
     */
    public V get(K key) {
        Node<K, V> node = find(root, key);
        return node != null ? node.value : null;
    }

    /**
     * Returns true if the map contains the specified key.
     *
     * @param key the key to check
     * @return true if present, false otherwise
     */
    public boolean containsKey(K key) {
        return find(root, key) != null;
    }

    /**
     * Finds the node associated with the given key in the subtree.
     *
     * @param node the current subtree root
     * @param key the key to search for
     * @return the node if found, or null
     */
    private Node<K, V> find(Node<K, V> node, K key) {
        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return node;
        }
        return null;
    }

    /**
     * Removes the mapping for the specified key if present.
     *
     * @param key the key to remove
     * @return the removed value, or null if not found
     * @throws NullPointerException if the key is null
     */
    public V remove(K key) {
        Objects.requireNonNull(key, "Key cannot be null");
        Node<K, V> node = find(root, key);
        if (node == null) return null;
        root = delete(root, key);
        size--;
        return node.value;
    }

    /**
     * Deletes the node with the specified key from the subtree.
     *
     * @param node the current subtree root
     * @param key the key to delete
     * @return the updated subtree root
     */
    private Node<K, V> delete(Node<K, V> node, K key) {
        if (node == null) return null;
        int cmp = compare(key, node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node<K, V> min = getMin(node.right);
            node.key = min.key;
            node.value = min.value;
            node.right = delete(node.right, min.key);
        }
        return node;
    }

    /**
     * Returns the smallest key in the map.
     *
     * @return the minimum key
     * @throws NoSuchElementException if the map is empty
     */
    public K minKey() {
        if (root == null) throw new NoSuchElementException("Tree is empty");
        return getMin(root).key;
    }

    /**
     * Returns the largest key in the map.
     *
     * @return the maximum key
     * @throws NoSuchElementException if the map is empty
     */
    public K maxKey() {
        if (root == null) throw new NoSuchElementException("Tree is empty");
        Node<K, V> node = root;
        while (node.right != null) node = node.right;
        return node.key;
    }

    /**
     * Returns the node with the smallest key in the subtree.
     *
     * @param node the subtree root
     * @return the node with the minimum key
     */
    private Node<K, V> getMin(Node<K, V> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    /**
     * Returns the number of key-value mappings in the map.
     *
     * @return the size of the map
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the map contains no key-value mappings.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all key-value mappings from the map.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Compares two keys using the comparator or natural ordering.
     *
     * @param a the first key
     * @param b the second key
     * @return a negative value if a < b, zero if equal, positive if a > b
     */
    private int compare(K a, K b) {
        if (comparator != null) return comparator.compare(a, b);
        @SuppressWarnings("unchecked")
        Comparable<? super K> cmp = (Comparable<? super K>) a;
        return cmp.compareTo(b);
    }
}
