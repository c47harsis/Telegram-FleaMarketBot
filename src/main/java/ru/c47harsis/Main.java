package ru.c47harsis;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class Main {

    public static void main(String[] args) {
        FleaMarketBot fleaMarketBot = new FleaMarketBot();
        log.info("Starting {}", fleaMarketBot.getBotUsername());
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(fleaMarketBot);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        log.info("{} successfully started", fleaMarketBot.getBotUsername());
    }
}



