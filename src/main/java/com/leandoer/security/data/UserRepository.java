package com.leandoer.security.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> getAdminByUsername(String username);

}
