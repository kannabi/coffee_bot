package com.awsm_guy.coffee_army_bot.bot


import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

@Component
class TelegramCoffeeBot: TelegramLongPollingBot() {

    private val mLog = LoggerFactory.getLogger(TelegramCoffeeBot::class.java)

    @Value("\${bot.token}")
    private lateinit var mBotToken: String

    @Value("\${bot.username}")
    private lateinit var mBotName: String

    override fun getBotToken(): String = mBotToken

    override fun getBotUsername(): String = mBotName

    override fun onUpdateReceived(update: Update?) {}
}