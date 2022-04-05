package com.example.rest_api

import android.content.Context
import androidx.room.Room

fun buildNewDB(context: Context) : AppDatabase{

    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "requests_database").build()
}