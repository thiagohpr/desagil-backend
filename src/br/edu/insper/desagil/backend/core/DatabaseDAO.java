package br.edu.insper.desagil.backend.core;

import java.util.Date;
import java.util.List;

import br.edu.insper.desagil.backend.core.exception.DatabaseException;

public interface DatabaseDAO<K, V> {
	boolean exists(K key) throws DatabaseException;
	boolean exists(List<K> keys) throws DatabaseException;
	Date create(V value) throws DatabaseException;
	V retrieve(K key) throws DatabaseException;
	List<V> retrieve(List<K> keys) throws DatabaseException;
	List<V> retrieveLt(String name, Object value) throws DatabaseException;
	List<V> retrieveLeq(String name, Object value) throws DatabaseException;
	List<V> retrieveEq(String name, Object value) throws DatabaseException;
	List<V> retrieveGt(String name, Object value) throws DatabaseException;
	List<V> retrieveGeq(String name, Object value) throws DatabaseException;
	List<V> retrieveIn(String name, List<Object> values) throws DatabaseException;
	List<V> retrieveAll() throws DatabaseException;
	Date update(V value) throws DatabaseException;
	Date delete(K key) throws DatabaseException;
	List<Date> delete(List<K> keys) throws DatabaseException;
	List<Date> deleteAll() throws DatabaseException;
}
