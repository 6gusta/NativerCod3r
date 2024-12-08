package com.Native.coder.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.Native.coder.Modelo.Perguntas;

public interface PerguntaRepository extends JpaRepository<Perguntas, Long> {

	  Optional<Perguntas> findById(Long idpergunta);



	
}