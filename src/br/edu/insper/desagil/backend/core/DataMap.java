package br.edu.insper.desagil.backend.core;

import br.edu.insper.desagil.backend.core.exception.DBException;

public interface DataMap<K> {
	boolean has(K key) throws DBException;
	Object get(K key) throws DBException;
	void set(K key, Object value) throws DBException;
	void del(K key) throws DBException;
}
