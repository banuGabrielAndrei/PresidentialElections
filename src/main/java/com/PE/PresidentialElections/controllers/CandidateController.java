package com.PE.PresidentialElections.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.PE.PresidentialElections.dataTransfer.CandidateDto;
import com.PE.PresidentialElections.service.CandidatesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CandidateController {
    private CandidatesService candidatesService;

    public CandidateController(CandidatesService candidatesService) {
        this.candidatesService = candidatesService;
    }

    @GetMapping("/candidacy")
    public String candidacyForm(Model model) {
        model.addAttribute("candidate", new CandidateDto());
        return "candidacy";
    }

    @GetMapping("PresidentialElections/candidates")
    public String candidatesList(Model model) {
        List<CandidateDto> candidates = candidatesService.findAllCandidates();
        model.addAttribute("candidates", candidates);
        return "candidates";
    }

    @PostMapping("/candidate/save")
    public String saveCandidate(@ModelAttribute("candidate") CandidateDto candidateDto, Model model) {
        try {
            candidatesService.saveCandidate(candidateDto);
            return "redirect:/PresidentialElections/candidates";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "candidacy";
        }
    }
}
