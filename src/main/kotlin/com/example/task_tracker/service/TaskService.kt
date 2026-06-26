package com.example.task_tracker.service

import com.example.task_tracker.model.Task
import com.example.task_tracker.model.dto.TaskRequest
import com.example.task_tracker.model.enum.TaskStatus
import com.example.task_tracker.repository.TaskRepository
import com.example.task_tracker.service.kafka.TaskEventProducer
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TaskService(
    private val repo: TaskRepository,
    private val eventProducer: TaskEventProducer
) {

    fun getAll(): List<Task> = repo.findAll()

    fun getById(id: Long): Task =
        repo.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task #$id not found")

    fun getByStatus(status: TaskStatus): List<Task> =
        repo.findAllByStatus(status)

    fun create(req: TaskRequest): Task {
        val task = repo.save(Task(title = req.title, description = req.description))
        eventProducer.sendTaskCreatedEvent(task.id ?: 0L, task.title)
        return task
    }

    fun updateStatus(id: Long, status: TaskStatus): Task {
        val task = getById(id)
        task.status = status
        return repo.save(task)
    }

    fun delete(id: Long) {
        if (!repo.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task #$id not found")
        }
        repo.deleteById(id)
    }

}