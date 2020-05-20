package com.example.loan.arrears.batch.processor;

import com.example.loan.arrears.batch.domain.Loan;
import com.example.loan.arrears.batch.dto.LoanDTO;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class LoanProcessor implements ItemProcessor<LoanDTO, Loan> {

    private final ModelMapper modelMapper;

    @Autowired
    public LoanProcessor(ModelMapper modelMapper){
        this.modelMapper= modelMapper;
    }

    @Override
    public Loan process(LoanDTO item) throws Exception {
        return modelMapper.map(item,Loan.class);
    }
}
