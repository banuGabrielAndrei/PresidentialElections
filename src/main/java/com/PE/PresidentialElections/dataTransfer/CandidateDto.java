package com.PE.PresidentialElections.dataTransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CandidateDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Integer votes;
}