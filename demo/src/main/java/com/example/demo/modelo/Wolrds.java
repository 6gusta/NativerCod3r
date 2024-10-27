package com.Native.coder.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Mundos")
public class World {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mundoid") 
    private long mundoid;
    private String nome;
    private String descricao;

    public World() {}

    public World(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return descricao;
    }

    public void setDesc(String descricao) {
        this.descricao = descricao;
    }
}
