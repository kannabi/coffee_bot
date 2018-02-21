package com.awsm_guy.coffee_army_bot.bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

@Component
class TelegramCoffeeBot: TelegramLongPollingBot() {



    override fun getBotToken(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBotUsername(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpdateReceived(update: Update?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}