package br.edu.insper.desagil.backend.model.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.insper.desagil.backend.core.Endpoint;
import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.exception.DBException;
import br.edu.insper.desagil.backend.core.exception.DatabaseRequestException;
import br.edu.insper.desagil.backend.model.Aluno;
import br.edu.insper.desagil.backend.model.db.AlunoDAO;

public class AlunoEndpoint extends Endpoint<Aluno> {
	public AlunoEndpoint() {
		super("/aluno");
	}

	@Override
	public Aluno get(Map<String, String> args) throws APIException {
		Aluno aluno;
		AlunoDAO dao = new AlunoDAO();
		String key = extract(args, "matricula");
		try {
			aluno = dao.retrieve(key);
		} catch (DBException exception) {
			throw new DatabaseRequestException(exception);
		}
		return aluno;
	}

	@Override
	public List<Aluno> getList(Map<String, String> args) throws APIException {
		List<Aluno> alunos;
		AlunoDAO dao = new AlunoDAO();
		String arg = extract(args, "matriculas");
		List<String> keys = split(arg, ",");
		try {
			alunos = dao.retrieve(keys);
		} catch (DBException exception) {
			throw new DatabaseRequestException(exception);
		}
		return alunos;
	}

	@Override
	public Map<String, String> post(Map<String, String> args, Aluno aluno) throws APIException {
		Date date;
		AlunoDAO dao = new AlunoDAO();
		try {
			date = dao.create(aluno);
		} catch (DBException exception) {
			throw new DatabaseRequestException(exception);
		}
		Map<String, String> body = new HashMap<>();
		body.put("date", date.toString());
		return body;
	}

	@Override
	public Map<String, String> put(Map<String, String> args, Aluno aluno) throws APIException {
		Date date;
		AlunoDAO dao = new AlunoDAO();
		try {
			date = dao.update(aluno);
		} catch (DBException exception) {
			throw new DatabaseRequestException(exception);
		}
		Map<String, String> body = new HashMap<>();
		body.put("date", date.toString());
		return body;
	}

	@Override
	public Map<String, String> delete(Map<String, String> args) throws APIException {
		Date date;
		AlunoDAO dao = new AlunoDAO();
		String key = extract(args, "matricula");
		try {
			date = dao.delete(key);
		} catch (DBException exception) {
			throw new DatabaseRequestException(exception);
		}
		Map<String, String> body = new HashMap<>();
		body.put("date", date.toString());
		return body;
	}
}
