package com.Native.coder.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class RespostaUsuario {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long idReposta;
	
	@ManyToOne
	@JoinColumn(name = "login") 
	  private Login  login;
	
	@ManyToOne
	@JoinColumn(name="idperguntas")
	  private   perguntas idperguntas;
	  private String  res_user;
	  private   String repostaAlgoritimo;
	  private  Boolean VouF;
	  private String nomeUser;
	
	  
	  

	public perguntas getIdperguntas() {
		return idperguntas;
	}
	public void setIdperguntas(perguntas idperguntas) {
		this.idperguntas = idperguntas;
	}
	public String getRes_user() {
		return res_user;
	}
	public void setRes_user(String res_user) {
		this.res_user = res_user;
	}
	public Boolean getVouF() {
		return VouF;
	}
	public void setVouF(Boolean vouF) {
		VouF = vouF;
	}
	public String getRepostaAlgoritimo() {
		return repostaAlgoritimo;
	}
	public void setRepostaAlgoritimo(String repostaAlgoritimo) {
		this.repostaAlgoritimo = repostaAlgoritimo;
	}
	public void save(RespostaUsuario reposta) {
		
		
	}
	public String getNomeUser() {
		return nomeUser;
	}
	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}
	


}
