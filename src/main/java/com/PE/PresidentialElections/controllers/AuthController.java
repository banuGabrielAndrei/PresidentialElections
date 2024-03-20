package com.PE.PresidentialElections.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.PE.PresidentialElections.dataTransfer.UserDto;
import com.PE.PresidentialElections.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String startPage() {
        return "startApp";
    }

    @GetMapping("/registerPage")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "registerPage";
    }

    @SuppressWarnings("null")
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        try {
            userService.saveUser(userDto);
            return "redirect:/loginPage";
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Email")) {
                result.rejectValue("email", "error", e.getMessage());
            } else if (e.getMessage().contains("Username")) {
                result.rejectValue("username", "error", e.getMessage());
            }
            model.addAttribute("user", userDto);
            return "registerPageError";
        }
    }

    @GetMapping("/PresidentialElections")
    public String appPage() {
        return "PresidentialElections";
    }

    @GetMapping("/registerPageError")
    public String errorRegistering() {
        return "registerPageError";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }
}