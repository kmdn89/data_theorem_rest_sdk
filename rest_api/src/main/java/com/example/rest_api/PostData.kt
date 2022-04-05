package com.example.rest_api

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import javax.net.ssl.HttpsURLConnection


fun postData(serverDB : AppDatabase) : AppDatabase {

    val myJsonObject = JSONObject()
    myJsonObject.put("company", "DataTheorem")
    myJsonObject.put("Location_1", "Palo Alto")
    myJsonObject.put("Location_2", "Paris")
    myJsonObject.put("Location_3", "London")

    val jsonObjectString = myJsonObject.toString()

    GlobalScope.launch(Dispatchers.IO) {

        val url = URL("https://httpbin.org/post")
        val myHttpsConnection = url.openConnection() as HttpsURLConnection


        try {

            myHttpsConnection.requestMethod = "POST"
            myHttpsConnection.setRequestProperty("Content-Type", "application/json")
            myHttpsConnection.setRequestProperty("Accept", "application/json")

            myHttpsConnection.doInput = true
            myHttpsConnection.doOutput = true

            myHttpsConnection.setChunkedStreamingMode(0)

            val myOutputStream = myHttpsConnection.outputStream
            val myOutputStreamWriter = OutputStreamWriter(myOutputStream)
            myOutputStreamWriter.write(jsonObjectString)
            myOutputStreamWriter.flush()
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

                // we map response data to request table fields
                val currentData = RequestEntity(
                    null,
                    myHttpsConnection.url.toString(),
                    myHttpsConnection.responseCode)
                // we use our Dao method to persist data in App Database
                serverDB.requestDao().insertData(
                    currentData)


                withContext(Dispatchers.Main) {

                    val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
                    val myJsonResult = gsonBuilder.toJson(JsonParser.parseString(myResponse))
                    Log.d("Response :", myJsonResult)
                    Log.d("Response Headers: ", myHttpsConnection.headerFields.toString())
                    Log.d("Response Code", myHttpsConnection.responseCode.toString())
                    Log.d("Response Message ", myHttpsConnection.responseMessage)
                    Log.d("Url : ", myHttpsConnection.url.toString())
                }

            } else {
                Log.e("HTTPsCONNECTION_ERROR", myResponseCode.toString())
                Log.e("HTTPsCONNECTION_HEADER", myHttpsConnection.headerFields.toString())
            }
        } catch (e: Exception) {
            Log.e("Exception error", e.toString())
        } finally {
            myHttpsConnection.disconnect()
        }

    }
    return serverDB

}
