package com.PE.PresidentialElections.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/election-rounds-details/{id}")
    public String getElectionRound(@PathVariable Integer id, Model model) {
        Optional<ElectionsRound> electionsRoundOptional = electionsRoundService.getRoundById(id);
        if (electionsRoundOptional.isPresent()) {
            ElectionsRound electionsRound = electionsRoundOptional.get();
            model.addAttribute("electionRound", electionsRound);
            return "election-rounds-details"; 
        } else {
            model.addAttribute("errorMessage", "Election round not found");
            return "presidential-election";
        }
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

    @GetMapping("/rounds/results")
    public String roundsResults(Model model) {
        List<ElectionsRound> electionRounds = electionsRoundService.getAllRounds();
        model.addAttribute("electionRounds", electionRounds);
        return "election-results";
    }
}
