package ru.tinkoff.edu.java.bot.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

@Validated
@Data
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Configuration
@Import({TelegramBotStarterConfiguration.class})
public class ApplicationConfig {
    private String test;
    private Bot bot;
    private Scrapper scrapper;

    @Validated
    @Data
    public static class Bot {
        private String token;
        private String name;
    }

    @Validated
    @Data
    public static class Scrapper {
        private String url;
    }
}