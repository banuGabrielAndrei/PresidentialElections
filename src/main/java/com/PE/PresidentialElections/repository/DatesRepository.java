package com.PE.PresidentialElections.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PE.PresidentialElections.models.Dates;

public interface DatesRepository extends JpaRepository<Dates, Integer>{

    Dates findByUsage(String usage);
}
