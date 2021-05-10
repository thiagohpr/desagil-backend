package br.edu.insper.desagil.backend.core.firestore;

import br.edu.insper.desagil.backend.core.Entity;

public interface FirestoreEntity extends Entity<String> {
	@Override
	public abstract String key();
}
