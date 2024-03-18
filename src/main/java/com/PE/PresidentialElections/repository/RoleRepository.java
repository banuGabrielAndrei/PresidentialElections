package com.PE.PresidentialElections.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PE.PresidentialElections.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
