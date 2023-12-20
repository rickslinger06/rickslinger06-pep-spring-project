package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    Account findByUsername(String username);

   
}
