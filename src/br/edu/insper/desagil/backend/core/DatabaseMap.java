package br.edu.insper.desagil.backend.core;

import br.edu.insper.desagil.backend.core.exception.DatabaseException;

public interface DatabaseMap<K> {
	boolean has(K key) throws DatabaseException;
	Object get(K key) throws DatabaseException;
	void put(K key, Object value) throws DatabaseException;
	void del(K key) throws DatabaseException;
}
