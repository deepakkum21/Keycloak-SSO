package com.example.productapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

		model.addAttribute("products", Arrays.asList("iPad", "iPhone", "iPod"));
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
