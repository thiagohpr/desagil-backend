package br.edu.insper.desagil.backend.model.api;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.edu.insper.desagil.backend.core.Args;
import br.edu.insper.desagil.backend.core.Endpoint;
import br.edu.insper.desagil.backend.core.Result;
import br.edu.insper.desagil.backend.model.Aluno;
import br.edu.insper.desagil.backend.model.db.AlunoDAO;

public class AlunoEndpoint extends Endpoint<Aluno> {
	public AlunoEndpoint() {
		super("/aluno");
	}

	@Override
	public Aluno get(Args args) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		String key = args.get("matricula");
		return dao.retrieve(key);
	}

	@Override
	public List<Aluno> getList(Args args) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		List<String> keys = args.split("matriculas", ",");
		return dao.retrieve(keys);
	}

	@Override
	public Result post(Args args, Aluno aluno) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		Date date = dao.create(aluno);
		Result result = new Result();
		result.put("date", date);
		return result;
	}

	@Override
	public Result put(Args args, Aluno aluno) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		Date date = dao.update(aluno);
		Result result = new Result();
		result.put("date", date);
		return result;
	}

	@Override
	public Result delete(Args args) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		String key = args.get("matricula");
		Date date = dao.delete(key);
		Result result = new Result();
		result.put("date", date);
		return result;
	}

	@Override
	public Result deleteList(Args args) throws Exception {
		AlunoDAO dao = new AlunoDAO();
		List<String> keys = args.split("matriculas", ",");
		List<Date> dates = dao.delete(keys);
		Iterator<String> ikey = keys.iterator();
		Iterator<Date> idate = dates.iterator();
		Result result = new Result();
		while (ikey.hasNext() && idate.hasNext()) {
			String key = ikey.next();
			Date date = idate.next();
			result.put(key, date);
		}
		return result;
	}
}
