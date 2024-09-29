package me.a8kj.ww.parent.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

/**
 * A utility class for managing a map of key-value pairs.
 * Provides basic operations for adding, removing, and updating map entries,
 * as well as querying the map for keys and values.
 *
 * @param <K> the type of keys in the map.
 * @param <V> the type of values in the map.
 */
public class SimpleMapManager<K, V> {

    /**
     * The map being managed.
     */
    private Map<K, V> map;

    /**
     * Constructs a new SimpleMapManager with the specified initial map.
     * If the initial map is {@code null}, an empty map is created.
     *
     * @param initialMap the initial map to manage; may be {@code null}.
     */
    public SimpleMapManager(Map<K, V> initialMap) {
        this.map = initialMap != null ? initialMap : new HashMap<>();
    }

    public V getValue(String key) {
        return this.map.get(key);
    }

    /**
     * Adds a key-value pair to the map if the key is not already present.
     *
     * @param key   the key to add.
     * @param value the value associated with the key.
     */
    public void add(K key, V value) {
        this.map.putIfAbsent(key, value);
    }

    /**
     * Removes a key-value pair from the map by key.
     *
     * @param key the key of the entry to remove.
     */
    public void remove(K key) {
        this.map.remove(key);
    }

    /**
     * Clears all entries from the map.
     */
    public void clearAll() {
        this.map.clear();
    }

    /**
     * Checks if the map contains a specific key.
     *
     * @param key the key to check for.
     * @return {@code true} if the map contains the key; {@code false} otherwise.
     */
    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    /**
     * Checks if the map contains a specific value.
     *
     * @param value the value to check for.
     * @return {@code true} if the map contains the value; {@code false} otherwise.
     */
    public boolean containsValue(V value) {
        return this.map.containsValue(value);
    }

    /**
     * Updates the value associated with a specific key if the key is present in the
     * map.
     *
     * @param key   the key of the entry to update.
     * @param value the new value to associate with the key.
     */
    public void updateValue(K key, V value) {
        if (this.map.containsKey(key)) {
            this.map.put(key, value);
        }
    }

    /**
     * Retrieves the key associated with a specific value.
     *
     * @param value the value to search for.
     * @return an {@link Optional} containing the key if found; an empty
     *         {@link Optional} otherwise.
     */
    public Optional<K> getKeyFromValue(V value) {
        return this.map.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    /**
     * Updates the map with a new map.
     * If the new map is {@code null}, an empty map is used.
     *
     * @param newMap the new map to set; may be {@code null}.
     */
    public void updateMap(Map<K, V> newMap) {
        this.map = newMap != null ? newMap : new HashMap<>();
    }

    /**
     * Returns the map being managed.
     *
     * @return the current map.
     */
    public Map<K, V> getMap() {
        return map;
    }

    /**
     * Returns the set of keys in the map.
     *
     * @return a {@link Set} of keys in the map.
     */
    public Set<K> getKeys() {
        return map.keySet();
    }

    /**
     * Returns the set of entries (key-value pairs) in the map.
     *
     * @return a {@link Set} of entries in the map.
     */
    public Set<Entry<K, V>> getEntrySet() {
        return this.map.entrySet();
    }

    /**
     * Returns the collection of values in the map.
     *
     * @return a {@link Collection} of values in the map.
     */
    public Collection<V> getValues() {
        return map.values();
    }
}