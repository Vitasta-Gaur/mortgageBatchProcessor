package com.example.loan.arrears.batch.writer;

import com.example.loan.arrears.batch.domain.LoanPart;
import com.example.loan.arrears.batch.repository.LoanPartRepository;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.function.Consumer;

public class LoanPartWriter implements ItemWriter<LoanPart> {

    private final LoanPartRepository loanPartRepository;

    public  LoanPartWriter(LoanPartRepository loanPartRepository){
        this.loanPartRepository = loanPartRepository;
    }

    @Override
    public void write(List<? extends LoanPart> items) throws Exception {
        items.forEach((Consumer<LoanPart>) loanPartRepository::save);
    }
}
