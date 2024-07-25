package com.PE.PresidentialElections.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.PE.PresidentialElections.models.Candidate;
import com.PE.PresidentialElections.models.ElectionsRound;
import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.repository.UserRepository;
import com.PE.PresidentialElections.service.CandidatesService;
import com.PE.PresidentialElections.service.ElectionsRoundService;
import com.PE.PresidentialElections.service.UserService;
import com.PE.PresidentialElections.service.VotesService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CandidateController {

    @Autowired
    private CandidatesService candidatesService;

    @Autowired
    private ElectionsRoundService electionsRoundService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VotesService votesService;

    @Autowired
    UserService userService;

    @GetMapping("/candidacy-error")
    public String candidacyError() {
        return "candidacy-error";
    }

    @PostMapping("/candidate/save")
    public String saveCandidate(Model model, @RequestParam("roundId") Integer id) {
        try {
            ElectionsRound currElectionsRound = electionsRoundService.getRoundById(id)
                    .orElseThrow(() -> 
                    new IllegalArgumentException("Invalid round ID: " + id));
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startVoting = currElectionsRound.getStartElectionProcess()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (now.isBefore(startVoting)) {
                Candidate candidate = new Candidate();
                candidatesService.saveCandidate(candidate, currElectionsRound.getId());
                return "redirect:/candidates/list?roundId=" + id;
            } else {
                model.addAttribute("candidacyEnds", 
                    "You cannot candidate anymore! " + 
                    "Elections are already started!");
                return "candidacy-error";
            }
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "candidacy-error";
        }
    }

    @GetMapping("/candidates/list")
    public String candidatesList(Model model, @RequestParam("roundId") Integer roundId) {
        ElectionsRound dbRound = electionsRoundService.getRoundById(roundId)
                .orElseThrow(() -> 
                new IllegalArgumentException("Invalid round ID: " + roundId));
        LocalDateTime startVoting = dbRound.getStartElectionProcess().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        model.addAttribute("startVoting", startVoting);
        LocalDateTime endVoting = dbRound.getEndElectionProcess().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        model.addAttribute("endVoting", endVoting);
        List<Candidate> candidates = candidatesService.findAllCandidates(roundId);
        List<UserEntity> users = candidates.stream()
            .map(candidate -> userRepository.findById(candidate.getUserId())
                    .orElseThrow(() ->
                     new IllegalStateException("User not found with ID: " +
                      candidate.getUserId())))
            .collect(Collectors.toList());
        model.addAttribute("users", users);
        model.addAttribute("candidates", candidates);
        model.addAttribute("electionRound", dbRound);
        return "candidates";
    }

    @PostMapping("/vote")
    public String voteCandidate(@RequestParam("candidateId") Integer candidateId,
                                @RequestParam("roundId") Integer roundId,
                                Principal principal, Model model) {
        ElectionsRound dbRound = electionsRoundService.getRoundById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid round ID: " 
                + roundId));                     
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endVoting = dbRound.getEndElectionProcess().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (now.isBefore(endVoting)) {
            String username = principal.getName();
            UserEntity user = userService.findByUsername(username);
            try {
                userService.voteCandidate(user, dbRound);
                candidatesService.incrementVotes(candidateId);
                votesService.saveVote(candidateId, roundId);
                model.addAttribute("voted", 
                    "You have voted!");
                return "/voting";
            } catch (IllegalStateException e) {
                model.addAttribute("votingError", e.getMessage());
                return "/voting";
            }
        }
        model.addAttribute("votingError", 
            "You cannot vote anymore!");
        return "/voting";
    }
}
