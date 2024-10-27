package com.Native.coder.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Telefone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long id;
	private String telefoneuser;
	
	public Telefone() {
		
	}
	
	 public Telefone(String telefoneuser) {
		 this.telefoneuser = telefoneuser;
	 }
	public String getTelefoneuser() {
		return telefoneuser;
	}
	public void setTelefoneuser(String telefoneuser) {
		this.telefoneuser = telefoneuser;
	}
	public void setLogin(Login savedLogin) {
		// TODO Auto-generated method stub
		
	}

}
