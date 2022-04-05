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


fun postRequest(serverDB : AppDatabase){

    // Lets create a JSON Object
    val myJsonObject = JSONObject()
    // within which we insert data
    myJsonObject.put("company", "DataTheorem")
    myJsonObject.put("Location_1", "Palo Alto")
    myJsonObject.put("Location_2", "Paris")
    myJsonObject.put("Location_3", "London")

    // Convert Json Object to String
    val jsonObjectString = myJsonObject.toString()

    GlobalScope.launch(Dispatchers.IO) {

        val url = URL("https://httpbin.org/post")
        // creates an instance of an HTTPS connection
        val myHttpsConnection = url.openConnection() as HttpsURLConnection

        try {
            // POST request accepting Json data-type request and response
            myHttpsConnection.requestMethod = "POST"
            myHttpsConnection.setRequestProperty("Content-Type", "application/json")
            myHttpsConnection.setRequestProperty("Accept", "application/json")

            // configure the connection to allow data download from the web server
            myHttpsConnection.doInput = true
            // configure the connection to allow data upload to the web server
            myHttpsConnection.doOutput = true

            // used when we don't know content length in advance
            // enable http request body without internal buffering
            myHttpsConnection.setChunkedStreamingMode(0)


            val myOutputStream = myHttpsConnection.outputStream
            // Creates an OutputStreamWriter that uses the default character encoding.
            // Here the data "jsonObjectString" is encoded
            // into Bytes by myOutputStreamWriter.
            val myOutputStreamWriter = OutputStreamWriter(myOutputStream)
            // The resulting bytes are accumulated in a buffer
            // before being written to myOutputStream.
            myOutputStreamWriter.write(jsonObjectString)
            // Flushes myOutputStream
            // and forces any buffered output bytes to be written out.
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

                // We create a coroutine to display Response in the UI thread
                withContext(Dispatchers.Main) {
                    // Inside this coroutine, we use gson library to pretty print response
                    val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
                    val myJsonResult = gsonBuilder.toJson(JsonParser.parseString(myResponse))
                    Log.d("Response :", myJsonResult)

                }

                // we map response data to request table fields
                val currentData = RequestEntity(
                    null,
                    myHttpsConnection.url.toString(),
                    myHttpsConnection.responseCode,
                    myHttpsConnection.requestMethod
                )
                // we use our Dao method to persist data in App Database
                serverDB.requestDao().insertData(currentData)

            } else {
                // we map unsuccessful response data to request table fields
                val currentData = RequestEntity(
                    null,
                    myHttpsConnection.url.toString(),
                    myHttpsConnection.responseCode,
                    myHttpsConnection.requestMethod
                )
                // we use our Dao method to persist data in App Database
                serverDB.requestDao().insertData(
                    currentData
                )
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
