package com.example.task_tracker.model

import com.example.task_tracker.model.enum.TaskStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
class Task(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var title: String = "",

    var description: String? = null,

    @Enumerated(EnumType.STRING)
    var status: TaskStatus = TaskStatus.TODO,

    val createdAt: LocalDateTime = LocalDateTime.now()
) : Serializable
