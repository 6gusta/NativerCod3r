package com.Native.coder.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Native.coder.Modelo.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByUsername(String username);
    Login findByEmail(String email);
}
