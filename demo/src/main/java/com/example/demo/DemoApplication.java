package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.Services.LoginServer;
import com.example.demo.modelo.Login;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		Login login = new Login("", "");
		LoginServer loginServer = new LoginServer(login);

		loginServer.ObterLogin();


		System.out.println("usuario" + login.getUsers());
		System.out.println("Senhas" + login.getSenha());

		
		SpringApplication.run(DemoApplication.class, args);
	}

}
