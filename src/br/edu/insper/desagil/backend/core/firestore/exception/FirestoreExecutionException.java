package br.edu.insper.desagil.backend.core.firestore.exception;

import java.util.concurrent.ExecutionException;

import br.edu.insper.desagil.backend.core.exception.DatabaseException;

public class FirestoreExecutionException extends DatabaseException {
	private static final long serialVersionUID = -5735895412390449890L;

	public FirestoreExecutionException(ExecutionException exception) {
		super("Firestore execution failed", exception);
	}
}
