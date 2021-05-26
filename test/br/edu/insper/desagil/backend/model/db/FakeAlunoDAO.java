package br.edu.insper.desagil.backend.model.db;

import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.exception.DBException;
import br.edu.insper.desagil.backend.core.firestore.FirestoreDAO;
import br.edu.insper.desagil.backend.model.Aluno;

public class FakeAlunoDAO extends FirestoreDAO<Aluno> {
	public FakeAlunoDAO() throws DBException, APIException {
		super("fake_alunos");
	}
}
