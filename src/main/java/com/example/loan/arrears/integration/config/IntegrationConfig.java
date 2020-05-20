package com.example.loan.arrears.integration.config;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Value("${ftp.upload.dir}")
    private String ftpUploadDir;

    @Bean
    public MessageChannel fileInputChannel() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "3000"))
    public MessageSource<File> fileReadingMessageSource() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File(ftpUploadDir));
        source.setFilter(new SimplePatternFileListFilter("*.csv"));
        source.setScanEachPoll(true);
        source.setUseWatchService(true);
        return source;
    }

    @Bean
    @ServiceActivator(inputChannel = "jobChannel", outputChannel = "nullChannel")
    protected JobLaunchingMessageHandler launcher(JobLauncher jobLauncher) {
        return new JobLaunchingMessageHandler(jobLauncher);
    }
}
