package com.example.loan.arrears.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class LoanStepExecutionListener implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        final String fileName = jobExecution.getJobParameters().getString("fileName");

        if (stepExecution.getExitStatus() == ExitStatus.COMPLETED){
            return new FlowExecutionStatus("SUCCESS");
        }
        return new FlowExecutionStatus("FAIL");
    }
}
