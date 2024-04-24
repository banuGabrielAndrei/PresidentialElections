package com.PE.PresidentialElections.dataTransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Integer votes;
}