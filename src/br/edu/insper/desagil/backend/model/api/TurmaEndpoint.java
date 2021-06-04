package br.edu.insper.desagil.backend.model.api;

import java.util.Date;

import br.edu.insper.desagil.backend.core.Args;
import br.edu.insper.desagil.backend.core.Endpoint;
import br.edu.insper.desagil.backend.core.Result;
import br.edu.insper.desagil.backend.model.Turma;
import br.edu.insper.desagil.backend.model.db.TurmaDAO;

public class TurmaEndpoint extends Endpoint<Turma> {
	public TurmaEndpoint() {
		super("/turma");
	}

	@Override
	public Turma get(Args args) throws Exception {
		TurmaDAO dao = new TurmaDAO();
		String key = args.get("key");
		return dao.retrieve(key);
	}

	@Override
	public Result post(Args args, Turma turma) throws Exception {
		TurmaDAO dao = new TurmaDAO();
		Date date = dao.create(turma);
		Result response = new Result();
		response.put("date", date);
		response.put("key", turma.getKey());
		return response;
	}

	@Override
	public Result put(Args args, Turma turma) throws Exception {
		TurmaDAO dao = new TurmaDAO();
		Date date = dao.update(turma);
		Result response = new Result();
		response.put("date", date);
		return response;
	}

	@Override
	public Result delete(Args args) throws Exception {
		TurmaDAO dao = new TurmaDAO();
		String key = args.get("key");
		Date date = dao.delete(key);
		Result response = new Result();
		response.put("date", date);
		return response;
	}
}
