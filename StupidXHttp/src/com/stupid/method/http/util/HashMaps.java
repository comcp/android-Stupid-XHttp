package com.stupid.method.http.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMaps<K, V> implements Map<K, V> {
	private Map<K, V> map;

	HashMaps() {
		setMap(new HashMap<K, V>());
	}

	HashMaps(Map<K, V> map) {
		if (map == null)
			setMap(new HashMap<K, V>());
		else
			this.map = map;
	}

	public void clear() {
		getMap().clear();
	}

	public boolean containsKey(Object key) {
		return getMap().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return getMap().containsValue(value);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return getMap().entrySet();
	}

	public boolean equals(Object object) {
		return getMap().equals(object);
	}

	public V get(Object key) {
		return (V) getMap().get(key);
	}

	public int hashCode() {
		return getMap().hashCode();
	}

	public boolean isEmpty() {
		return getMap().isEmpty();
	}

	public Set<K> keySet() {
		return getMap().keySet();
	}

	public V put(K key, V value) {
		return (V) getMap().put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		getMap().putAll((Map<? extends K, ? extends V>) m);
	}

	public V remove(Object key) {
		return (V) getMap().remove(key);
	}

	public int size() {
		return getMap().size();
	}

	public Collection<V> values() {
		return getMap().values();
	}

	public HashMaps<K, V> add(K key, V value) {
		put(key, value);
		return this;
	}

	public Map<K, V> getMap() {
		return map;
	}

	public void setMap(HashMap<K, V> map) {
		this.map = map;
	}

}
