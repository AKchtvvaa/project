package ru.tinkoff.edu.java.bot.telegram.command;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(1)
public class HelpCommand extends AbstractCommand {
    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "показать список команд";

    public HelpCommand() {
        super(COMMAND, DESCRIPTION);
    }

    @Override
    public SendMessage handle(@NotNull Message message) {
        // TODO: implement
        return new SendMessage(message.getChatId().toString(),
                "/start - запустить бота \n" +
                "/track - начать отслеживание \n" +
                "/untrack - остановить отслеживание \n" +
                "/help - список команд");
    }

    @Override
    public boolean supports(Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }
}
