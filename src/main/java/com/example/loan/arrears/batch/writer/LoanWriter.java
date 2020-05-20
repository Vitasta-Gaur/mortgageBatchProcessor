package com.example.loan.arrears.batch.writer;

import com.example.loan.arrears.batch.domain.Loan;
import com.example.loan.arrears.batch.repository.LoanRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

public class LoanWriter implements ItemWriter<Loan> {

    private final LoanRepository loanRepository;

    public LoanWriter(LoanRepository loanRepository){
        this.loanRepository = loanRepository;
    }

    @Transactional
    @Override
    public void write(List<? extends Loan> items) throws Exception {
        items.forEach((Consumer<Loan>) loanRepository::save);
    }
}
