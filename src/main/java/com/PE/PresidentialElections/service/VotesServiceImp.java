package com.PE.PresidentialElections.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.models.Candidate;
import com.PE.PresidentialElections.models.ElectionsRound;
import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.models.Vote;
import com.PE.PresidentialElections.repository.CandidatesRepository;
import com.PE.PresidentialElections.repository.ElectionRoundRepository;
import com.PE.PresidentialElections.repository.UserRepository;
import com.PE.PresidentialElections.repository.VoteRepository;

@Service
public class VotesServiceImp implements VotesService{

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ElectionRoundRepository electionRoundRepository;

    @Autowired
    private CandidatesRepository candidatesRepository;

    public void saveVote(Integer votedCandidateID, Integer electionsRoundId) {
        Authentication authentication = SecurityContextHolder.getContext()
                            .getAuthentication();
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);
        ElectionsRound electionRound = electionRoundRepository.
          findById(electionsRoundId)
          .orElseThrow(() -> 
          new IllegalArgumentException("Invalid round ID" + electionsRoundId));
        Candidate candidate = candidatesRepository.findById(votedCandidateID)
          .orElseThrow(() ->
          new IllegalArgumentException("Invalid candidateID" + votedCandidateID));
        Vote vote = new Vote();
        vote.setElectionRound(electionRound);
        vote.setUser(user);
        vote.setVotedCandidateID(candidate.getId());
        voteRepository.save(vote);
    }
}
