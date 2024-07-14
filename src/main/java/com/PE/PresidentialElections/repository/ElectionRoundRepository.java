package com.PE.PresidentialElections.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PE.PresidentialElections.models.ElectionsRound;

public interface ElectionRoundRepository extends JpaRepository<ElectionsRound, Integer> {

    @SuppressWarnings("null")
    Optional<ElectionsRound> findById(Integer id);

}