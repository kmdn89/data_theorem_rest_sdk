package com.example.rest_api

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

fun getData() {

    GlobalScope.launch(Dispatchers.IO) {

        val url = URL("https://httpbin.org/get")
        val myHttpsURLConnection = url.openConnection() as HttpsURLConnection
        myHttpsURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
        myHttpsURLConnection.requestMethod = "GET"

        myHttpsURLConnection.doInput = true
        myHttpsURLConnection.doOutput = false

        val responseCode = myHttpsURLConnection.responseCode
        if (responseCode == HttpsURLConnection.HTTP_OK) {


            val myInputStream = myHttpsURLConnection.inputStream
            val myInputStreamReader = InputStreamReader(myInputStream)
            val myBufferedReader = BufferedReader(myInputStreamReader)
            val response = myBufferedReader.readText()


            // to display formated json body on the UI (main) thread
            withContext(Dispatchers.Main ) {
                val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
                val formatedJson = gsonBuilder.toJson(JsonParser.parseString(response))
                val responseHeaders = myHttpsURLConnection.headerFields.toString()

                Log.d("Response Code", responseCode.toString())

                Log.d("Response Body  ", formatedJson)

                Log.d("Response headers ", responseHeaders )


            }


        } else {

            Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())

        }
    }
}
