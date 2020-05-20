package com.example.loan.arrears.batch.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Loan {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "LOAN_NUMBER" , unique = true)
    private Long loanNumber;

    private String loanHolderName;
    private BigDecimal totalMortageAmount;
    private BigDecimal totalAmountPaid;
}
