package com.example.task_tracker.repository

import com.example.task_tracker.model.Task
import com.example.task_tracker.model.enum.TaskStatus
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long> {

    fun findAllByStatus(status: TaskStatus): List<Task>

}