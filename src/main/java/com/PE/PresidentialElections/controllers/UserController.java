package com.PE.PresidentialElections.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PE.PresidentialElections.dataTransfer.UserDto;
import com.PE.PresidentialElections.models.Dates;
import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.service.DatesService;
import com.PE.PresidentialElections.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {
    private UserService userService;
    private DatesService datesService;

    public UserController(UserService userService, DatesService datesService) {
        this.userService = userService;
        this.datesService = datesService;
    }

    @GetMapping("/")
    public String startPage() {
        return "start-app";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        try {
            userService.saveUser(userDto);
            return "redirect:/login-page";
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("email")) {
                result.rejectValue("email", "error", e.getMessage());
            } else if (e.getMessage().contains("Username")) {
                result.rejectValue("username", "error", e.getMessage());
            }
            model.addAttribute("user", userDto);
            return "register-error";
        }
    }

    @GetMapping("/Presidential-Elections")
    public String appPage(Model model) {
        Optional<Dates> dbdate = datesService.getDateById(1);
        LocalDateTime endCandidacy = dbdate.get().getCandidacyDeadline().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        model.addAttribute("endCandidacy", endCandidacy);
        return "Presidential-Elections";
    }

    @GetMapping("/register-error")
    public String errorRegistering() {
        return "register-error";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "login-page";
    }

    @GetMapping("/user/{username}/profile")
    public String profile(Model model, @PathVariable("username") String username) {
        UserEntity user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/user/description")
    public String saveDescription(@RequestParam("description") String description,
            @RequestParam("username") String username) {
        userService.addDescription(username, description);
        return "redirect:/user/" + username + "/profile";
    }
}