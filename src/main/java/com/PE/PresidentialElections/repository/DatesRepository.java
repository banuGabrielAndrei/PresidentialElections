package com.PE.PresidentialElections.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PE.PresidentialElections.models.Dates;

public interface DatesRepository extends JpaRepository<Dates, Integer> {

    @SuppressWarnings("null")
    Optional<Dates> findById(Integer id);
}