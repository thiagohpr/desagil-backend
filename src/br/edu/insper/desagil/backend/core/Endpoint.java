package br.edu.insper.desagil.backend.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import br.edu.insper.desagil.backend.core.exception.BadRequestException;
import br.edu.insper.desagil.backend.core.exception.NotImplementedException;

public abstract class Endpoint<T> {
	private final String uri;
	private final Class<T> klass;
	private final Gson gson;

	@SuppressWarnings("unchecked")
	protected Endpoint(String uri) {
		this.uri = uri;

		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();
		this.klass = (Class<T>) types[0];

		this.gson = new Gson();
	}

	protected T get(Args args) throws Exception {
		throw new NotImplementedException("get");
	}

	protected List<T> getList(Args args) throws Exception {
		throw new NotImplementedException("get");
	}

	protected Result post(Args args, T body) throws Exception {
		throw new NotImplementedException("post");
	}

	protected Result put(Args args, T body) throws Exception {
		throw new NotImplementedException("put");
	}

	protected Result delete(Args args) throws Exception {
		throw new NotImplementedException("delete");
	}

	protected Result deleteList(Args args) throws Exception {
		throw new NotImplementedException("delete");
	}

	public final String getURI() {
		return uri;
	}

	public final String doGet(Args args, boolean isList) throws Exception {
		if (isList) {
			return gson.toJson(getList(args));
		} else {
			return gson.toJson(get(args));
		}
	}

	public final String doPost(Args args, String requestBody) throws Exception {
		T body;
		try {
			body = gson.fromJson(requestBody, klass);
		} catch (JsonSyntaxException exception) {
			throw new BadRequestException("Invalid POST body: " + exception.getMessage());
		}
		if (body == null) {
			throw new BadRequestException("POST must have a body");
		}
		return gson.toJson(post(args, body));
	}

	public final String doPut(Args args, String requestBody) throws Exception {
		T body;
		try {
			body = gson.fromJson(requestBody, klass);
		} catch (JsonSyntaxException exception) {
			throw new BadRequestException("Invalid PUT body: " + exception.getMessage());
		}
		if (body == null) {
			throw new BadRequestException("PUT must have a body");
		}
		return gson.toJson(put(args, body));
	}

	public final String doDelete(Args args, boolean isList) throws Exception {
		if (isList) {
			return gson.toJson(deleteList(args));
		} else {
			return gson.toJson(delete(args));
		}
	}
}
