package com.awsm_guy.coffee_army_bot.bot.pojo

import com.fasterxml.jackson.annotation.JsonProperty

import javax.annotation.Generated

class QuizData {

    @JsonProperty("result_pics")
    var resultPics: List<ResultPicsItem>? = null

    @JsonProperty("questions")
    var questions: List<QuestionsItem>? = null

    override fun toString(): String {
        return "QuizData{" +
                "result_pics = '" + resultPics + '\'' +
                ",questions = '" + questions + '\'' +
                "}"
    }
}