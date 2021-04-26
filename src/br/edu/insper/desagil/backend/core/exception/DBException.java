package br.edu.insper.desagil.backend.core.exception;

public class DBException extends Exception {
	private static final long serialVersionUID = 8983407025617583638L;

	public DBException(String prefix, Exception exception) {
		super(prefix + ": " + exception.getMessage());
	}
}
