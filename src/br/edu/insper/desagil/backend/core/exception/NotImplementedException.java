package br.edu.insper.desagil.backend.core.exception;

import jakarta.servlet.http.HttpServletResponse;

public class NotImplementedException extends EndpointException {
	private static final long serialVersionUID = -415001212388822691L;

	public NotImplementedException(String method) {
		super(HttpServletResponse.SC_METHOD_NOT_ALLOWED, method.toUpperCase() + " not implemented");
	}
}
