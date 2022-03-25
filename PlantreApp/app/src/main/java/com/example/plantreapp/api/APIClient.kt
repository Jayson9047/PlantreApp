package com.example.plantreapp.api

import android.content.Context
import com.example.plantreapp.entities.PlantInfo
import com.example.plantreapp.repository.PlantRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession

data class ResponsePlants(
    val data: List<PlantInfo>
)

class APIClient(context: Context) {
    private var context : Context? = null;
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    private val plantJsonAdapter = moshi.adapter<ResponsePlants>(ResponsePlants::class.java)

    init {
        this.context = context
    }



    suspend fun loadPlants() = withContext(Dispatchers.IO) {
        var list = CompletableDeferred<List<PlantInfo>>();

            val request = Request.Builder().url("https://plantre.azurewebsites.net/api/plant").build()
            instance?.newCall(request)?.execute().use { response ->
                if (response != null) {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val res = plantJsonAdapter.fromJson(response.body!!.source())
                    if (res != null) {

                        list.complete(res.data)
                        println("Great Success")

                    } else {
                        list.complete(emptyList())
                    }


                } else {
                    list.complete(emptyList())
                }
            }
        return@withContext list.await();
    }

    companion object {
        @Volatile private var instance: OkHttpClient? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildClient().also { instance = it}
        }

        private fun buildClient() = OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(5, TimeUnit.MINUTES) // write timeout
            .readTimeout(5, TimeUnit.MINUTES)
            .hostnameVerifier(HostnameVerifier() {s: String?, sslSession: SSLSession? ->
                val hv: Boolean = HttpsURLConnection.getDefaultHostnameVerifier().verify("https://plantre.azurewebsites.net", sslSession)
                return@HostnameVerifier true // This needs to change before deploy app - Allows for man in the middle attacks
            })
            .build()
    }
}

//.hostnameVerifier(HostnameVerifier() {s: String?, sslSession: SSLSession? ->
//    val hv: HostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier()
//    return@HostnameVerifier true // This needs to change before deploy app - Allows for man in the middle attacks
//})