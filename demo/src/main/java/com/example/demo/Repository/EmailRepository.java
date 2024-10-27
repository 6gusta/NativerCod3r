package com.Native.coder.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Native.coder.Modelo.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {
    Email findByEmailuser(String emailuser);
}
