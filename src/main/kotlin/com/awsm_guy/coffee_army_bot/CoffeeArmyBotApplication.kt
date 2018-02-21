package com.awsm_guy.coffee_army_bot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class CoffeeArmyBotApplication

fun main(args: Array<String>) {
    SpringApplication.run(CoffeeArmyBotApplication::class.java, *args)
}
