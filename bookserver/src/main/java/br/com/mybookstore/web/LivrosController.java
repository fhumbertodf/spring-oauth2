package br.com.mybookstore.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.mybookstore.domain.DadosDoLivro;
import br.com.mybookstore.domain.Livro;
import br.com.mybookstore.domain.User;
import br.com.mybookstore.security.ResourceOwner;
import br.com.mybookstore.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class LivrosController {

	@Autowired
	private UsuarioService usuarios;

	@GetMapping("/livros/principal")
	public ModelAndView principal() {
		ModelAndView mv = new ModelAndView("livros/principal");

		mv.addObject("dadosDoLivro", new DadosDoLivro());
		mv.addObject("livros", donoDosLivros().getEstante().todosLivros());

		return mv;
	}

	@PostMapping("/livros/principal")
	public ModelAndView adicionarLivro(@Valid DadosDoLivro dadosDoLivro, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("livros/principal");

		User usuario = donoDosLivros();

		if (bindingResult.hasErrors()) {
			mv.addObject("livros", usuario.getEstante().todosLivros());
			mv.addObject("dadosDoLivro", dadosDoLivro);
			return mv;
		}

		Livro novoLivro = new Livro(dadosDoLivro.getTitulo(), dadosDoLivro.getNota());
		usuario.getEstante().adicionar(novoLivro);

		usuarios.atualizar(usuario);

		mv.addObject("livros", usuario.getEstante().todosLivros());
		mv.addObject("dadosDoLivro", new DadosDoLivro());

		return mv;
	}

	private User donoDosLivros() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ResourceOwner donoDosLivros = (ResourceOwner) authentication.getPrincipal();

		return usuarios.buscarPorID(donoDosLivros.getId());
	}
}
