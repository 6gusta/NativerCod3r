package com.Native.coder.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Native.coder.Modelo.Cadastro;

public interface UserRepository extends JpaRepository<Cadastro, Long> {
    Cadastro findByUsername(String username);
}
