package com.PE.PresidentialElections.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.dataTransfer.UserDto;
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

        // implementation for users single role
        Role role = roleRepo.findByName("ROLE_USER");
        if (role == null) {
            role = createRole("ROLE_USER");
        }

        // implementation for users with both ADMIN and USER role

        // Role adminRole = roleRepo.findByName("ROLE_ADMIN");
        // if (adminRole == null) {
        // adminRole = createRole("ROLE_ADMIN");
        // }

        // Role userRole = roleRepo.findByName("ROLE_USER");
        // if (userRole == null) {
        // userRole = createRole("ROLE_USER");
        // }
        // user.setRoles(Arrays.asList(adminRole, userRole));

        user.setRoles(Arrays.asList(role));
        userRepo.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepo.findByUsername(email);
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

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepo.save(role);
    }
}