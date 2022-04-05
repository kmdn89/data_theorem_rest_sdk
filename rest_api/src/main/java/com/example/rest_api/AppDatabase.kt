package com.example.rest_api

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RequestEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun requestDao() : RequestDao


}
