package com.PE.PresidentialElections.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.models.ElectionsRound;
import com.PE.PresidentialElections.models.UserEntity;
import com.PE.PresidentialElections.repository.ElectionRoundReposirory;
import com.PE.PresidentialElections.repository.UserRepository;

@Service
public class ElectionsRoundServiceImp implements ElectionsRoundService {

    @Autowired
    private ElectionRoundReposirory electionRoundReposirory;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveRounds(ElectionsRound electionsRound) {
        if (!isValidVotingDate(electionsRound)) {
            throw new IllegalStateException("The time between start election " +
            "and end election is not valid");
        }
        if (!isValidNewRound(electionsRound)) {
            throw new IllegalStateException("You cannot set the start date for " +
            "a new round before the current round ends.");
        }
        List<UserEntity> users = userRepository.findAll();
        if (!users.isEmpty()) {
            for (UserEntity userVoting : users) {
                userVoting.setHasVoted(false);
                userRepository.save(userVoting);
            }
        }
        electionRoundReposirory.save(electionsRound);
    }

    @Override
    public Optional<ElectionsRound> getRoundById(Integer id) {
        Optional<ElectionsRound> electionRound = electionRoundReposirory.findById(id);
        return electionRound;
    }

    @Override
    public boolean isValidVotingDate(ElectionsRound electionsRound) {
        LocalDateTime deadlineDateTime = electionsRound.getStartElectionProcess().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime votingDateTime = electionsRound.getEndElectionProcess().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(deadlineDateTime, votingDateTime);
        return duration.toMinutes() >= 10;
    }

    @Override
    public boolean isValidNewRound(ElectionsRound electionsRound) {
        List<ElectionsRound> rounds = electionRoundReposirory.findAll();
        if (!rounds.isEmpty()) {
            rounds.sort(Comparator.comparing(ElectionsRound::getId).reversed());
            ElectionsRound latestRound = rounds.get(0);
            LocalDateTime endRound = latestRound.getEndElectionProcess()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime newRound = electionsRound.getStartElectionProcess()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();
            return newRound.isAfter(endRound) && endRound.isBefore(now);
        }
        return true;
    }

    @Override
    public ElectionsRound getCurrentElectionRound() {
        List<ElectionsRound> rounds = electionRoundReposirory.findAll();
        if (!rounds.isEmpty()) {
            rounds.sort(Comparator.comparing(ElectionsRound::getId).reversed());
            return rounds.get(0);
        }
        return null;
    }
}
