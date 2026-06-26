package com.example.task_tracker.service.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class TaskEventConsumer {

    @KafkaListener(topics = ["task-events"], groupId = "task-tracker-group")
    fun listen(message: String) {
        println("Received from Kafka: $message")
    }

}