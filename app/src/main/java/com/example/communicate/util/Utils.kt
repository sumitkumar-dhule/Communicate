package com.example.communicate.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle

fun getStringFromProvider(context: Context, length:Int): String {
    var result1: String = ""
    val contentUri = Uri.parse(DATA_URI)
    val projection = arrayOf("data")

    context.contentResolver.query(
        contentUri,
        projection,
        Bundle().apply { putInt(ContentResolver.QUERY_ARG_LIMIT, length) },
        null
    )?.use { cursor ->
        val dataCol = cursor.getColumnIndex("data")

        while (cursor.moveToNext()) {
            result1 = cursor.getString(dataCol)
        }

        cursor.close()
    }
    return result1
}