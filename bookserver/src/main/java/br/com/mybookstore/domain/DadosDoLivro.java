package br.com.mybookstore.domain;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

public class DadosDoLivro {

	@NotEmpty
	private String titulo;

	@Range(min = 0, max = 10)
	private int nota;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "DadosDoLivro [titulo=" + titulo + ", nota=" + nota + "]";
	}

}