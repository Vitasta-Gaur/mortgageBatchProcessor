package com.example.loan.arrears.batch.processor;

import com.example.loan.arrears.batch.domain.LoanPart;
import com.example.loan.arrears.batch.dto.LoanPartDTO;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class LoanPartProcessor implements ItemProcessor<LoanPartDTO , LoanPart> {

    private final ModelMapper modelMapper;

    public LoanPartProcessor(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public LoanPart process(LoanPartDTO item) throws Exception {
        return modelMapper.map(item,LoanPart.class);
    }
}
