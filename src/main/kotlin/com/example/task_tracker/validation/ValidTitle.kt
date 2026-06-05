package com.example.task_tracker.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidTitleValidator::class])
annotation class ValidTitle(

    val message: String = "Title must contain only letters and spaces",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []

    )