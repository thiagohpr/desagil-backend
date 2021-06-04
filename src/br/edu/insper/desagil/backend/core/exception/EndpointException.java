package br.edu.insper.desagil.backend.core.exception;

public class EndpointException extends Exception {
	private static final long serialVersionUID = -7042454218210681570L;

	private final int sc;

	public EndpointException(int sc, String message) {
		super(message);
		this.sc = sc;
	}

	public final int getStatus() {
		return sc;
	}
}
