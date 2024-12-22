package com.example.communicate.data.mapper

import com.example.communicate.data.dto.RandomStringDto
import com.example.communicate.domain.model.RandomString
import com.google.gson.Gson
import javax.inject.Inject

class RandomStringMapper @Inject constructor() {

    fun map(json: String): RandomString =
        Gson().fromJson(json, RandomStringDto::class.java).randomText

}
