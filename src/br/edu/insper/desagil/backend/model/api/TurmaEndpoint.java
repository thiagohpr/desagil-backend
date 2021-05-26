package br.edu.insper.desagil.backend.model.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.edu.insper.desagil.backend.core.Endpoint;
import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.exception.ConsistencyRequestException;
import br.edu.insper.desagil.backend.core.exception.DBException;
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
	public Turma get(Map<String, String> args) throws Exception {
		TurmaDAO dao = new TurmaDAO();
		String key = require(args, "key");
		return dao.retrieve(key);
	}

	@Override
	public Map<String, Object> post(Map<String, String> args, Turma turma) throws Exception {
		check(turma);
		TurmaDAO dao = new TurmaDAO();
		Date date = dao.create(turma);
		Map<String, Object> response = new HashMap<>();
		response.put("date", date);
		response.put("key", turma.getKey());
		return response;
	}

	@Override
	public Map<String, Object> put(Map<String, String> args, Turma turma) throws Exception {
		check(turma);
		TurmaDAO dao = new TurmaDAO();
		Date date = dao.update(turma);
		Map<String, Object> response = new HashMap<>();
		response.put("date", date);
		return response;
	}

	@Override
	public Map<String, Object> delete(Map<String, String> args) throws Exception {
		TurmaDAO dao = new TurmaDAO();
		String key = require(args, "key");
		Date date = dao.delete(key);
		Map<String, Object> response = new HashMap<>();
		response.put("date", date);
		return response;
	}
}
