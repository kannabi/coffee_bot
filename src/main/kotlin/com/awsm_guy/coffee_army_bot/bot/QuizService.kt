package com.awsm_guy.coffee_army_bot.bot

import com.awsm_guy.coffee_army_bot.bot.pojo.QuizData
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import javax.annotation.PostConstruct

@Component
class QuizService {
    private val mLog = LoggerFactory.getLogger(QuizService::class.java)

    private val mObjectMapper by lazy { ObjectMapper() }

    @Value("\${data.path}")
    private lateinit var mDataPath: String

    private lateinit var mQuizData: QuizData

    @PostConstruct
    fun loadData() {
        try {
            mQuizData = mObjectMapper.readValue(
                    File(mDataPath).inputStream().bufferedReader().use { it.readText() },
//                    File("C:\\Users\\hekpo\\Documents\\Projects\\coffee_army_bot\\data.json").inputStream().bufferedReader().use { it.readText() },
                    QuizData::class.java
            )
            mLog.info("Quiz data loaded.")
        } catch (e: Exception){
            mLog.error("Error load quiz data:", e)
        }
    }

}