package br.edu.insper.desagil.backend.core.exception;

public class InternalException extends RuntimeException {
	private static final long serialVersionUID = -5140132672481805241L;

	public InternalException(String message) {
		super(message);
	}
}
