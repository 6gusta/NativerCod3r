package com.Native.coder.Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Perguntas {  
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpergunta;

    private String texto;

    private String respostaCorretaIngles; 

    private String respostaAlgoritmo; 


    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

   
    public String getRespostaCorretaIngles() {
        return respostaCorretaIngles;
    }

    public void setRespostaCorretaIngles(String respostaCorretaIngles) {
        this.respostaCorretaIngles = respostaCorretaIngles;
    }

   
    public Long getIdpergunta() {
        return idpergunta;
    }

    public void setIdpergunta(Long idpergunta) {
        this.idpergunta = idpergunta;
    }


    public String getRespostaAlgoritmo() {
        return respostaAlgoritmo;
    }

    public void setRespostaAlgoritmo(String respostaAlgoritmo) {
        this.respostaAlgoritmo = respostaAlgoritmo;
    }
}
