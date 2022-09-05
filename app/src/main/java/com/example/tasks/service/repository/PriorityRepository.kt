package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.R
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.PersonModel
import com.example.tasks.service.model.PriorityModel
import com.example.tasks.service.repository.remote.PersonService
import com.example.tasks.service.repository.remote.PriorityService
import com.example.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(val context: Context) {

    private val remote = RetrofitClient.getService(PriorityService::class.java)

    fun list(listener: APIListener<List<PriorityModel>>){
        val call = remote.list()

        call.enqueue(object: Callback<List<PriorityModel>>{
            override fun onResponse(
                call: Call<List<PriorityModel>>,
                response: Response<List<PriorityModel>>
            ) {
                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let{ listener.onSuccess(it)}
                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }
            }

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                val s = ""
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }

    private fun failResponse(str: String): String{
        return Gson().fromJson(str,String::class.java)

    }
}