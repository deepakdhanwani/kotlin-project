package com.example.upworkkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class UpworkKotlinApplication

fun main(args: Array<String>) {
	runApplication<UpworkKotlinApplication>(*args)
}
