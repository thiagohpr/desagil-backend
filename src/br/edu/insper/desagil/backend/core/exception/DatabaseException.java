package br.edu.insper.desagil.backend.core.exception;

public class DatabaseException extends Exception {
	private static final long serialVersionUID = 2444731027045903173L;

	public DatabaseException(String prefix, Exception exception) {
		super(prefix + ": " + exception.getMessage());
	}
}
