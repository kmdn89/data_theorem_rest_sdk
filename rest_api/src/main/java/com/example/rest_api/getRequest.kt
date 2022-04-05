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

fun getRequest(serverDB : AppDatabase) {


    GlobalScope.launch(Dispatchers.IO) {

        val url = URL("https://httpbin.org/get")
        val myHttpsConnection = url.openConnection() as HttpsURLConnection

        try {

            myHttpsConnection.requestMethod = "GET"
            myHttpsConnection.setRequestProperty("Accept", "application/json")

            myHttpsConnection.doInput = true
            myHttpsConnection.doOutput = false



            // Check if  connection is successful
            val myResponseCode = myHttpsConnection.responseCode

            if (myResponseCode == HttpsURLConnection.HTTP_OK) {

                // Byte Stream Response
                val myInputStream = myHttpsConnection.inputStream
                // Convert the Byte stream to Character format stream
                val myInputStreamReader = InputStreamReader(myInputStream)
                // Create a BufferedReader
                // with default buffer array
                val myBufferedReader = BufferedReader(myInputStreamReader)
                // the buffered reader reads all the text provided by inputStream
                val myResponse = myBufferedReader.readText()

                // We create a coroutine to display Response in the UI thread
                withContext(Dispatchers.Main) {
                    //here we use gson library to pretty print json response
                    val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
                    val myJsonResult = gsonBuilder.toJson(JsonParser.parseString(myResponse))
                    Log.d("Response :", myJsonResult)

                }

                // we map successful response data to request table fields
                val currentData = RequestEntity(
                    null,
                    myHttpsConnection.url.toString(),
                    myHttpsConnection.responseCode,
                    myHttpsConnection.requestMethod)
                // we use our Dao method to persist data in App Database
                serverDB.requestDao().insertData(currentData)


            } else {
                // we map unsuccessful response data to request table fields
                val currentData = RequestEntity(
                    null,
                    myHttpsConnection.url.toString(),
                    myHttpsConnection.responseCode,
                    myHttpsConnection.requestMethod)
                // we use our Dao method to persist data in App Database
                serverDB.requestDao().insertData(currentData)

                Log.e("HTTPsCONNECTION_ERROR", myResponseCode.toString())
                Log.e("HTTPsCONNECTION_HEADER", myHttpsConnection.headerFields.toString())
            }
        } catch (e: Exception) {
            Log.e("Exception error", e.toString())
        } finally {
            myHttpsConnection.disconnect()
        }

    }

}

