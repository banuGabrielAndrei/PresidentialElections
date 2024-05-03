package com.PE.PresidentialElections.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PE.PresidentialElections.models.Dates;
import com.PE.PresidentialElections.repository.DatesRepository;

@Service
public class DatesServiceImp implements DatesService {

    @Autowired
    private DatesRepository datesRepository;

    @Override
    public void saveDates(Dates date) {
        Optional<Dates> existingOptDate = getDateById(1);
        if (existingOptDate.isPresent()) {
            Dates existingDate = existingOptDate.get();
            existingDate.setCandidacyDeadline(date.getCandidacyDeadline());
            existingDate.setEndVoting(date.getEndVoting());
            datesRepository.save(existingDate);
        } else {
            datesRepository.save(date);
        }
        if (!isValidVotingDate(date)) {
            throw new IllegalStateException("End voting date must be at least 24 hours after candidacy deadline.");
        }
    }

    @Override
    public Optional<Dates> getDateById(Integer id) {
        Optional<Dates> date = datesRepository.findById(id);
        return date;
    }

    @Override
    public boolean isValidVotingDate(Dates date) {
        LocalDateTime deadlineDateTime = date.getCandidacyDeadline().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime votingDateTime = date.getEndVoting().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(deadlineDateTime, votingDateTime);
        return duration.toHours() >= 24;
    }
}
