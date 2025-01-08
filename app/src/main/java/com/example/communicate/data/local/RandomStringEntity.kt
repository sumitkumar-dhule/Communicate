package com.example.communicate.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "random_string_table")
data class RandomStringEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: String,
    val length: Int,
    val created: String
)

