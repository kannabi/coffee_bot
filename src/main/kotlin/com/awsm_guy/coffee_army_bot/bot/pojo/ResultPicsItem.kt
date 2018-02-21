package com.awsm_guy.coffee_army_bot.bot.pojo

import com.fasterxml.jackson.annotation.JsonProperty

import javax.annotation.Generated

class ResultPicsItem {

    @JsonProperty("pic_uri")
    var picUri: String? = null

    @JsonProperty("rate")
    var rate: Int? = null

    override fun toString(): String {
        return "ResultPicsItem{" +
                "pic_uri = '" + picUri + '\'' +
                ",rate = '" + rate + '\'' +
                "}"
    }
}