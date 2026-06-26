package com.example.task_tracker.service.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class TaskEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun sendTaskCreatedEvent(taskId: Long, title: String) {
        val message = "Task created: id=$taskId, title=$title"
        kafkaTemplate.send("task-events", message)
        println("Sent to Kafka: $message")
    }
}