package com.PE.PresidentialElections.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.PE.PresidentialElections.dataTransfer.CandidateDto;
import com.PE.PresidentialElections.models.Dates;
import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.service.CandidatesService;
import com.PE.PresidentialElections.service.DatesService;
import com.PE.PresidentialElections.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CandidateController {

    @Autowired
    private CandidatesService candidatesService;

    @Autowired
    private DatesService datesService;

    @Autowired
    private UserService userService;

    @GetMapping("/candidacy/form")
    public String candidacyForm(Model model) {
        model.addAttribute("candidate", new CandidateDto());
        return "candidacy";
    }

    @PostMapping("/candidate/save")
    public String saveCandidate(@ModelAttribute("candidate") CandidateDto candidateDto, Model model) {
        try {
            Optional<Dates> dbDate = datesService.getDateById(1);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime candidacyDeadLine = dbDate.get().getCandidacyDeadline().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (now.isBefore(candidacyDeadLine)) {
                candidatesService.saveCandidate(candidateDto);
                return "redirect:/candidates/list";
            } else {
                model.addAttribute("candidacyEnds", "You cannot candidate anymore! Elections are already started!");
                return "candidacy";
            }

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "candidacy";
        }
    }

    @GetMapping("/candidates/list")
    public String candidatesList(Model model) {
        Optional<Dates> dbdate = datesService.getDateById(1);
        LocalDateTime startVoting = dbdate.get().getCandidacyDeadline().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        model.addAttribute("startVoting", startVoting);
        LocalDateTime endVoting = dbdate.get().getEndVoting().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        model.addAttribute("endVoting", endVoting);
        List<CandidateDto> candidates = candidatesService.findAllCandidates();
        model.addAttribute("candidates", candidates);
        return "candidates";
    }

    @PostMapping("/candidate/vote")
    public String voteCandidate(@RequestParam("uniqueIdentifier") String uniqueIdentifier,
            Principal principal, Model model) {
        Optional<Dates> dbDate = datesService.getDateById(1);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endVoting = dbDate.get().getEndVoting().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (now.isBefore(endVoting)) {
            String username = principal.getName();
            UserEntity user = userService.findByUsername(username);
            try {
                userService.voteCandidate(user);
                candidatesService.incrementVotes(uniqueIdentifier);
                model.addAttribute("voted", "You have voted!");
                return "/voting";
            } catch (IllegalStateException e) {
                model.addAttribute("votingError", e.getMessage());
                return "/voting";
            }
        }
        model.addAttribute("votingError", "You cannot vote anymore!");
        return "/voting";
    }
}
