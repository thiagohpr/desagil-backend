package br.edu.insper.desagil.backend.model;

import java.util.List;

import br.edu.insper.desagil.backend.firestore.FirestoreEntity;

public class Turma extends FirestoreEntity {
	private String codigo;
	private List<String> alunos;

	public Turma() {
	}
	public Turma(List<String> alunos) {
		this.alunos = alunos;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public List<String> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<String> alunos) {
		this.alunos = alunos;
	}

	@Override
	public String key() {
		return codigo;
	}
}
