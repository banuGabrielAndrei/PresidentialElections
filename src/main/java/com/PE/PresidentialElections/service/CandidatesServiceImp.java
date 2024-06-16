package com.PE.PresidentialElections.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.dataTransfer.CandidateDto;
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

    private CandidateDto mapCandidateDto(Candidate candidate) {
        var candidateDto = new CandidateDto();
        candidateDto.setFirstName(candidate.getFirstName());
        candidateDto.setLastName(candidate.getLastName());
        candidateDto.setEmail(candidate.getEmail());
        candidateDto.setVotes(candidate.getVotes());
        return candidateDto;
    }

    @Override
    public void saveCandidate(CandidateDto candidateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String candidateUsername = authentication.getName();
        ElectionsRound currentRound = electionsRoundService.getCurrentElectionRound();

        boolean candidateUsernameExists = currentRound.getCandidates().stream()
        .anyMatch(candidate -> candidate.getUsername().equals(candidateUsername));
        if (candidateUsernameExists) {
            throw new IllegalStateException("You already applied as a candidate in the current election round!");
        }

        boolean candidateEmailExists = currentRound.getCandidates().stream()
        .anyMatch(candidate -> candidate.getEmail().equals(candidateDto.getEmail()));
        if (candidateEmailExists) {
            throw new IllegalStateException("Email already used!");
    }

        Candidate candidate = new Candidate();
        candidate.setFirstName(candidateDto.getFirstName());
        candidate.setLastName(candidateDto.getLastName());
        candidate.setEmail(candidateDto.getEmail());
        candidate.setUsername(candidateUsername);
        candidate.setElectionsRound(currentRound);
        currentRound.getCandidates().add(candidate);
        candidatesRepository.save(candidate);
        electionRoundRepository.save(currentRound);
    }

    @Override
    public Candidate findByEmail(String email) {
        return candidatesRepository.findByEmail(email);
    }

    @Override
    public List<CandidateDto> findAllCandidates() {
        ElectionsRound currentRound = electionsRoundService.getCurrentElectionRound();
        List<Candidate> candidates = currentRound.getCandidates();
        return candidates.stream().sorted(Comparator.comparing(Candidate::getId))
                .map((candidate) -> mapCandidateDto(candidate)).collect(Collectors.toList());
    }

    @Override
    public void incrementVotes(Integer candidateId) {
        Candidate candidate = candidatesRepository.findById(candidateId)
            .orElseThrow(() -> new IllegalArgumentException("id not found"));
        candidate.setVotes(candidate.getVotes() + 1);
        candidatesRepository.save(candidate);
        System.out.println(candidate.getId().toString()+ "aici");
    }
}