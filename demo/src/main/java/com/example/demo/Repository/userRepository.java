package com.Native.coder.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Native.coder.Modelo.Email;
import com.Native.coder.Modelo.Endereco;
import com.Native.coder.Modelo.Login;
import com.Native.coder.Modelo.Telefone;

public interface userRepository extends JpaRepository<Login, Long> {
    Login findByUsers(String users);
    Login findByEmail_Emailuser(String email);

	
}
