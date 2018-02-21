package com.awsm_guy.coffee_army_bot.bot.pojo

import com.fasterxml.jackson.annotation.JsonProperty

import javax.annotation.Generated

class QuizData {
    @JsonProperty("hello_message")
    var helloMessage: String = ""

    @JsonProperty("end_message")
    var endMessage: String = ""

    @JsonProperty("result_pics")
    var resultPics: List<ResultPicsItem> = mutableListOf()

    @JsonProperty("questions")
    var questions: List<QuestionsItem> = mutableListOf()

    override fun toString(): String {
        return "QuizData{" +
                "result_pics = '" + resultPics + '\'' +
                ",questions = '" + questions + '\'' +
                "}"
    }
}