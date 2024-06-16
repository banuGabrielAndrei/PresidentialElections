package com.PE.PresidentialElections.service;

import java.util.List;

import com.PE.PresidentialElections.models.Candidate;

public interface CandidatesService {

    void saveCandidate(Candidate candidate);

    void incrementVotes(Integer candidateId);

    Candidate findByEmail(String email);

    List<Candidate> findAllCandidates();

}