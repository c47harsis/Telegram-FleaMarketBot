package ru.c47harsis

import mu.KotlinLogging
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

private val log = KotlinLogging.logger {}

fun main() {
    val fleaMarketBot = FleaMarketBot()
    log.info { "Starting ${fleaMarketBot.botUsername}" }
    try {
        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
        botsApi.registerBot(fleaMarketBot)
    } catch (e: TelegramApiException) {
        log.error { "An error occurred when starting ${fleaMarketBot.botUsername}, message: ${e.message}" }
    }
    log.info { "${fleaMarketBot.botUsername} successfully started" }
}