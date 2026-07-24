package com.example.task_tracker.config

import com.example.task_tracker.model.Task
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisTaskConfig {

    @Bean
    fun taskRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Task> {
        val template = RedisTemplate<String, Task>()
        template.connectionFactory = connectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = RedisSerializer.json()
        return template
    }

}