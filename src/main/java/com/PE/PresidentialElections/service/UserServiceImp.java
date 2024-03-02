package com.PE.PresidentialElections.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.DataTransfer.UserDto;
import com.PE.PresidentialElections.models.Role;
import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.repository.RoleRepository;
import com.PE.PresidentialElections.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.roleRepo = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = setUserRole(userDto);
        user.setRoles(Arrays.asList(role));
        userRepo.save(user);
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
        return userDto;
    }

    private Role setUserRole(UserDto userDto) {
        if (userDto.getUsername().equals("admin")) {
            return roleRepo.findByName("ADMIN");
        } else {
            return roleRepo.findByName("USER");
        }
    }
}