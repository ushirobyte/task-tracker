package com.example.task_tracker.service.kafka

import com.example.task_tracker.model.Task
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper
import java.time.Duration

@Service
class TaskEventConsumer(
    private val taskRedisTemplate: RedisTemplate<String, Task>,
    private val objectMapper: ObjectMapper
) {

    @RetryableTopic(attempts = "3")
    @KafkaListener(topics = ["task-events"], groupId = "task-tracker-group")
    fun listen(message: String) {
        println("Received from Kafka: $message")

        val task = objectMapper.readValue(message, Task::class.java)

        taskRedisTemplate.opsForValue().set(
            "task:${task.id}",
            task,
            Duration.ofMinutes(10)
        )

        println("Cached in Redis: task:${task.id}")
    }

}