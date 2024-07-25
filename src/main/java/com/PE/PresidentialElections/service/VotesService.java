package com.PE.PresidentialElections.service;

public interface VotesService {

    void saveVote(Integer votedCandidateID, Integer electionsRoundId);
}
