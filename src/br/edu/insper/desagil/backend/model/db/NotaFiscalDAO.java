package br.edu.insper.desagil.backend.model.db;

import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.firestore.FirestoreDAO;
import br.edu.insper.desagil.backend.model.NotaFiscal;

public class NotaFiscalDAO extends FirestoreDAO<NotaFiscal> {

	public NotaFiscalDAO() throws APIException {
		super("notas");
	}

}
