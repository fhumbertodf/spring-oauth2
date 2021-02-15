package br.com.mybookstore.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Estante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User usuario;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "estante_id")
	private List<Livro> livros = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public boolean temLivros() {
		return livros.size() > 0;
	}

	public Collection<Livro> todosLivros() {
		return Collections.unmodifiableCollection(livros);
	}

	public void adicionar(Livro livro) {
		livros.add(livro);
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
}
