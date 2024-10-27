package com.Native.coder.Servico;

import java.time.LocalDateTime;
import java.util.Date;
import javax.security.auth.login.AccountNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Native.coder.Modelo.Login;
import com.Native.coder.Modelo.Email;
import com.Native.coder.Modelo.Endereco;
import com.Native.coder.Modelo.Telefone;
import com.Native.coder.Modelo.World;
import com.Native.coder.Repository.userRepository;
import com.Native.coder.Repository.EmailRepository;
import com.Native.coder.Repository.EnderecoRepository;
import com.Native.coder.Repository.TelefoneRepository;
import com.Native.coder.Repository.WorldRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

    public void registrarLogin(String users) throws AccountNotFoundException {
        System.out.println("Tentando registrar login para o usuário: " + users);
        Login login = loginRepository.findByUsers(users); 
        
        if (login != null) {
            System.out.println("Usuário encontrado: " + login.getUsers());
            
            
            int newLoginCount = login.getLoginCount() + 1;
            login.setLoginCount(newLoginCount);
            
        
            login.getLoginTimes().add(LocalDateTime.now());

          
            System.out.println("Antes de salvar: " + login);

   
            loginRepository.save(login);
            

            System.out.println("Após salvar: " + login);
        } else {
            System.out.println("Usuário não encontrado: " + users);
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
    
   public World obtermundo(Long id) {
	   

	   Login login = loginRepository.findById(id).orElseThrow(() -> new RuntimeException(" usuario nao encotrado"));
	   return login.getMundoAtual();
   }

}

