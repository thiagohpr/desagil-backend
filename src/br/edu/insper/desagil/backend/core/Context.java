package br.edu.insper.desagil.backend.core;

import java.util.Map;

public abstract class Context {
	private final String uri;

	protected Context(String uri) {
		this.uri = uri;
	}

	public final String getURI() {
		return uri;
	}

	public abstract String doGet(Map<String, String> args, boolean isList) throws Exception;
	public abstract String doPost(Map<String, String> args, String body) throws Exception;
	public abstract String doPut(Map<String, String> args, String body) throws Exception;
	public abstract String doDelete(Map<String, String> args, boolean isList) throws Exception;
}
