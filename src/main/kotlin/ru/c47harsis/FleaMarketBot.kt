package ru.c47harsis

import mu.KotlinLogging
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.c47harsis.config.Config

private val log = KotlinLogging.logger {}

class FleaMarketBot : TelegramLongPollingBot() {
    override fun getBotUsername(): String {
        return Config.BOT_USERNAME.value
            ?: throw IllegalStateException("Bot username should be defined but null")
    }

    override fun getBotToken(): String {
        return Config.BOT_TOKEN.value
            ?: throw IllegalStateException("Bot token should be defined but null")
    }

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val sendMessage = SendMessage()
            sendMessage.chatId = update.message.chatId.toString()
            sendMessage.text = update.message.text
            try {
                execute(sendMessage)
            } catch (e: TelegramApiException) {
                log.error(e.message)
            }
        }
    }
}