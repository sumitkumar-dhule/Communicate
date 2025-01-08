package com.example.communicate.presentation.util

import com.example.communicate.util.Result
import com.example.communicate.util.ValidationError

fun isInvalidStringLength(length: String): Result<Int, ValidationError> {
    var validLength = 0
    try {
        validLength = length.toInt()
    } catch (exception: Exception) {
        return Result.Error(ValidationError.StringLength.PARSEING)
    }

    if (validLength == 0) {
        return Result.Error(ValidationError.StringLength.ZERO)
    }

    if (validLength < 0) {
        return Result.Error(ValidationError.StringLength.NEGATIVE)
    }

    return Result.Success(validLength)
}