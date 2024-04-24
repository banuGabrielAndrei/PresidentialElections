package com.PE.PresidentialElections.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.dataTransfer.CandidateDto;
import com.PE.PresidentialElections.models.Candidate;
import com.PE.PresidentialElections.repository.CandidatesRepository;

@Service
public class CandidatesServiceImp implements CandidatesService {

    @Autowired
    private CandidatesRepository candidatesRepository;

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
        String candidateUsername = (authentication.getName());
        if (candidatesRepository.existsByUsername(candidateUsername)) {
            throw new IllegalStateException("User has already applied as a candidate.");
        }

        Candidate candidate = new Candidate();
        candidate.setFirstName(candidateDto.getFirstName());
        candidate.setLastName(candidateDto.getLastName());
        candidate.setEmail(candidateDto.getEmail());
        candidate.setUsername(candidateUsername);
        candidatesRepository.save(candidate);
    }

    @Override
    public Candidate findByEmail(String email) {
        return candidatesRepository.findByEmail(email);
    }

    @Override
    public List<CandidateDto> findAllCandidates() {
        List<Candidate> candidates = candidatesRepository.findAll();
        return candidates.stream().map((candidate) -> mapCandidateDto(candidate)).collect(Collectors.toList());
    }
}