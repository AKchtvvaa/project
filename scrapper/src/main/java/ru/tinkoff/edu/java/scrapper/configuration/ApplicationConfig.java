package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import java.time.Duration;


@EnableScheduling
@Validated
@Data
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Configuration
public class ApplicationConfig {
    private String test;
    private Scheduler scheduler;
    private GitHub gitHub;
    private StackOverflow stackOverflow;

    @Validated
    @Data
    public static class Scheduler {
        private Duration interval;
    }

    @Validated
    @Data
    public static class GitHub {
        private String url = "https://api.github.com";
    }

    @Validated
    @Data
    public static class StackOverflow {
        private String url = "https://stackoverflow.com/2.3";
    }
}
