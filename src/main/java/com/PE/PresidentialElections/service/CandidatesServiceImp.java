package com.PE.PresidentialElections.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.models.Candidate;
import com.PE.PresidentialElections.models.ElectionsRound;
import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.repository.CandidatesRepository;
import com.PE.PresidentialElections.repository.ElectionRoundRepository;
import com.PE.PresidentialElections.repository.UserRepository;

@Service
public class CandidatesServiceImp implements CandidatesService {

    @Autowired
    private CandidatesRepository candidatesRepository;

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private ElectionRoundRepository electionRoundRepository;

    @Autowired
    private ElectionsRoundService electionsRoundService;

    @Override
    public void saveCandidate(Candidate candidate, Integer roundId) {
        Optional<ElectionsRound> currentRoundOptional = electionsRoundService.getRoundById(roundId);
        if (currentRoundOptional.isPresent()) {
            ElectionsRound currentRound = currentRoundOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String candidateUsername = authentication.getName();
            UserEntity user = userRepository.findByUsername(candidateUsername);
            if (user.getCandidate() != null && user.getCandidate().getElectionsRound().equals(currentRound)) {
                throw new IllegalStateException("You already applied as a candidate in the current election round!");
            }
            candidate.setUser(user);
            candidate.setElectionsRound(currentRound);
            currentRound.getCandidates().add(candidate);
            candidatesRepository.save(candidate);
            electionRoundRepository.save(currentRound);
        } else {
            throw new IllegalArgumentException("Invalid round ID: " + roundId);
        }
    }

    @Override
    public List<Candidate> findAllCandidates(Integer id) {
        ElectionsRound currentRound = electionsRoundService.getRoundById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid round ID: " + id));
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