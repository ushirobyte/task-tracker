package com.example.task_tracker.service.kafka

import org.springframework.kafka.annotation.BackOff
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.stereotype.Service

@Service
class TaskEventConsumer {

    @RetryableTopic(
        attempts = "3",                                         // 1 попытка + 2 retry = 3 всего
        autoCreateTopics = "false"
    )
    @KafkaListener(topics = ["task-events"], groupId = "task-tracker-group")
    fun listen(message: String) {
        println("Received from Kafka: $message")

        //
        if (message.contains("fail")) {
            throw RuntimeException("Simulated filure for message")
        }
    }

}