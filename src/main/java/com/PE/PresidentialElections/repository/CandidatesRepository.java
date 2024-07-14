package com.PE.PresidentialElections.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PE.PresidentialElections.models.Candidate;

public interface CandidatesRepository extends JpaRepository<Candidate, Integer> {

}
