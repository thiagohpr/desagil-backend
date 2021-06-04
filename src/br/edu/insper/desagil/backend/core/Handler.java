package br.edu.insper.desagil.backend.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.reflections.Reflections;

import br.edu.insper.desagil.backend.core.exception.BadRequestException;
import br.edu.insper.desagil.backend.core.exception.EndpointException;
import br.edu.insper.desagil.backend.core.exception.NotFoundException;
import br.edu.insper.desagil.backend.core.exception.NotSupportedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("rawtypes")
public class Handler extends AbstractHandler {
	private final Map<String, Endpoint> endpoints;

	public Handler() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
		super();
		this.endpoints = new HashMap<>();
		Reflections reflections = new Reflections("br.edu.insper.desagil.backend");
		for (Class<?> klass : reflections.getSubTypesOf(Endpoint.class)) {
			Constructor<?> constructor = klass.getConstructor();
			Endpoint context = (Endpoint) constructor.newInstance();
			this.endpoints.put(context.getURI(), context);
		}
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String responseBody;
		try {
			String uri = request.getRequestURI();
			int length = uri.length();
			boolean isList = length > 5 && uri.endsWith("/list");
			if (isList) {
				uri = uri.substring(0, length - 5);
			}
			Endpoint endpoint = endpoints.get(uri);
			if (endpoint == null) {
				throw new NotFoundException("Endpoint " + uri + " not found");
			}

			Map<String, String[]> map = request.getParameterMap();
			Args args = new Args();
			for (String name : map.keySet()) {
				if (name.isBlank()) {
					throw new BadRequestException("Blank args not allowed");
				}
				String[] values = map.get(name);
				if (values.length < 1) {
					throw new BadRequestException("Arg " + name + " must have a value");
				}
				if (values.length > 1) {
					throw new BadRequestException("Arg " + name + " must have only one value");
				}
				String value = values[0];
				if (value.isBlank()) {
					throw new BadRequestException("Arg " + name + " cannot be blank");
				}
				args.put(name, value);
			}

			String line;
			BufferedReader reader = request.getReader();
			StringBuilder builder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append('\n');
			}
			String requestBody = builder.toString();

			String method = request.getMethod();
			switch (method) {
			case "GET":
				responseBody = endpoint.doGet(args, isList);
				break;
			case "POST":
				responseBody = endpoint.doPost(args, requestBody);
				break;
			case "PUT":
				responseBody = endpoint.doPut(args, requestBody);
				break;
			case "DELETE":
				responseBody = endpoint.doDelete(args, isList);
				break;
			case "OPTIONS":
				responseBody = "";
				break;
			default:
				throw new NotSupportedException(method);
			}
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json");
		} catch (EndpointException exception) {
			responseBody = exception.getMessage();
			response.setStatus(exception.getStatus());
			response.setContentType("text/plain");
		} catch (Exception exception) {
			exception.printStackTrace();
			responseBody = "Internal server error";
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
		}
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		PrintWriter writer = response.getWriter();
		writer.println(responseBody);
		baseRequest.setHandled(true);
	}
}
