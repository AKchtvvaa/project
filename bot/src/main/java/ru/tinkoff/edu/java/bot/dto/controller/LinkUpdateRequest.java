package ru.tinkoff.edu.java.bot.dto.controller;

import java.util.List;

public record LinkUpdateRequest(
        Integer id,
        String url,
        String description,
        List<Integer> tgChatsIds
) {}
