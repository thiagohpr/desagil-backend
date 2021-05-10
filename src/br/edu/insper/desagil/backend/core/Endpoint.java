package br.edu.insper.desagil.backend.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.exception.BadRequestException;
import br.edu.insper.desagil.backend.core.exception.MethodNotImplementedException;

public abstract class Endpoint<T> extends Context {
	private final Class<T> klass;
	private final Gson gson;

	@SuppressWarnings("unchecked")
	protected Endpoint(String uri) {
		super(uri);

		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();
		this.klass = (Class<T>) types[0];

		this.gson = new Gson();
	}

	protected String extract(Map<String, String> args, String key) throws APIException {
		String value = args.get(key);
		if (value == null) {
			throw new BadRequestException("Key " + key + " not found");
		}
		return value;
	}

	@Override
	public final String doGet(Map<String, String> args) throws APIException {
		return gson.toJson(get(args));
	}

	@Override
	public final String doPost(Map<String, String> args, String body) throws APIException {
		T value;
		try {
			value = gson.fromJson(body, klass);
		} catch (JsonSyntaxException exception) {
			throw new BadRequestException("POST body must be an object");
		}
		if (value == null) {
			throw new BadRequestException("POST request must have a body");
		}
		return gson.toJson(post(args, value));
	}

	@Override
	public final String doPut(Map<String, String> args, String body) throws APIException {
		T value;
		try {
			value = gson.fromJson(body, klass);
		} catch (JsonSyntaxException exception) {
			throw new BadRequestException("PUT body must be an object");
		}
		if (value == null) {
			throw new BadRequestException("PUT request must have a body");
		}
		return gson.toJson(put(args, value));
	}

	@Override
	public final String doDelete(Map<String, String> args) throws APIException {
		return gson.toJson(delete(args));
	}

	protected T get(Map<String, String> args) throws APIException {
		throw new MethodNotImplementedException("get");
	}

	protected Map<String, String> post(Map<String, String> args, T body) throws APIException {
		throw new MethodNotImplementedException("post");
	}

	protected Map<String, String> put(Map<String, String> args, T body) throws APIException {
		throw new MethodNotImplementedException("put");
	}

	protected Map<String, String> delete(Map<String, String> args) throws APIException {
		throw new MethodNotImplementedException("delete");
	}
}
