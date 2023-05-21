package ru.tinkoff.edu.java.bot.telegram.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {
    SendMessage handle(Message message);

    default boolean supports(Message message) {
        return false;
    }
}
