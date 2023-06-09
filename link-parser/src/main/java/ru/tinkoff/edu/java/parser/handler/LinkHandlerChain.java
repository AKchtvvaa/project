package ru.tinkoff.edu.java.parser.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.parser.data.LinkData;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class LinkHandlerChain {
    private final List<LinkHandler> handlers;

    public LinkData handle(String link) {
        return handlers.stream()
                .map(handler -> handler.handleLink(link))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
