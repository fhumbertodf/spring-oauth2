package br.com.mybookstore.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mybookstore.domain.User;
import br.com.mybookstore.service.UsuarioService;
import br.com.mybookstore.web.dto.DadosDeRegistro;

@RestController
@RequestMapping("/api")
public class UsuariosController {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
   /**
	 * Método que recebe os dados do formulário de cadastro de usuário
	 * 
	 * @param dadosDeRegistro
	 * @return
	 */
	@PostMapping("/usuarios")
	public ResponseEntity<?> registrar(@Valid DadosDeRegistro dadosDeRegistro, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("usuarios/cadastro", HttpStatus.OK);			
		}

		// cria um usuario no sistema
		User usuario = new User();
		usuario.setLogin(dadosDeRegistro.getLogin());
		usuario.setName(dadosDeRegistro.getNome());
		usuario.setPassword(passwordEncoder.encode(dadosDeRegistro.getSenha()));

		// persiste os dados do usuario
		usuarioService.registrar(usuario);

		// autentica o usuário recem-registrado para que o mesmo nao precise fazer o
		// login		
		mantemUsuarioAutenticado(dadosDeRegistro.getLogin(), dadosDeRegistro.getSenha());

		// usuário cadastrado é redirecionado para página de controle de livros
		return new ResponseEntity<String>("redirect:/livros/principal", HttpStatus.OK);
	}

	/**
	 * Esse método é usado apenas para adicionar o usuário recem cadastrado na
	 * sessão do Spring Security para que o usuário não precise se autenticar assim
	 * que se cadastra.
	 * 
	 * @param authenticationManager
	 * @param usuario
	 */
	private void mantemUsuarioAutenticado(String login, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, password);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
