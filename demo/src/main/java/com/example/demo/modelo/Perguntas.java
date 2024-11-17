package com.Native.coder.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class perguntas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long idpergunta;
	  private String  texto;
	  private   String RepostaCorreta;
	  private   String repostaAlgoritimo;
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getRepostaCorreta() {
		return RepostaCorreta;
	}
	public void setRepostaCorreta(String repostaCorreta) {
		RepostaCorreta = repostaCorreta;
	}
	public String getRepostaAlgoritimo() {
		return repostaAlgoritimo;
	}
	public void setRepostaAlgoritimo(String repostaAlgoritimo) {
		this.repostaAlgoritimo = repostaAlgoritimo;
	}

}
