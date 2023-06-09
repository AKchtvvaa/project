package ru.tinkoff.edu.java.bot.telegram.command;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.edu.java.bot.dto.scrapper.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.scrapper.ListLinksResponse;
import ru.tinkoff.edu.java.bot.service.ScrapperWebService;
import java.net.URI;
import java.util.stream.Collectors;

@Order(2)
@Component
public class ListCommand extends AbstractCommand {
    private final ScrapperWebService webService;

    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "показать список ссылок";
    private static final String EMPTY_LINKS_LIST_MESSAGE =
            "У вас еще нет отслеживаемых ссылок";

    public ListCommand(ScrapperWebService webService) {
        super(COMMAND, DESCRIPTION);
        this.webService = webService;
    }

    @Override
    public SendMessage handle(@NotNull Message message) {
        ListLinksResponse response = webService.getAllLinks(message.getChatId());
        String text = response.size() == 0 ? EMPTY_LINKS_LIST_MESSAGE : getFormattedText(response);
        return new SendMessage(message.getChatId().toString(), text);
    }

    @Override
    public boolean supports(@NotNull Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }

    private String getFormattedText(ListLinksResponse response) {
        java.util.List<String> links = response
                .links()
                .stream()
                .map(LinkResponse::link)
                .map(URI::toString)
                .toList();
        return "Список ваших отслеживаемых ссылок: \n" + links
                .stream()
                .map(link -> "- " + link)
                .collect(Collectors.joining("\n"));
    }
}
