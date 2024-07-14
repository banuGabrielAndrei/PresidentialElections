package com.PE.PresidentialElections.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.PE.PresidentialElections.models.ElectionsRound;
import com.PE.PresidentialElections.service.ElectionsRoundService;

@Controller
public class AppController {

    @Autowired
    private ElectionsRoundService electionsRoundService;

    @GetMapping("/")
    public String startPage() {
        return "start-app";
    }

    @GetMapping("/presidential-elections")
    public String appPage(Model model) {
        List<ElectionsRound> electionRounds = electionsRoundService.getAllRounds();
        model.addAttribute("electionRounds", electionRounds);
        return "presidential-elections";
    }
}
