package br.edu.insper.desagil.backend.model.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.edu.insper.desagil.backend.core.Endpoint;
import br.edu.insper.desagil.backend.model.Aluno;
import br.edu.insper.desagil.backend.model.db.AlunoDAO;

public class AlunoEndpoint extends Endpoint<Aluno> {
	public AlunoEndpoint() {
		super("/aluno");
	}

	@Override
	public Aluno get(Map<String, String> args) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		String key = require(args, "matricula");
		return dao.retrieve(key);
	}

	@Override
	public List<Aluno> getList(Map<String, String> args) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		String arg = require(args, "matriculas");
		List<String> keys = split(arg, ",");
		return dao.retrieve(keys);
	}

	@Override
	public Map<String, Object> post(Map<String, String> args, Aluno aluno) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		Date date = dao.create(aluno);
		Map<String, Object> response = new HashMap<>();
		response.put("date", date);
		return response;
	}

	@Override
	public Map<String, Object> put(Map<String, String> args, Aluno aluno) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		Date date = dao.update(aluno);
		Map<String, Object> response = new HashMap<>();
		response.put("date", date);
		return response;
	}

	@Override
	public Map<String, Object> delete(Map<String, String> args) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		String key = require(args, "matricula");
		Date date = dao.delete(key);
		Map<String, Object> response = new HashMap<>();
		response.put("date", date);
		return response;
	}

	@Override
	public Map<String, Object> deleteList(Map<String, String> args) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		String arg = require(args, "matriculas");
		List<String> keys = split(arg, ",");
		List<Date> dates = dao.delete(keys);
		Iterator<String> ikey = keys.iterator();
		Iterator<Date> idate = dates.iterator();
		Map<String, Object> response = new HashMap<>();
		while (ikey.hasNext() && idate.hasNext()) {
			String key = ikey.next();
			Date date = idate.next();
			response.put(key, date);
		}
		return response;
	}
}
