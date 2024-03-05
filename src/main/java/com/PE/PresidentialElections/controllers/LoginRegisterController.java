package com.PE.PresidentialElections.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.PE.PresidentialElections.repository.UserRepository;

@Controller
public class LoginRegisterController {
    private final UserRepository userRepository;

    public LoginRegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String firstPage() {
        return "ex";
    }

    @GetMapping("/registerPage")
    public String getMethodName() {
        return "registerPage";
    }

}