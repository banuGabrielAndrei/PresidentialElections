package com.PE.PresidentialElections.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserEntity user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already in use");
        }
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepo.save(user);
    }

    @Override
    public void addDescription(String username, String description) {
        UserEntity user = userRepo.findByUsername(username);
        user.setDescription(description);
        userRepo.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        List<UserEntity> users = userRepo.findAll();
        return users;
    }

    @Override
    public void voteCandidate(UserEntity user) {
        
    }
}