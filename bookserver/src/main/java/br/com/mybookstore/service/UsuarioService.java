package br.com.mybookstore.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.mybookstore.domain.User;
import br.com.mybookstore.repository.UserRepository;

@Service
@Transactional
public class UsuarioService {
	
	UserRepository em;

    public UsuarioService(UserRepository em) {
        this.em = em;
    }

	public User registrar(User novoUsuario) {
		return em.save(novoUsuario);
	}

	public User buscarPorID(Long id) {
		return em.findById(id).get();
	}

	public Optional<User> buscarPorLogin(String login) {		
		return em.getUsuarioByLogin(login);		
	}

	public void atualizar(User usuario) {
		em.save(usuario);
	}

}
