package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Services.LoginServer;
import com.example.demo.modelo.Login;

@SpringBootTest
class DemoApplicationTests {

	@Test
    public void testSetAndGetUsers() {
        // Cria uma nova instância de Login
        Login login = new Login("", "");

		login.setUsers("testUser");

        // Verifica se o nome de usuário foi armazenado corretamente
        assertEquals("testUser", login.getUsers());

	}

	@Test

	public void testSetAndGetSnha(){

		Login login = new Login("", "");

		login.setUsers("tessenha");

        // Verifica se o nome de usuário foi armazenado corretamente
        assertEquals("testsenha", login.getUsers());
		
	}



}
