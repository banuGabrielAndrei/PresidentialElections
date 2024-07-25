package com.PE.PresidentialElections.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.PE.PresidentialElections.models.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer>{
    
    @Query("SELECT v FROM Vote v JOIN v.user u WHERE v.electionRound.id = :roundId")
    List<Vote> findVotesByElectionRoundId(@Param("roundId") Integer roundId);
}
