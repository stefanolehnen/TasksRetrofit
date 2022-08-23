package com.example.tasks.service.repository.remote

import com.example.tasks.service.repository.local.TaskDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object{

        private lateinit var INSTANCE: Retrofit

        private fun getRetrofitIstance(): Retrofit {

            val httpClient = OkHttpClient.Builder()

            if(!::INSTANCE.isInitialized){
                synchronized(RetrofitClient::class) {
                    INSTANCE = Retrofit.Builder()
                        .baseUrl("http://devmasterteam.com/CursoAndroidAPI/")
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
            return INSTANCE

        }

        fun <T> getService(serviceClass: Class<T>): T{
            return getRetrofitIstance().create(serviceClass)
        }
    }
}