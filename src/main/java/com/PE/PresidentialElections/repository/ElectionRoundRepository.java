package com.PE.PresidentialElections.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PE.PresidentialElections.models.ElectionsRound;

public interface ElectionRoundRepository extends JpaRepository<ElectionsRound, Integer> {
    
}