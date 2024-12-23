package com.example.communicate.util

sealed interface ValidationError: Error {

    enum class StringLength: ValidationError {
        ZERO,
        NEGATIVE,
        PARSEING
    }
}