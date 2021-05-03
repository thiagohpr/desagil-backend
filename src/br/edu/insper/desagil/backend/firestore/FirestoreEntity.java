package br.edu.insper.desagil.backend.firestore;

import br.edu.insper.desagil.backend.core.Entity;

public abstract class FirestoreEntity implements Entity<String> {
	@Override
	public abstract String key();
}
