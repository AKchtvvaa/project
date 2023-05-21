package ru.tinkoff.edu.java.parser.handler;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.edu.java.parser.data.LinkData;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LinkHandlerChainTest {
    private final LinkHandlerChain linkHandlerChain;

    LinkHandlerChainTest() {
        List<LinkHandler> handlers = Arrays.asList(new GitHubLinkHandler(), new StackOverflowLinkHandler());
        linkHandlerChain = new LinkHandlerChain(handlers);
    }

    @ParameterizedTest
    @MethodSource({"getParametersWrongFormat", "getParametersWrongDomain", "getParametersCorrect"})
    void handle__incorrectLink_returnNull(String link, boolean correct) {
        LinkData data = linkHandlerChain.handle(link);
        assertEquals(data != null, correct);
    }

    private Stream<Arguments> getParametersCorrect() {
        return Stream.of(
                Arguments.of("https://stackoverflow.com/questions/9706688/what", true),
                Arguments.of("https://stackoverflow.com/questions/9706688/12345", true),
                Arguments.of("https://github.com/AKchtvvaa/12345", true)
        );
    }

    private Stream<Arguments> getParametersWrongFormat() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("1", false),
                Arguments.of("github.com/", false),
                Arguments.of("https://github.com/", false),
                Arguments.of("https://github.com/AKchtvvaa", false),
                Arguments.of("https://github.com/AKchtvvaa/qwerty/qwerty", false),
                Arguments.of("https://stackoverflow.random/questions/qwerty/what", false),
                Arguments.of("https://stackoverflow.random/questions/qwerty/what/qwerty", false),
                Arguments.of("https://stackoverflow.random/qwerty/9706688/qwerty", false)
        );
    }

    private Stream<Arguments> getParametersWrongDomain() {
        return Stream.of(
                Arguments.of("https://ru.wikipedia.org/questions/9706688/what", false),
                Arguments.of("https://stackoverflow.random/questions/9706688/what", false)
        );
    }
}
