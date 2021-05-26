package br.edu.insper.desagil.backend.model.db;

import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.exception.DBException;
import br.edu.insper.desagil.backend.core.firestore.FirestoreDAO;
import br.edu.insper.desagil.backend.model.Turma;

public class FakeTurmaDAO extends FirestoreDAO<Turma> {
	public FakeTurmaDAO() throws DBException, APIException {
		super("fake_turmas");
	}
}
