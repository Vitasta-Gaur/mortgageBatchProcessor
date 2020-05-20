package com.example.loan.arrears.batch.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanDTO {

    private Long loanNumber;
    private String loanHolderName;
    private BigDecimal totalMortageAmount;
    private BigDecimal totalAmountPaid;
}
