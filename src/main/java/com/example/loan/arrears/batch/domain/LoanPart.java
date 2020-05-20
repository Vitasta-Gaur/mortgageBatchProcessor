package com.example.loan.arrears.batch.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class LoanPart {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private Long loanPartId;
    private Long loanNumber;
    private String typeOfLoan;
}
