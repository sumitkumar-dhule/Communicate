package com.example.communicate

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.communicate.data.local.RandomStringDao
import com.example.communicate.data.local.RandomStringEntity

@Database(entities = [RandomStringEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun randomStringDao(): RandomStringDao
}
