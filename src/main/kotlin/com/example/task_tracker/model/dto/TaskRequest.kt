package com.example.task_tracker.model.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class TaskRequest(
    @field:NotBlank(message = "Title cannot be blank")
    @field:Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    val title: String,

    val description: String? = null
)
