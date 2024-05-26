package com.PE.PresidentialElections.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.PE.PresidentialElections.models.Dates;
import com.PE.PresidentialElections.service.DatesService;

@Controller
public class AppController {

    @Autowired
    private DatesService datesService;

    @GetMapping("/")
    public String startPage() {
        return "start-app";
    }

    @GetMapping("/presidential-elections")
    public String appPage(Model model) {
        Optional<Dates> dbdate = datesService.getDateById(1);
        LocalDateTime endCandidacy = dbdate.get().getCandidacyDeadline().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        model.addAttribute("endCandidacy", endCandidacy);
        return "presidential-elections";
    }
}
