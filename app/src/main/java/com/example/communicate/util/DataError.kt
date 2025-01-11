package com.example.communicate.util

sealed interface DataError: Error {

    enum class Local: DataError {
        REQUEST_TIMEOUT,
        DISK_FULL,
        NO_PROVIDER,
        DATABASE_ERROR,
        INVALID_DATA,
        UNKNOWN
    }
}