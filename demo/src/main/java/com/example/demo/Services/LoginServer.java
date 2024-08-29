package com.example.demo.Services;



import java.util.Scanner;
import com.example.demo.modelo.Login;

public class LoginServer {

    private Login login;

    // Construtor para inicializar o serviço com uma instância de Login
    public LoginServer(Login login) {
        this.login = login;
    }

    public void ObterLogin() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite seu usuário: ");
        String userString = entrada.nextLine();
        
        // Define o nome de usuário na instância de Login
        login.setUsers(userString);

        System.out.println("Digite sua senha: ");
        String password = entrada.nextLine();

        // Define a senha na instância de Login
        login.setSenha(password);

        entrada.close();  // Fechar o Scanner para evitar vazamento de recursos
    }
}

