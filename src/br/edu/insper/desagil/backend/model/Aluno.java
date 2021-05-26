package br.edu.insper.desagil.backend.model;

import br.edu.insper.desagil.backend.core.firestore.FirestoreDatum;

public class Aluno extends FirestoreDatum {
	private int matricula;
	private String nome;
	private boolean regular;
	private double cr;

	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isRegular() {
		return regular;
	}
	public void setRegular(boolean regular) {
		this.regular = regular;
	}
	public double getCr() {
		return cr;
	}
	public void setCr(double cr) {
		this.cr = cr;
	}

	@Override
	public String key() {
		return Integer.toString(matricula);
	}
}
