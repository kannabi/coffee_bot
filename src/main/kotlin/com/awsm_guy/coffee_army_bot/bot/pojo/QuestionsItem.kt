package com.awsm_guy.coffee_army_bot.bot.pojo

import com.fasterxml.jackson.annotation.JsonProperty

import javax.annotation.Generated

class QuestionsItem {

    @JsonProperty("answers")
    var answers: List<String>? = null

    @JsonProperty("correct_answers")
    var correctAnswers: String? = null

    @JsonProperty("id")
    var id: String? = null

    @JsonProperty("body")
    var body: String? = null

    override fun toString(): String {
        return "QuestionsItem{" +
                "answers = '" + answers + '\'' +
                ",correct_answers = '" + correctAnswers + '\'' +
                ",id = '" + id + '\'' +
                ",body = '" + body + '\'' +
                "}"
    }
}