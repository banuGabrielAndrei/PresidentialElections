package com.PE.PresidentialElections.service;

import com.PE.PresidentialElections.models.Dates;

public interface DatesService {

    void saveDates(Dates date);

    void editDates(Dates date);

    Dates findByUsage(String usage);

}
