package br.edu.insper.desagil.backend.model.api;

import java.util.HashMap;
import java.util.Map;

import br.edu.insper.desagil.backend.core.Endpoint;
import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.exception.DBException;
import br.edu.insper.desagil.backend.model.NotaFiscal;
import br.edu.insper.desagil.backend.model.db.NotaFiscalDAO;

public class NotaFiscalEndpoint extends Endpoint<NotaFiscal> {
	public NotaFiscalEndpoint() {
		super("/nota");
	}

	@Override
	public NotaFiscal get(Map<String, String> args) throws APIException {
		NotaFiscalDAO dao = new NotaFiscalDAO();
		String key = args.get("id");
		NotaFiscal nf = null;
		try {
			nf = dao.retrieve(key);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return nf;
	}

	@Override
	public Map<String, String> post(Map<String, String> args, NotaFiscal body) throws APIException {
		NotaFiscalDAO dao = new NotaFiscalDAO();
		try {
			dao.create(body);
		} catch (DBException e) {
			e.printStackTrace();
		}
		Map<String, String> resposta = new HashMap<>();
		resposta.put("mensagem", "gravou!");
		return resposta;
	}

	@Override
	public Map<String, String> put(Map<String, String> args, NotaFiscal body) throws APIException {
		NotaFiscalDAO dao = new NotaFiscalDAO();
		System.out.println(body.getId());
		System.out.println(body.getNome());
		System.out.println(body.getValor());
		return null;
	}

	@Override
	public Map<String, String> delete(Map<String, String> args) throws APIException {
		NotaFiscalDAO dao = new NotaFiscalDAO();
		System.out.println(args.get("id"));
		return null;
	}
}
