package com.Native.coder.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Native.coder.Modelo.RespostaUsuario;
@Repository 
public interface RepostaUserRepository  extends JpaRepository <RespostaUsuario, Long>{
	
	

}
