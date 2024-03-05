package com.PE.PresidentialElections.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PE.PresidentialElections.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);
}