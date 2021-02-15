package br.com.mybookstore.security;

import java.util.Locale;
import java.util.Optional;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mybookstore.domain.User;
import br.com.mybookstore.repository.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Service	
public class DomainUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

	private final UserRepository userRepository;

	public DomainUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override	
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);

		if (new EmailValidator().isValid(login, null)) {
			Optional<User> usuario = userRepository.getUsuarioByLogin(login);
			if (usuario.isPresent()) {
				return new ResourceOwner(usuario.get());
			} else {
				throw new UsernameNotFoundException("usuario não autorizado");
			}
		}

		String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		Optional<User> usuario = userRepository.getUsuarioByLogin(lowercaseLogin);
		if (usuario.isPresent()) {
			return new ResourceOwner(usuario.get());
		} else {
			throw new UsernameNotFoundException("usuario não autorizado");
		}
	}
}
