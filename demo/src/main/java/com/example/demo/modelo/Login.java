package com.Native.coder.Modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String users;
    private String senha;
    private String datanasc;       
    private String sexo;          
    private  int loginCount = 0;
    @ManyToOne
    @JoinColumn(name = "world_id")
    private World mundoAtual;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Email email;
    @OneToOne(cascade = CascadeType.ALL)
    private Telefone telefone;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    
    @ElementCollection
    private List<LocalDateTime> loginTimes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

   

    public String getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(String datanasc) {
        this.datanasc = datanasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	

    public List<LocalDateTime> getLoginTimes() {
        return loginTimes;
    }
    
    public void setLogintimes(List<LocalDateTime> logintimes) {
    	this.loginTimes = logintimes;
    	
    	
    }
    public void setEmail(Email email) {
        this.email = email;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    

    public World getMundoAtual() {
        return mundoAtual;
    }

    public void setMundoAtual(World mundoAtual) {
        this.mundoAtual = mundoAtual;
    }
}

