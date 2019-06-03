package com.example.productapp;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class ProductAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAppApplication.class, args);
	}
}

@Controller
class ProductController {

	@GetMapping(path = "/products")
	public String getProducts(Model model) {

		model.addAttribute("products", Arrays.asList("iPad", "iPhone", "iPod", "iMac"));
		return "products";
	}

	@GetMapping(path = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse reponse) throws ServletException {

		request.logout();
		try {
			// reponse.sendRedirect("http://auth-server/auth/realms/{realm-name}/protocol/openid-connect/logout?redirect_uri=encodedRedirectUri");
			reponse.sendRedirect(request.getContextPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/";
	}
}



@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter
{
	/**
	 * Registers the KeycloakAuthenticationProvider with the authentication manager.
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		// to avoid appending of the Role or user by spring security
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	/**
	 * Defines the session authentication strategy.
	 */
	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Bean
	public KeycloakConfigResolver KeycloakConfigResolver() {return new KeycloakSpringBootConfigResolver();}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		super.configure(http);
		http
				.authorizeRequests()
				.antMatchers("/products*").hasRole("user")
				.anyRequest().permitAll();
	}
}

// configureGlobal: 
/*				Here we change the Granted Authority Mapper, by default in Spring Security, roles are prefixed with ROLE_, 
				we could change that in our Realm configuration but it could be confusing for other applications that do not know this convention, 
				so here we assign a SimpleAuthorityMapper that will make sure no prefix is added. */

//	keycloakConfigResolver:
/*				By default, the Keycloak Spring Security Adapter will look up for a file named keycloak.json present on your classpath. 
				But here we want to leverage the Spring Boot properties file support. */

//  configure: 
/*				Here is where we define our security constraints, pretty simple to understand we secure the path “/products” with role “user” */