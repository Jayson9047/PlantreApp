package com.example.plantreapp.api

import android.content.Context
import com.example.plantreapp.entities.Plant
import com.example.plantreapp.repository.PlantRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession

data class ResponsePlants(
    val data: List<Plant>
)

class APIClient(context: Context) {
    private var context : Context? = null;
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    private val plantJsonAdapter = moshi.adapter<ResponsePlants>(ResponsePlants::class.java)

    init {
        this.context = context
    }



    fun loadPlants() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder().url("https://10.0.0.196:3000/api/plant").build()
            instance?.newCall(request)?.execute().use { response ->
                if (response != null) {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val plants = plantJsonAdapter.fromJson(response.body!!.source())
                    if (plants != null) {
                        val repo = context?.let { PlantRepository(it) }

                        //repo?.insertAll(plants)
                        println("Great Success")

                    }


                }
            }
        }
    }

    companion object {
        @Volatile private var instance: OkHttpClient? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildClient().also { instance = it}
        }

        private fun buildClient() = OkHttpClient.Builder().hostnameVerifier(HostnameVerifier() {s: String?, sslSession: SSLSession? ->
            val hv: HostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier()
            return@HostnameVerifier true // This needs to change before deploy app - Allows for man in the middle attacks
        }).build()
    }
}