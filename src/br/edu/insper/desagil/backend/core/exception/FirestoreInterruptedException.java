package br.edu.insper.desagil.backend.core.exception;

public class FirestoreInterruptedException extends DBException {
	private static final long serialVersionUID = -5697905500804413537L;

	public FirestoreInterruptedException(InterruptedException exception) {
		super("Execution interrupted", exception);
	}
}
