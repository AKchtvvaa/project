package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.edu.java.bot.service.ScrapperWebService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(4)
@Slf4j
@Component
public class Track extends AbstractCommand {
    private final ScrapperWebService webService;
    private static final String COMMAND = "/track";
    private static final String DESCRIPTION = "начать отслеживание";
    private static final Pattern PATTERN = Pattern.compile("^\\s*/track (\\S+)\\s*$");
    private static final String SUCCESS_RESPONSE = "добавить ссылку в список";
    private static final String WRONG_FORMAT_RESPONSE = "Используйте формат: /track <link>";

    public Track(ScrapperWebService webService) {
        super(COMMAND, DESCRIPTION);
        this.webService = webService;
    }

    @Override
    public SendMessage handle(Message message) {
        String text = message.getText();
        Matcher matcher = PATTERN.matcher(text);

        if (!matcher.matches()) {
            return new SendMessage(message.getChatId().toString(), WRONG_FORMAT_RESPONSE);
        }

        String url = matcher.group(1);
        log.info("Created link {}", url);
        webService.createLink(message.getChatId(), url);
        return new SendMessage(message.getChatId().toString(), SUCCESS_RESPONSE);
    }

    @Override
    public boolean supports(Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }
}
