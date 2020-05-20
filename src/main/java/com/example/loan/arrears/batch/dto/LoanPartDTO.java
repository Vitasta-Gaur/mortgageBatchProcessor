package com.example.loan.arrears.batch.dto;

import lombok.Data;

@Data
public class LoanPartDTO {

    private Long loanPartId;
    private Long loanNumber;
    private String typeOfLoan;
}
