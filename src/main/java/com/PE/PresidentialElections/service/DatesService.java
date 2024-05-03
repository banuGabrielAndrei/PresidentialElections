package com.PE.PresidentialElections.service;

import java.util.Optional;

import com.PE.PresidentialElections.models.Dates;

public interface DatesService {

    void saveDates(Dates date);

    Optional<Dates> getDateById(Integer id);

    boolean isValidVotingDate(Dates date);
}
