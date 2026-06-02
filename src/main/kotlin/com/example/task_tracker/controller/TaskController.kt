package com.example.task_tracker.controller

import com.example.task_tracker.model.Task
import com.example.task_tracker.model.dto.TaskRequest
import com.example.task_tracker.model.enum.TaskStatus
import com.example.task_tracker.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val service: TaskService
) {

    @GetMapping
    fun getAll(): List<Task> = service.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) : Task = service.getById(id)

    @GetMapping("/status/{status}")
    fun getByStatus(@PathVariable status: TaskStatus): List<Task> =
        service.getByStatus(status)

    @PostMapping
    fun create(@Valid @RequestBody req: TaskRequest) : Task =
        service.create(req)

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @RequestBody status: TaskStatus
    ): Task = service.updateStatus(id, status)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = service.delete(id)

}