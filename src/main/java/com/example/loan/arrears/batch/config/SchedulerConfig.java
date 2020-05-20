package com.example.loan.arrears.batch.config;


import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

//@EnableScheduling
//@Configuration
public class SchedulerConfig {

    @Autowired
    public Job job;

    @Autowired
    public JobRepository jobRepository;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(15);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(30);
        return taskExecutor;
    }

    @Bean
    public JobLauncher jobLauncher(){
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setTaskExecutor(taskExecutor());
        jobLauncher.setJobRepository(jobRepository);
        return jobLauncher;
    }

    @Scheduled(fixedDelay = 30000)
    public void run() {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("currentTime", new JobParameter(System.currentTimeMillis()));
        maps.put("message", new JobParameter("processing mortage arrears"));
        JobParameters jobParameters = new JobParameters(maps);

        try {
            final JobExecution jobExecution = jobLauncher().run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
