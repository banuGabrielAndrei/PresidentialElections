package com.PE.PresidentialElections.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.PE.PresidentialElections.models.Dates;
import com.PE.PresidentialElections.service.DatesService;

@Controller
public class DatesController {
    private DatesService datesService;

    public DatesController(DatesService datesService) {
        this.datesService = datesService;
    }

    @GetMapping("/dates/set-dates")
    public String datesForm(Model model) {
        model.addAttribute("date", new Dates());
        return "dates";
    }

    @PostMapping("/dates/save")
    public String saveDates(@ModelAttribute("date") Dates date, Model model) {
        try {
            datesService.saveDates(date);
            return "redirect:/Presidential-Elections";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "dates";
        }
    }
}
