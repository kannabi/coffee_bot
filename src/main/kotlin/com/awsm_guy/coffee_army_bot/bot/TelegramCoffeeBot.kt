package com.awsm_guy.coffee_army_bot.bot


import com.awsm_guy.coffee_army_bot.bot.pojo.QuestionsItem
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.send.SendPhoto
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.exceptions.TelegramApiException
import java.io.File

@Component
class TelegramCoffeeBot: TelegramLongPollingBot() {

    private val mLog = LoggerFactory.getLogger(TelegramCoffeeBot::class.java)

    @Value("\${bot.token}")
    private lateinit var mBotToken: String

    @Value("\${bot.username}")
    private lateinit var mBotName: String

    @Autowired @Lazy
    private lateinit var mQuizService: QuizService

    override fun getBotToken(): String = mBotToken

    override fun getBotUsername(): String = mBotName

    override fun onUpdateReceived(update: Update) {
        mQuizService.processMessage(update)
    }

    fun executeMessage(message: SendMessage){
        execute(message)
    }

    fun sendQuestion(chatId: Long, questionsItem: QuestionsItem){
        try {
            execute(getQuestionMessage(chatId, questionsItem))
        } catch (e: TelegramApiException){
            mLog.error("Can't send question: ", e)
        }
    }

    fun sendPicture(chatId: Long, picture: File){
        try {
            sendPhoto(
                    with(SendPhoto()){
                        setChatId(chatId)
                        setNewPhoto(picture)
                        this
                    }
            )
        } catch (e: TelegramApiException){
            mLog.error("Can't send picture:", e)
        }
    }

    private fun getQuestionMessage(chatId: Long, question: QuestionsItem) : SendMessage{
        return with(SendMessage()){
            setChatId(chatId)
            text = question.body

            replyMarkup = with(ReplyKeyboardMarkup()) keyboard@ {
                this.keyboard = question.answers?.map {
                    with(KeyboardRow()){
                        add(it)
                        this
                    }
                }
                oneTimeKeyboard = true
                this
            }
            this
        }
    }

    fun sendPlainTextMessage(chatId: Long, message: String){
        try {
            executeMessage(SendMessage()
                    .setChatId(chatId)
                    .setText(message))
        } catch (e: TelegramApiException){
            mLog.error("Can't send plain message: ", e)
        }
    }
}