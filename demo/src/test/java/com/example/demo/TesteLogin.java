package com.Native.coder.Teste;

import com.Native.coder.Modelo.Login;
import com.Native.coder.Repository.userRepository;
import com.Native.coder.Servico.LoginServer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TesteLogin {

    @Test
    public void testValidarLoginSuccess() {
       
        userRepository mockRepository = Mockito.mock(userRepository.class);

  
        LoginServer loginServer = new LoginServer();
        loginServer.loginRepository = mockRepository; 

  
        Login login = new Login();
        login.setUsers("user");
        login.setSenha("password");
        when(mockRepository.findByUsers("user")).thenReturn(login);

    
        boolean isValid = loginServer.validarLogin("user", "password");

  
        assertEquals(true, isValid);
    }

    @Test
    public void testValidarLoginFailure() {

        userRepository mockRepository = Mockito.mock(userRepository.class);

  
        LoginServer loginServer = new LoginServer();
        loginServer.loginRepository = mockRepository;

        when(mockRepository.findByUsers("user")).thenReturn(null);


        boolean isValid = loginServer.validarLogin("user", "password");

 
        assertEquals(false, isValid);
    }

  
}
