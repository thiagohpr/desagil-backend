package br.edu.insper.desagil.backend.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.edu.insper.desagil.backend.core.exception.InternalException;

public final class Args extends HashMap<String, String> {
	private static final long serialVersionUID = -5172276765838531270L;

	public final String get(String name) {
		String value = super.get(name);
		if (value == null) {
			throw new InternalException("Arg " + name + " not found");
		}
		return value;
	}

	public final List<String> split(String name, String regex) {
		String value = get(name);
		String[] values = value.split(regex);
		return Arrays.asList(values);
	}
}
