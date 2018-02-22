package com.awsm_guy.coffee_army_bot.bot

import com.awsm_guy.coffee_army_bot.bot.pojo.QuizData
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Update
import java.io.File
import javax.annotation.PostConstruct


@Component
class QuizService {
    private val mLog = LoggerFactory.getLogger(QuizService::class.java)

    private val mObjectMapper by lazy { ObjectMapper() }

    @Value("\${data.path}")
    private lateinit var mDataPath: String

    @Volatile private lateinit var mQuizData: QuizData

    @Volatile private var mChatsCache = mutableMapOf<Long, Chat>()

    @Volatile private var mDoneChats = mutableListOf<Long>()

    @Autowired
    @Volatile private lateinit var mBot: TelegramCoffeeBot

    @PostConstruct
    private fun loadData() {
        try {
            mQuizData = mObjectMapper.readValue(
                    ClassPathResource("data.json")
                            .inputStream.bufferedReader().use { it.readText() },
                    QuizData::class.java
            )
            mLog.info("Quiz data loaded.")
        } catch (e: Exception){
            mLog.error("Error load quiz data:", e)
        }
    }

    @Async
    fun processMessage(update: Update) {
        when (update.message.text) {
            "/start" -> startQuiz(update)
            "/ping" -> answerToPing(update.message.chatId)
            else -> processAnswer(update.message.chatId, update.message.text)
        }
    }

    private fun answerToPing(chatId: Long) {
        mBot.sendPlainTextMessage(chatId, "Bot work correctly\n\nNumber of current dialogs: ${mChatsCache.size}\n\nDialogs passed: ${mDoneChats.size}")
    }

    private fun processAnswer(chatId: Long, answer: String) {
        mChatsCache[chatId]?.let {
            val correctAnswer = mQuizData.questions[it.currentStep].correctAnswers

            mLog.info("User ${it.chatId} send $answer")
            mBot.sendPlainTextMessage(chatId,
                                        if (answer == correctAnswer){
                                            it.currentRate++
                                            mLog.info("User ${it.chatId} answer correct")
                                             "Верно!"
                                        } else {
                                            mLog.info("User ${it.chatId} don't answer correct")
                                            "Неверно. На самом деле: $correctAnswer"
                                        })

            it.currentStep++
            if (it.currentStep == mQuizData.questions.size){
                endQuiz(it)
                return
            }
            mBot.sendQuestion(chatId, mQuizData.questions[it.currentStep])
        }
    }



    private fun startQuiz(update: Update) {
        val chatId = update.message.chatId
        if (mDoneChats.contains(chatId)){
            mBot.sendPlainTextMessage(chatId, "Вы уже прошли опрос")
            return
        }
        with(update.message.from){
            mLog.info("User $chatId start quiz")
            mChatsCache.put(chatId, Chat(chatId))
        }
        mBot.sendPlainTextMessage(chatId, mQuizData.helloMessage)
        mBot.sendQuestion(chatId, mQuizData.questions[0])
    }

    private fun endQuiz(chat: Chat) {
        with(chat){
            mChatsCache.remove(chatId)
            mDoneChats.add(chatId)
            mLog.info("User $chatId end quiz")
            mBot.sendPlainTextMessage(chatId, mQuizData.endMessage)
            mBot.sendPicture(chatId, getFile("${mQuizData.resultPics[currentRate].picUri}"))
        }
    }

    private fun getFile(path: String): File {
        val classPathResource = ClassPathResource(path)

        val inputStream = classPathResource.inputStream
        val somethingFile = File.createTempFile(path.split(".")[0], ".jpg")
        try {
            FileUtils.copyInputStreamToFile(inputStream, somethingFile)
        } finally {
            IOUtils.closeQuietly(inputStream)
        }

        return somethingFile
    }
}