package br.com.mybookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	//@Override
	//protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//	auth.userDetailsService(this.userDetailsComponent).passwordEncoder(new SigeuPasswordEncoder());
	//}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	private static final String[] PUBLIC_MATCHERS = {
			"/v2/api-docs", 
			"/swagger-resources/**", 
			"/swagger-ui.html",	
			"/h2-console",
			"/api/usuarios" 
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
				.cors()
				.and()
					.authorizeRequests()					
					.antMatchers(PUBLIC_MATCHERS).permitAll()					
				.and()
					.formLogin()
					.loginPage("/login.html")
					.loginProcessingUrl("/login")					
					.permitAll()
				.and()
					.httpBasic().
				and().logout().logoutSuccessUrl("/login.html").permitAll();
	}
}
