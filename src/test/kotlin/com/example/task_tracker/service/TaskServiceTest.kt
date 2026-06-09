package com.example.task_tracker.service

import com.example.task_tracker.model.Task
import com.example.task_tracker.model.dto.TaskRequest
import com.example.task_tracker.model.enum.TaskStatus
import com.example.task_tracker.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows
import org.springframework.web.server.ResponseStatusException
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskServiceTest {

    private val taskRepository = mockk<TaskRepository>()
    private val taskService = TaskService(taskRepository)

    // ✅ Happy path - создание задачи
    @Test
    fun `should create task successfully`() {
        val request = TaskRequest(title = "Learn Kotlin", description = "Study coroutines")
        val savedTask = Task(id = 1, title = "Learn Kotlin", description = "Study coroutines")

        every { taskRepository.save(any()) } returns savedTask

        val result = taskService.create(request)

        assertEquals("Learn Kotlin", result.title)
        assertEquals(TaskStatus.TODO, result.status)
        verify(exactly = 1) { taskRepository.save(any()) }
    }

    // ✅ Happy path - получить задачу по ID
    @Test
    fun `should return task by id`() {
        val task = Task(id = 1, title = "Learn Kotlin")

        every { taskRepository.findById(1L) } returns Optional.of(task)

        val result = taskService.getById(1L)

        assertEquals(1L, result.id)
        assertEquals("Learn Kotlin", result.title)
    }

    // ❌ Ошибка - задача не найдена
    @Test
    fun `should throw exception when task not found`() {
        every { taskRepository.findById(99L) } returns Optional.empty()

        val exception = assertThrows(ResponseStatusException::class.java) {
            taskService.getById(99L)
        }

        assertEquals(404, exception.statusCode.value())
    }

    // ✅ Happy path - Обновить статус
    @Test
    fun `should update task status`() {
        val task = Task(id = 1, title = "Learn Kotlin", status = TaskStatus.TODO)

        every { taskRepository.findById(1L) } returns Optional.of(task)
        every { taskRepository.save(any()) } returns Task(id = 1, title = "Learn Kotlin", status = TaskStatus.IN_PROGRESS)

        val result = taskService.updateStatus(1L, TaskStatus.IN_PROGRESS)

        assertEquals(TaskStatus.IN_PROGRESS, result.status)
    }

    // ❌ Ошибка - удалить нусуществующую задачу
    @Test
    fun `should throw exception when deleting non-existent task`() {
        every { taskRepository.existsById(99L) } returns false

        val exception = assertThrows(ResponseStatusException::class.java) {
            taskService.delete(99L)
        }

        assertEquals(404, exception.statusCode.value())
    }

}