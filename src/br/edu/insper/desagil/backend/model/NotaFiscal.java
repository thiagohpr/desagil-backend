package br.edu.insper.desagil.backend.model;

import br.edu.insper.desagil.backend.core.firestore.FirestoreEntity;

public class NotaFiscal extends FirestoreEntity {
	private int id;
	private String nome;
	private int valor;

	public NotaFiscal() {

	}
	public NotaFiscal(int id, String nome, int valor) {
		super();
		this.id = id;
		this.nome = nome;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String key() {
		return Integer.toString(id);
	}
}
