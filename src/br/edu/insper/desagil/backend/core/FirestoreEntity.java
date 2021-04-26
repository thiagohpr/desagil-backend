package br.edu.insper.desagil.backend.core;

public abstract class FirestoreEntity implements Entity<String> {
	@Override
	public abstract String key();
}
