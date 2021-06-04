package br.edu.insper.desagil.backend.core;

import java.util.HashMap;

import br.edu.insper.desagil.backend.core.exception.InternalException;

public final class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = 3801845786710561283L;

	public final Object put(String name, Object value) {
		if (name.isBlank()) {
			throw new InternalException("Blank results not allowed");
		}
		return super.put(name, value);
	}
}
