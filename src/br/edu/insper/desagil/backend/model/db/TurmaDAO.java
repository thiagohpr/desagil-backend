package br.edu.insper.desagil.backend.model.db;

import br.edu.insper.desagil.backend.core.firestore.FirestoreDAO;
import br.edu.insper.desagil.backend.model.Turma;

public class TurmaDAO extends FirestoreDAO<Turma> {
	public TurmaDAO() {
		super("turmas");
	}
}
