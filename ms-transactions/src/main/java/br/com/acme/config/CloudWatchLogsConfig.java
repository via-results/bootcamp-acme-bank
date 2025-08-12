package br.com.acme.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;

@Configuration
public class CloudWatchLogsConfig {

    @Bean
    public CloudWatchLogsClient cloudWatchLogsClient() {
        return CloudWatchLogsClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(java.net.URI.create("http://localhost:4566"))
                .credentialsProvider(() -> software.amazon.awssdk.auth.credentials.AwsBasicCredentials
                        .create("cbgomes", "cbgomes"))
                .build();
    }
}
