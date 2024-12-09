package com.Native.coder.Servico;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Native.coder.Modelo.Login;
import com.Native.coder.Modelo.RespostaUsuario;
import com.Native.coder.Modelo.Email;
import com.Native.coder.Modelo.Endereco;
import com.Native.coder.Modelo.Telefone;
import com.Native.coder.Modelo.World;
import com.Native.coder.Modelo.Perguntas;
import com.Native.coder.Repository.userRepository;
import com.Native.coder.Repository.EmailRepository;
import com.Native.coder.Repository.EnderecoRepository;
import com.Native.coder.Repository.PerguntaRepository;
import com.Native.coder.Repository.RepostaUserRepository;
import com.Native.coder.Repository.TelefoneRepository;
import com.Native.coder.Repository.WorldRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;

import javax.crypto.SecretKey;

@Service
public class LoginServer {
	
	  @Autowired
	    private EmailRepository emailRepository;
	  
	  /*~~(Unable to determine parameter type)~~>*/@Autowired
	  private EnderecoRepository enderecoRepository;
	  
	  @Autowired
	  private TelefoneRepository telefonerepository;
	  
	  @Autowired
	  private WorldRepository worldrepository;
	  
	  @Autowired
	  private RepostaUserRepository respostaUserRepository;
	  
	  @Autowired
	  private PerguntaRepository pergunta;
	  
	  
    private final userRepository loginRepository;
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Chave segura
    private static final long EXPIRATION_TIME = 86400000;
  

    @Autowired
    public LoginServer(userRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public String validarLogin(String user, String senha) {
        try {
            Login login = loginRepository.findByUsers(user);
            if (login != null && BCrypt.checkpw(senha, login.getSenha())) {
            	  registrarLogin(user); 
                return GerarToken(user); 
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erro: no método validarLogin " + e.getMessage());
            return null; 
        }
    }
    
    public Login criarLogin(String user, String senha, String emailuser, String telefone, String datanasc, String sexo, Endereco endereco) {

        if (emailRepository.findByEmailuser(emailuser) != null) {
            throw new RuntimeException("Você já possui uma conta. Volte para a página inicial e faça seu login.");
        }
        
        try {

            Login login = new Login();
            login.setUsers(user);
            login.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
            login.setDatanasc(datanasc);
            login.setSexo(sexo);
            
        
            Email email = new Email();
            email.setEmailuser(emailuser); 
            login.setEmail(email);
            if (login.getSenha() == null) {
                throw new IllegalArgumentException("Senha não pode ser nula.");
            }

            if (login.getUsers() == null || login.getUsers().isEmpty()) {
                throw new IllegalArgumentException("Usuário não pode ser nulo ou vazio.");
            }

            Endereco endereco1 = new Endereco();
            endereco1.setBairro(endereco.getBairro());
            endereco1.setCep(endereco.getCep());
            endereco1.setCidade(endereco.getCidade());
            endereco1.setEstado(endereco.getEstado());
            endereco1.setLote(endereco.getLote());
            endereco1.setQuadra(endereco.getQuadra());
            endereco1.setRua(endereco.getRua());
            login.setEndereco(endereco1); 


            Telefone telefone1 = new Telefone();
            telefone1.setTelefoneuser(telefone);
            login.setTelefone(telefone1);
            
            emailRepository.save(email);
            enderecoRepository.save(endereco1);
            telefonerepository.save(telefone1);

            System.out.println("Usuário a ser salvo: " + login.getUsers());
            return loginRepository.save(login);
        } catch (NullPointerException e) {
            System.out.print("Erro de ponteiro nulo no método criar login: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.print("Erro no método criar login: " + e.getMessage());
            return null;
        }
    }

    public void registrarLogin(String user) throws AccountNotFoundException {
        System.out.println("Tentando registrar login para o usuário: " + user);
        Login login = loginRepository.findByUsers(user); 
        
        if (login != null) {
            System.out.println("Usuário encontrado: " + login.getUsers());
            
            
            int newLoginCount = login.getLoginCount() + 1;
            login.setLoginCount(newLoginCount);
            
        
            login.getLoginTimes().add(LocalDateTime.now());

          
            System.out.println("Antes de salvar: " + login);

   
            loginRepository.save(login);
            

            System.out.println("Após salvar: " + login);
        } else {
            System.out.println("Usuário não encontrado: " + user);
            throw new AccountNotFoundException("Usuário não encontrado");
        }
    }


    public String GerarToken(String user) {
        try {
            String token = Jwts.builder()
                    .setSubject(user)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SECRET_KEY)
                    .compact();
            System.out.println("Token gerado com sucesso: " + token);
            return token;
        } catch (Exception e) {
            System.err.println("Erro ao gerar token: " + e.getMessage());
            return null;
        }
    }
    
    
  public void regitrarMundo( World mundo ) {
	 if(mundo.getNome() == null || mundo.getNome().isEmpty()) {
		 throw new IllegalArgumentException(" nome do mundo nao pode ser vazio ");
	 }
	 
	 worldrepository.save(mundo);
	 
 }


    
    public World obterMundoAtual(Long usuarioId) {
        return worldrepository.obterMundoAtual(usuarioId);
    }


@Transactional
public void associarMundo(Long id, Long mundoid) {
    Login usuario = loginRepository.findById(id).orElse(null);
    World mundo = worldrepository.findById(mundoid).orElse(null);
    if (usuario != null && mundo != null) {
        usuario.setMundoAtual(mundo);
        loginRepository.save(usuario);
    } else {
        throw new IllegalArgumentException("Usuário ou mundo não encontrado!");
    }
}



public RespostaUsuario processarResposta(RespostaUsuario resposta, Long idpergunta, String res_user, String respostaAlgoritimoUser, String respostaAlgoritimo) {

    Optional<Perguntas> perguntaOpt = pergunta.findById(idpergunta); 

    if (perguntaOpt.isPresent()) {
        Perguntas pergunta = perguntaOpt.get();

        System.out.println("O algoritmo correto é: " + pergunta.getRespostaAlgoritmo()); 

        // Verifica as respostas
        if (pergunta.getRespostaCorretaIngles().equalsIgnoreCase(res_user.trim()) && 
            pergunta.getRespostaAlgoritmo().equalsIgnoreCase(respostaAlgoritimoUser.trim())) {
            
            resposta.setVouF("V");
        } else {
            resposta.setVouF("F");
        }

        resposta.setIdpergunta(pergunta);

        respostaUserRepository.save(resposta);

        return resposta;
    } else {
        throw new IllegalArgumentException("Pergunta não encontrada para o ID fornecido");
    }
}

}








