package com.PE.PresidentialElections.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.PE.PresidentialElections.models.ElectionsRound;
import com.PE.PresidentialElections.service.ElectionsRoundService;

@Controller
public class ElectionsRoundController {

    @Autowired
    private ElectionsRoundService electionsRoundService;

    @GetMapping("/rounds/set-dates")
    public String datesForm(Model model) {
        model.addAttribute("electionRound", new ElectionsRound());
        return "election-rounds";
    }

    @PostMapping("/rounds/save")
    public String saveDates(@ModelAttribute("electionRound") ElectionsRound electionRound, Model model) {
        try {
            electionsRoundService.saveRounds(electionRound);
            return "redirect:/presidential-elections";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "election-rounds";
        }
    }
}
