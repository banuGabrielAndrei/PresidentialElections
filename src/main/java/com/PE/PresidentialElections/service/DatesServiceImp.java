package com.PE.PresidentialElections.service;

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
        datesRepository.save(date);
    }

    @Override
    public void editDates(Dates newDate) {
        Dates existingDate = datesRepository.findByUsage(newDate.getUsage());
        if (newDate.getUsage().equals("candidacy")) {
            existingDate.setCandidacyDeadline(newDate.getCandidacyDeadline());
        } else {
            existingDate.setEndVoting(newDate.getEndVoting());
        }
    }

    @Override
    public Dates findByUsage(String usage) {
        return datesRepository.findByUsage(usage);
    }
}
