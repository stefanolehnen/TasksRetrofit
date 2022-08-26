package com.example.tasks.service.repository.remote

import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.repository.local.TaskDatabase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object{

        private lateinit var INSTANCE: Retrofit
        private var personKey = ""
        private var tokenKey = ""

        private fun getRetrofitIstance(): Retrofit {

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(TaskConstants.HEADER.PERSON_KEY, personKey)
                    .addHeader(TaskConstants.HEADER.TOKEN_KEY, tokenKey)
                    .build()
                chain.proceed(request)
            }

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

        fun addHeaders(tokenValue: String, personKeyValue: String){
            tokenKey = tokenValue
            personKey = personKeyValue
        }

        fun <T> getService(serviceClass: Class<T>): T{
            return getRetrofitIstance().create(serviceClass)
        }

    }
}