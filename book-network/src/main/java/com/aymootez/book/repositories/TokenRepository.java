package com.aymootez.book.repositories;

import com.aymootez.book.models.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

   Optional<Token> findByToken(String token);
}

