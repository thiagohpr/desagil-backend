package br.edu.insper.desagil.backend.core.firestore.exception;

import java.util.concurrent.ExecutionException;

import br.edu.insper.desagil.backend.core.exception.DBException;

public class FirestoreExecutionException extends DBException {
	private static final long serialVersionUID = 3825564659925631367L;

	public FirestoreExecutionException(ExecutionException exception) {
		super("Execution failed", exception);
	}
}
