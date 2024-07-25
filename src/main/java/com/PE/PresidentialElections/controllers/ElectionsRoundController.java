package com.PE.PresidentialElections.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.PE.PresidentialElections.models.Candidate;
import com.PE.PresidentialElections.models.ElectionsRound;
import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.models.Vote;
import com.PE.PresidentialElections.repository.UserRepository;
import com.PE.PresidentialElections.repository.VoteRepository;
import com.PE.PresidentialElections.service.CandidatesService;
import com.PE.PresidentialElections.service.ElectionsRoundService;

@Controller
public class ElectionsRoundController {

    @Autowired
    private ElectionsRoundService electionsRoundService;

    @Autowired
    private CandidatesService candidatesService;

    @Autowired
    private UserRepository userRepository;

    @Autowired VoteRepository voteRepository;

    @GetMapping("/rounds/set-dates")
    public String datesForm(Model model) {
        model.addAttribute("electionRound", new ElectionsRound());
        return "election-rounds";
    }

    @GetMapping("/election-rounds-details/{id}")
    public String getElectionRound(@PathVariable Integer id, Model model) {
        ElectionsRound electionsRound = electionsRoundService.getRoundById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid round ID: " 
                    + id));
        model.addAttribute("electionRound", electionsRound);
        return "election-rounds-details";
    }

    @PostMapping("/rounds/save")
    public String saveDates(@ModelAttribute("electionRound") 
        ElectionsRound electionRound, Model model) {
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

    @GetMapping("/elections-history")
    public String electionsHistory(Model model) {
        List<ElectionsRound> electionsRounds = electionsRoundService.getAllRounds();
        model.addAttribute("electionRounds", electionsRounds);
        return "election-history";
    }

    @GetMapping("/election-history-round/{id}")
    public String round(@PathVariable Integer id, Model model) {
        ElectionsRound electionsRound = electionsRoundService.getRoundById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid round ID: "
            + id));
        List<Candidate> candidates = candidatesService.findAllCandidates(id)
            .stream()
            .sorted(Comparator.comparing(Candidate::getVotes).reversed())
            .collect(Collectors.toList());
        List<UserEntity> users = candidates.stream()
            .map(candidate -> userRepository.findById(candidate.getUserId())
                    .orElseThrow(() ->
                     new IllegalStateException("User not found with ID: " +
                      candidate.getUserId())))
            .collect(Collectors.toList());
        List<Vote> votes = voteRepository.findVotesByElectionRoundId(id);
        model.addAttribute("votes", votes);
        model.addAttribute("electionRound", electionsRound);
        model.addAttribute("users", users);
        model.addAttribute("candidates", candidates);
        return "election-history-round";
    }
}
