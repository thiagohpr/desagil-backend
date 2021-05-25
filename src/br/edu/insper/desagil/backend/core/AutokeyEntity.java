package br.edu.insper.desagil.backend.core;

public abstract class AutokeyEntity<K> implements Entity<K> {
	private K key;

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	@Override
	public K key() {
		return getKey();
	}
}
