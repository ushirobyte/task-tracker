package com.example.task_tracker.service.kafka

import com.example.task_tracker.model.Task
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper

@Service
class TaskEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    fun sendTaskCreatedEvent(task: Task) {
        val json = objectMapper.writeValueAsString(task)
        kafkaTemplate.send("task-events", task.id.toString(), json)
        println("Sent to Kafka: $json")
    }
}