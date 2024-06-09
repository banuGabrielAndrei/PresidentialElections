package com.PE.PresidentialElections.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        ElectionsRound dbdate = electionsRoundService.getCurrentElectionRound();
            if(dbdate != null) {
                LocalDateTime startElectionProcess = dbdate.getStartElectionProcess().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                model.addAttribute("startElectionProcess", startElectionProcess);
            }
        return "presidential-elections";
    }
}
