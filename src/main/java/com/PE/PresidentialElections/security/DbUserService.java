package com.PE.PresidentialElections.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.repository.UserRepository;

import java.util.Collections;

@Service
public class DbUserService implements UserDetailsService {
    private UserRepository userRepository;

    public DbUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.
            User(user.getUsername(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
        } else {
            throw new UsernameNotFoundException("Invalid username or password!");
        }
    }
}