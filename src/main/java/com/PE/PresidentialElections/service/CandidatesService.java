package com.PE.PresidentialElections.service;

import java.util.List;

import com.PE.PresidentialElections.dataTransfer.CandidateDto;
import com.PE.PresidentialElections.models.Candidate;

public interface CandidatesService {

    void saveCandidate(CandidateDto candidateDto);

    Candidate findByEmail(String email);

    List<CandidateDto> findAllCandidates();
}