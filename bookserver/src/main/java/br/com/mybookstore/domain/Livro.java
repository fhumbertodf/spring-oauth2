package br.com.mybookstore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Range;

@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	@Range(min = 0, max = 10)
	private int nota;

	@Deprecated
	Livro() {
	}

	public Livro(String titulo, int nota) {
		super();
		this.titulo = titulo;
		this.nota = nota;
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public int getNota() {
		return nota;
	}

}
