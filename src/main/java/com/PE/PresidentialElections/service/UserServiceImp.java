package com.PE.PresidentialElections.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.dataTransfer.UserDto;
import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        if (userRepo.findByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already in use");
        }
        if (userRepo.findByUsername(userDto.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already in use");
        }
        UserEntity user = new UserEntity();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
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
    public List<UserDto> findAllUsers() {
        List<UserEntity> users = userRepo.findAll();
        return users.stream().map((user) -> mapUserDto(user)).collect(Collectors.toList());
    }

    private UserDto mapUserDto(UserEntity user) {
        var userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}