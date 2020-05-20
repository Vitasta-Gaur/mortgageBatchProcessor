package com.example.loan.arrears.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.time.Instant;

public class BatchListener extends JobExecutionListenerSupport {

    @Value("${file.archieve.dir}")
    private String archiveDir;

    @Override
    public void afterJob(JobExecution jobExecution) {

        File fileUnderProcess = new File(jobExecution.getJobParameters().getString("fileName"));

        switch (jobExecution.getStatus()){
            case COMPLETED:
                fileUnderProcess.renameTo(new File(archiveDir + "/" + fileUnderProcess.getName() + "_COMPLETED_" + System.currentTimeMillis()));
                break;
            case FAILED:
                fileUnderProcess.renameTo(new File(archiveDir + "/" + fileUnderProcess.getName() + "_FAILED_" + System.currentTimeMillis()));
                break;
            default:
                fileUnderProcess.renameTo(new File(archiveDir + "/" + fileUnderProcess.getName() + "_UNKNOWM_" + System.currentTimeMillis()));
        }
    }
}
