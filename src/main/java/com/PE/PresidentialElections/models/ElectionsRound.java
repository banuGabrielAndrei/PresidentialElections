package com.PE.PresidentialElections.models;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "election_rounds")
@Entity
public class ElectionsRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startElectionProcess;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endElectionProcess;

    @OneToMany(mappedBy = "electionsRound", cascade = CascadeType.ALL)
    private Set<Candidate> candidates;
}
