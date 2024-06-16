package com.PE.PresidentialElections.service;

import java.util.List;
import java.util.Optional;
import com.PE.PresidentialElections.models.ElectionsRound;

public interface ElectionsRoundService {

    void saveRounds(ElectionsRound electionsRound);

    Optional<ElectionsRound> getRoundById(Integer id);

    boolean isValidVotingDate(ElectionsRound electionsRound);

    boolean isValidNewRound(ElectionsRound electionsRound);

    ElectionsRound getCurrentElectionRound();

    List<ElectionsRound> getAllRounds();
}
