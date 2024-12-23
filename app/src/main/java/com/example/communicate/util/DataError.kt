package com.example.communicate.util

sealed interface DataError: Error {

    enum class Local: DataError {
        REQUEST_TIMEOUT,
        DISK_FULL,
        NO_PROVIDER,
        INVALID_DATA,
        UNKNOWN
    }
}