package br.edu.insper.desagil.backend.core;

public interface FirestoreEntity extends Entity<String> {
	@Override
	public abstract String key();
}
