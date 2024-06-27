package com.PE.PresidentialElections.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.models.Candidate;
import com.PE.PresidentialElections.models.ElectionsRound;
import com.PE.PresidentialElections.repository.CandidatesRepository;
import com.PE.PresidentialElections.repository.ElectionRoundReposirory;

@Service
public class CandidatesServiceImp implements CandidatesService {

    @Autowired
    private CandidatesRepository candidatesRepository;

    @Autowired 
    private ElectionRoundReposirory electionRoundRepository;

    @Autowired
    private ElectionsRoundService electionsRoundService;

    @Override
    public void saveCandidate(Candidate candidate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String candidateUsername = authentication.getName();
        ElectionsRound currentRound = electionsRoundService.getCurrentElectionRound();

        boolean candidateUsernameExists = currentRound.getCandidates().stream()
                .anyMatch(c -> c.getUsername().equals(candidateUsername));
        if (candidateUsernameExists) {
            throw new IllegalStateException("You already applied as a candidate in the current election round!");
        }

        candidate.setUsername(candidateUsername);
        candidate.setElectionsRound(currentRound);
        currentRound.getCandidates().add(candidate);
        candidatesRepository.save(candidate);
        electionRoundRepository.save(currentRound);
    }

    @Override
    public List<Candidate> findAllCandidates() {
        ElectionsRound currentRound = electionsRoundService.getCurrentElectionRound();
        List<Candidate> candidates = currentRound.getCandidates();
        return candidates.stream()
                .sorted(Comparator.comparing(Candidate::getId))
                .collect(Collectors.toList());
    }

    @Override
    public void incrementVotes(Integer candidateId) {
        Candidate candidate = candidatesRepository.findById(candidateId)
            .orElseThrow(() -> new IllegalArgumentException("id not found"));
        candidate.setVotes(candidate.getVotes() + 1);
        candidatesRepository.save(candidate);
    }
}