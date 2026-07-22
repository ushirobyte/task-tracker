package com.example.task_tracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaRetryTopic
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@SpringBootApplication
@EnableCaching
@EnableKafka
@EnableKafkaRetryTopic
@EnableRedisHttpSession
class TaskTrackerApplication

fun main(args: Array<String>) {
	runApplication<TaskTrackerApplication>(*args)
}
