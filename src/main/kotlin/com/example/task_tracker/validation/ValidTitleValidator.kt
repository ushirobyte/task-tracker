package com.example.task_tracker.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidTitleValidator : ConstraintValidator<ValidTitle, String> {
    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?
    ): Boolean {
        if (value.isNullOrBlank()) return false
        return value.matches(Regex("^[a-zA-Z\\s]+\$"))
    }


}