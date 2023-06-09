package ru.tinkoff.edu.java.bot.telegram;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.telegram.command.AbstractCommand;
import java.util.List;

@Slf4j
@Component
public class TrackerBot extends TelegramLongPollingBot {
    private final ApplicationConfig config;
    private final MessageHandler messageHandler;
    private final List<AbstractCommand> commands;

    public TrackerBot(ApplicationConfig config, MessageHandler messageHandler, List<AbstractCommand> commands) {
        super(config.getBot().getToken());
        this.messageHandler = messageHandler;
        this.config = config;
        this.commands = commands;
    }

    @PostConstruct
    private void init() {
        List<BotCommand> botCommands = commands
                .stream()
                .map(AbstractCommand::toBotCommand)
                .toList();
        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(botCommands);
        try {
            this.execute(setMyCommands);
        } catch (TelegramApiException e) {
            log.error("Failed to set commands: {}", e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage sendMessage = messageHandler.handle(message);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Failed to send message: {}", e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBot().getName();
    }
}
