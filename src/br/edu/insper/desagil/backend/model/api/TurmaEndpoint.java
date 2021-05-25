package br.edu.insper.desagil.backend.model.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.edu.insper.desagil.backend.core.Endpoint;
import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.exception.ConsistencyRequestException;
import br.edu.insper.desagil.backend.core.exception.DBException;
import br.edu.insper.desagil.backend.core.exception.DatabaseRequestException;
import br.edu.insper.desagil.backend.model.Turma;
import br.edu.insper.desagil.backend.model.db.AlunoDAO;
import br.edu.insper.desagil.backend.model.db.TurmaDAO;

public class TurmaEndpoint extends Endpoint<Turma> {
	public TurmaEndpoint() {
		super("/turma");
	}

	private void check(Turma turma) throws DBException, APIException {
		AlunoDAO dao = new AlunoDAO();
		if (!dao.exists(turma.getAlunos())) {
			throw new ConsistencyRequestException("Nem todos os alunos existem");
		}
	}

	@Override
	public Turma get(Map<String, String> args) throws APIException {
		Turma turma;
		TurmaDAO dao = new TurmaDAO();
		String key = extract(args, "key");
		try {
			turma = dao.retrieve(key);
		} catch (DBException exception) {
			throw new DatabaseRequestException(exception);
		}
		return turma;
	}

	@Override
	public Map<String, String> post(Map<String, String> args, Turma turma) throws APIException {
		Date date;
		TurmaDAO dao = new TurmaDAO();
		try {
			check(turma);
			date = dao.create(turma);
		} catch (DBException exception) {
			throw new DatabaseRequestException(exception);
		}
		Map<String, String> body = new HashMap<>();
		body.put("date", date.toString());
		body.put("key", turma.getKey());
		return body;
	}

	@Override
	public Map<String, String> put(Map<String, String> args, Turma turma) throws APIException {
		Date date;
		TurmaDAO dao = new TurmaDAO();
		try {
			check(turma);
			date = dao.update(turma);
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
		TurmaDAO dao = new TurmaDAO();
		String key = extract(args, "key");
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
