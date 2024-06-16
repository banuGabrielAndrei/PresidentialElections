package com.PE.PresidentialElections.service;

import java.util.List;

import com.PE.PresidentialElections.models.UserEntity;

public interface UserService {

    void saveUser(UserEntity user);

    void addDescription(String username, String description);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    void voteCandidate(UserEntity user);

    List<UserEntity> findAllUsers();
}