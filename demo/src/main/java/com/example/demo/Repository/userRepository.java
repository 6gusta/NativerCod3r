package com.Native.coder.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Native.coder.Modelo.Login;

public interface userRepository extends JpaRepository<Login, Long> {
    Login findByUsers(String users);
    Login findByEmail(String email);
}
