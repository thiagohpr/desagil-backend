package br.edu.insper.desagil.backend.core.exception;

public class ConsistencyRequestException extends BadRequestException {
	private static final long serialVersionUID = 4554525928546667749L;

	public ConsistencyRequestException(String message) {
		super("Consistency error: " + message);
	}
}
