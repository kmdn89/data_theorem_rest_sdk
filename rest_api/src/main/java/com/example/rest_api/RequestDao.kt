package com.example.rest_api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RequestDao {

    @Insert(entity = RequestEntity::class)
    abstract fun insertData( requestEntity: RequestEntity)

    @Query("SELECT * from requests")
    abstract fun readHistory() : List<RequestEntity>




}
