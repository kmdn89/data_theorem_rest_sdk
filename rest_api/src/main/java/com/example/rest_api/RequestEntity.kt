package com.example.rest_api

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "requests")
data class RequestEntity(
    @PrimaryKey(autoGenerate = true)
    val requestID : Int?,
    val url : String?,
    val codeResponse : Int?
)
