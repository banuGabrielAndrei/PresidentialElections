package com.PE.PresidentialElections.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.service.UserService;

@Controller
public class ProfileController {
    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/saveDescription")
    public String saveDescription(@RequestParam("description") String description, Authentication authentication) {
        String username = authentication.getName();
        userService.addDescription(username, description);
        return "redirect:/profile";
    }
}
