package com.example.task_tracker.model.dto

import com.example.task_tracker.model.enum.TaskStatus

data class UpdateStatusRequest(val status: TaskStatus)
