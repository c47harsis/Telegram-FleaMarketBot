package ru.c47harsis;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.c47harsis.config.Config;

@Slf4j
public class FleaMarketBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return Config.BOT_USERNAME.getValue();
    }

    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN.getValue();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("TEST");

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }
}
