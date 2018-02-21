package com.awsm_guy.coffee_army_bot.bot

class Chat(val chatId: Long, val firstName: String,
           val lastName: String, val nickName: String) {
    var currentRate = 0
    var currentStep = 0

}