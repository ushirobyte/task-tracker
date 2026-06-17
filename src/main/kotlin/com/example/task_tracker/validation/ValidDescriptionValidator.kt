package com.example.task_tracker.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidDescriptionValidator : ConstraintValidator<ValidDescription, String?> {
    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?
    ): Boolean {
        // null разрешен - description необязательный
        if (value == null) return true

        // Проверяем на HTML теги <b>, </div>, <script> и т.д.
        val hasHtml = value.contains(Regex("<[^>]*>"))

        // Проверяем на URL: http://, https://, www.
        val hasUrl = value.contains(Regex("(https?://|www\\.)\\S+"))

        return !hasHtml && !hasUrl
    }
}