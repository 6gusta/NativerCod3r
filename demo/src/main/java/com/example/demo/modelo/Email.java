package com.Native.coder.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Email {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String emailuser;
	
	public Email() {
		
	}
	
	public Email(String email) {
		this.emailuser = email;
	}
	public String getEmailuser() {
		return emailuser;
	}
	public void setEmailuser(String emailuser) {
		this.emailuser = emailuser;
	}
	public void setLogin(Login savedLogin) {
		
		
	}

}
