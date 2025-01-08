package com.example.communicate.data.local

import android.content.Context
import androidx.room.Room
import com.example.communicate.AppDatabase

object DatabaseInstance {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "random_string_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
