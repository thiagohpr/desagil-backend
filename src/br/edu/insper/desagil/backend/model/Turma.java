package br.edu.insper.desagil.backend.model;

import java.util.List;

import br.edu.insper.desagil.backend.core.firestore.AutokeyFirestoreDatum;

public class Turma extends AutokeyFirestoreDatum {
	private List<String> alunos;

	public List<String> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<String> alunos) {
		this.alunos = alunos;
	}
}
