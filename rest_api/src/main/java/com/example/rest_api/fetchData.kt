package com.example.rest_api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchData(db : AppDatabase){

    withContext(Dispatchers.IO){
        val dataHistory = db.requestDao().readHistory()
        for (i in 0 until dataHistory.size-1){
            Log.d("Request ID", dataHistory[i].requestID.toString())
            Log.d("Request URL", dataHistory[i].url.toString())
            Log.d("Request URL", dataHistory[i].codeResponse.toString())
        }
    }

}