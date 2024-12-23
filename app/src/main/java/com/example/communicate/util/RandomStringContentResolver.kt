package com.example.communicate.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import javax.inject.Inject

class RandomStringContentResolver @Inject constructor(private val applicationContext: Context) {

    fun getResponseFromProvider(length:Int): String? {

        var jsonResponse: String? = null
        val contentUri = Uri.parse(DATA_URI)
        val projection = arrayOf("data")

        applicationContext.contentResolver.query(
            contentUri,
            projection,
            Bundle().apply { putInt(ContentResolver.QUERY_ARG_LIMIT, length) },
            null
        )?.use { cursor ->
            val dataCol = cursor.getColumnIndex("data")

            while (cursor.moveToNext()) {
                jsonResponse = cursor.getString(dataCol)
            }

            cursor.close()
        }
        return jsonResponse
    }


}