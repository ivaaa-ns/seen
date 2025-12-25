package com.ivaaan.seen.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameStartingWithIgnoreCase(String prefix);

    Optional<User> findByEmail(String email);

}
