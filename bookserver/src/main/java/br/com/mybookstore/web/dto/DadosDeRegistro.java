package br.com.mybookstore.web.dto;

import javax.validation.constraints.NotEmpty;

public class DadosDeRegistro {

	@NotEmpty
	private String nome;

	@NotEmpty
	private String login;

	@NotEmpty
	private String senha;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
