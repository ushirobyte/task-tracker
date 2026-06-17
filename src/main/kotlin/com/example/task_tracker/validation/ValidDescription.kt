package com.example.task_tracker.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidDescriptionValidator::class])
annotation class ValidDescription(
    val message: String = "Description must not contain HTML tags or URLs",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
