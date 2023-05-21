package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.edu.java.bot.service.ScrapperWebService;

@Order(3)
@Component
@RequiredArgsConstructor
public class Start implements Command {
    private final ScrapperWebService webService;
    private static final String COMMAND = "/start";
    private static final String WELCOME_MESSAGE = "Бот запущен!";

    @Override
    public SendMessage handle(Message message) {
        webService.createChat(message.getChatId());
        return new SendMessage(message.getChatId().toString(), WELCOME_MESSAGE);
    }

    @Override
    public boolean supports(Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }
}
