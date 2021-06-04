package br.edu.insper.desagil.backend.core.exception;

import jakarta.servlet.http.HttpServletResponse;

public class NotSupportedException extends EndpointException {
	private static final long serialVersionUID = -3613567536849549109L;

	public NotSupportedException(String method) {
		super(HttpServletResponse.SC_METHOD_NOT_ALLOWED, method.toUpperCase() + " not supported");
	}
}
