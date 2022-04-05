package com.example.data_theorem_rest_sdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import kotlinx.coroutines.*
import com.example.rest_api.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "requests_database").build()

        val updatedDB : AppDatabase = postData(db)

        GlobalScope.launch(Dispatchers.Main){

                fetchData(updatedDB)

        }

        // get Instance of HttpsRequestDao
        // obtain Data
        // val requestsList = db.httpsRequestsDao().readData()

    }

}



