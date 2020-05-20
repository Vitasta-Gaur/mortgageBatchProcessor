package com.example.loan.arrears.integration.transformer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Component
public class FileToLoanJobTransformer implements ApplicationContextAware {

    @Autowired
    @Qualifier("loanJob")
    private Job job;

    private ApplicationContext context;

    @Transformer(inputChannel = "loanJobChannel", outputChannel = "jobChannel")
    public JobLaunchRequest transform(File aFile) {

        String fileName = aFile.getAbsolutePath();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fileName", fileName)
                .addDate("dateTime", new Date())
                .toJobParameters();


        return new JobLaunchRequest(job, jobParameters);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
