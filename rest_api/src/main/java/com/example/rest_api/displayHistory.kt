package com.example.rest_api

import android.util.Log
import kotlinx.coroutines.*

fun displayHistory(db: AppDatabase){
    GlobalScope.launch(Dispatchers.Default) {
        delay(1000)
        val dataHistory = withContext(Dispatchers.IO) { db.requestDao().readHistory() }

        for (i in 0 until dataHistory.size - 1) {

            Log.d("Request ID", dataHistory[i].requestID.toString())
            Log.d("Request URL", dataHistory[i].url.toString())
            Log.d("Code Response", dataHistory[i].codeResponse.toString())
            Log.d("Request Method", dataHistory[i].requestMethod.toString())
        }
    }
}
