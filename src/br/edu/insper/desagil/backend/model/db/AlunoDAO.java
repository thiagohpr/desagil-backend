package br.edu.insper.desagil.backend.model.db;

import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.firestore.FirestoreDAO;
import br.edu.insper.desagil.backend.model.Aluno;

public class AlunoDAO extends FirestoreDAO<Aluno> {
	public AlunoDAO() throws APIException {
		super("alunos");
	}
}
