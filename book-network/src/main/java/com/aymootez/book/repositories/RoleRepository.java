package com.aymootez.book.repositories;

import com.aymootez.book.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

           Optional<Role> findByName(String name);
}
