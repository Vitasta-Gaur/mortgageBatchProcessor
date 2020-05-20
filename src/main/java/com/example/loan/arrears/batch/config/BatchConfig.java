package com.example.loan.arrears.batch.config;

import com.example.loan.arrears.batch.domain.Loan;
import com.example.loan.arrears.batch.domain.LoanPart;
import com.example.loan.arrears.batch.dto.LoanDTO;
import com.example.loan.arrears.batch.dto.LoanPartDTO;
import com.example.loan.arrears.batch.listener.BatchListener;
import com.example.loan.arrears.batch.listener.LoanStepExecutionListener;
import com.example.loan.arrears.batch.processor.LoanPartProcessor;
import com.example.loan.arrears.batch.processor.LoanProcessor;
import com.example.loan.arrears.batch.repository.LoanPartRepository;
import com.example.loan.arrears.batch.repository.LoanRepository;
import com.example.loan.arrears.batch.writer.LoanPartWriter;
import com.example.loan.arrears.batch.writer.LoanWriter;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanPartRepository loanPartRepository;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public LoanProcessor loanProcessor(){
        return new LoanProcessor(modelMapper());
    }

    @Bean
    public LoanPartProcessor loanPartProcessor(){
        return new LoanPartProcessor(modelMapper());
    }

    @Bean
    public LoanWriter loanWriter(){
        return new LoanWriter(loanRepository);
    }

    @Bean
    public LoanPartWriter loanPartWriter(){
        return new LoanPartWriter(loanPartRepository);
    }

    @Bean
    public BatchListener batchListener(){
        return new BatchListener();
    }

    @Bean
    public LoanStepExecutionListener loanStepExecutionListener(){
        return new LoanStepExecutionListener();
    }


    @Bean
    @StepScope
    public FlatFileItemReader<LoanDTO> loanReader(@Value("#{jobParameters[fileName]}") String resource){


            return  new FlatFileItemReaderBuilder<LoanDTO>()
                    .name("loanReader")
                    //.resource(new FileSystemResource("src\\main\\resources\\data\\loan_5345.csv"))
                    .resource(new FileSystemResource(resource))
                    .delimited()
                    .names("loanNumber", "loanHolderName", "totalMortageAmount", "totalAmountPaid")
                    .fieldSetMapper(new BeanWrapperFieldSetMapper<LoanDTO>() {{
                        setTargetType(LoanDTO.class);
                    }})
                    .build();

    }



    @Bean
    @StepScope
    public FlatFileItemReader<LoanPartDTO> loanPartReader(@Value("#{jobParameters[fileName]}") String resource){

        return new FlatFileItemReaderBuilder<LoanPartDTO>()
                .name("loanPartReader")
                //.resource(new FileSystemResource("src\\main\\resources\\data\\loanpart.csv"))
               .resource(new FileSystemResource(resource))
                .delimited()
                .names("loanPartId", "loanNumber","typeOfLoan")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<LoanPartDTO>() {{
                    setTargetType(LoanPartDTO.class);
                }})
                .build();

    }

    //Configure Jobs

    @Bean
    public Job loanJob() {

        return jobBuilderFactory.get("importFiles")
                .incrementer(new RunIdIncrementer())
                .flow(loadLoanFileStep())
                .end()
                .listener(batchListener())
                .build();
    }

    @Bean
    public Job loanPartJob() {

        return jobBuilderFactory.get("importPartFiles")
                .incrementer(new RunIdIncrementer())
                .flow(loadLoanPartFileStep())
                .end()
                .listener(batchListener())
                .build();
    }

    @Bean
    public Step loadLoanFileStep() {
        return stepBuilderFactory.get("loadLoanFileStep")
                .<LoanDTO, Loan> chunk(10)
                .reader(loanReader("fileName"))
                .processor(loanProcessor())
                .writer(loanWriter())
                .build();
    }

    @Bean
    public Step loadLoanPartFileStep() {
        return stepBuilderFactory.get("loadLoanPartFileStep")
                .<LoanPartDTO, LoanPart> chunk(10)
                .reader(loanPartReader("fileName"))
                .processor(loanPartProcessor())
                .writer(loanPartWriter())
                .build();
    }


}
