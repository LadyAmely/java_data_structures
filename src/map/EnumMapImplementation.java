package map;

import java.util.*;

/**
 * A minimal EnumMap implementation using an array indexed by enum ordinal.
 * Provides fast access and compact memory usage for enum keys.
 *
 * @param <K> the enum type used as keys
 * @param <V> the type of mapped values
 */
class EnumMapImplementation<K extends Enum<K>, V> {

    private final Class<K> keyType;
    private final K[] universe;
    private final Object[] values;
    private int size;

    /**
     * Constructs an empty EnumMap for the specified enum type.
     *
     * @param keyType the class of the enum key
     */
    @SuppressWarnings("unchecked")
    public EnumMapImplementation(Class<K> keyType) {
        this.keyType = Objects.requireNonNull(keyType, "Enum type cannot be null");
        this.universe = keyType.getEnumConstants();
        if (universe == null) {
            throw new IllegalArgumentException("Provided class is not an enum");
        }
        this.values = new Object[universe.length];
        this.size = 0;
    }

    /**
     * Associates the specified value with the specified key.
     * If the key already exists, its value is replaced.
     *
     * @param key the enum key
     * @param value the value to associate
     * @return the previous value, or null if none
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "Key cannot be null");
        int index = key.ordinal();
        @SuppressWarnings("unchecked")
        V old = (V) values[index];
        values[index] = value;
        if (old == null && value != null) size++;
        if (old != null && value == null) size--;
        return old;
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the enum key
     * @return the value, or null if not present
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return (V) values[key.ordinal()];
    }

    /**
     * Removes the mapping for the specified key if present.
     *
     * @param key the enum key
     * @return the removed value, or null if not found
     */
    @SuppressWarnings("unchecked")
    public V remove(K key) {
        Objects.requireNonNull(key, "Key cannot be null");
        int index = key.ordinal();
        V old = (V) values[index];
        if (old != null) {
            values[index] = null;
            size--;
        }
        return old;
    }

    /**
     * Returns true if the map contains the specified key.
     *
     * @param key the enum key
     * @return true if present, false otherwise
     */
    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return values[key.ordinal()] != null;
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
     * Removes all mappings from the map.
     */
    public void clear() {
        Arrays.fill(values, null);
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * @return a set of keys
     */
    public Set<K> keySet() {
        Set<K> keys = new LinkedHashSet<>();
        for (int i = 0; i < universe.length; i++) {
            if (values[i] != null) {
                keys.add(universe[i]);
            }
        }
        return keys;
    }
}
