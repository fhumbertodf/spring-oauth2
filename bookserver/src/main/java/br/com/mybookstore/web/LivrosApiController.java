package br.com.mybookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mybookstore.domain.Estante;
import br.com.mybookstore.domain.User;
import br.com.mybookstore.security.ResourceOwner;
import br.com.mybookstore.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class LivrosApiController {

    @Autowired
    private UsuarioService usuarios;

    @GetMapping("/livros")
    public ResponseEntity<?> livros() {

        Estante estante = donoDosLivros().getEstante();
        if (estante.temLivros()) {
            return new ResponseEntity<>(estante.todosLivros(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    private User donoDosLivros() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ResourceOwner donoDosLivros = (ResourceOwner) authentication.getPrincipal();

        return usuarios.buscarPorID(donoDosLivros.getId());
    }
}
