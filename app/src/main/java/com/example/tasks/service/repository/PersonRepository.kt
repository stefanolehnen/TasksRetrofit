package com.example.tasks.service.repository

import android.app.Person
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.PersonModel
import com.example.tasks.service.model.PriorityModel
import com.example.tasks.service.repository.remote.PersonService
import com.example.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository {

    val remote = RetrofitClient.getService(PersonService::class.java)

    fun login(email: String, password: String, listener: APIListener<PersonModel>){

        val call = remote.login(email, password)
        call.enqueue(object: Callback<PersonModel>{
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                val s = ""
                if(response.code() == 200){
                    response.body()?.let{ listener.onSuccess(it)}
                }else{
                    listener.onFailure(response.errorBody()!!.toString())
                }
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                val s = ""
                listener.onFailure("Erro Inesperado")
            }
        })
    }
}