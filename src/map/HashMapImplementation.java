package map;

import java.util.Objects;

/**
 * A minimal production-grade HashMap implementation using separate chaining.
 * Supports constant-time average operations and automatic resizing.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
class HashMapImplementation<K, V> {

    /**
     * Node represents a key-value pair stored in the map.
     */
    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMapImplementation() {
        buckets = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
    }

    /**
     * Associates the specified value with the specified key.
     * If the key already exists, its value is updated.
     *
     * @param key the key
     * @param value the value
     * @return the previous value, or null if none
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "Key cannot be null");
        int index = indexFor(key);
        Node<K, V> node = buckets[index];

        while (node != null) {
            if (node.key.equals(key)) {
                V old = node.value;
                node.value = value;
                return old;
            }
            node = node.next;
        }

        buckets[index] = new Node<>(key, value, buckets[index]);
        size++;

        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }

        return null;
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key
     * @return the value, or null if not found
     */
    public V get(K key) {
        Objects.requireNonNull(key, "Key cannot be null");
        int index = indexFor(key);
        Node<K, V> node = buckets[index];

        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }

        return null;
    }

    /**
     * Removes the mapping for the specified key if present.
     *
     * @param key the key
     * @return the removed value, or null if not found
     */
    public V remove(K key) {
        Objects.requireNonNull(key, "Key cannot be null");
        int index = indexFor(key);
        Node<K, V> node = buckets[index];
        Node<K, V> prev = null;

        while (node != null) {
            if (node.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
            node = node.next;
        }

        return null;
    }

    /**
     * Returns true if the map contains the specified key.
     *
     * @param key the key
     * @return true if present, false otherwise
     */
    public boolean containsKey(K key) {
        return get(key) != null;
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
     * Removes all mappings from the map.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private int indexFor(K key) {
        return (key.hashCode() & 0x7fffffff) % buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = (Node<K, V>[]) new Node[oldBuckets.length * 2];
        size = 0;

        for (Node<K, V> node : oldBuckets) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }
}
