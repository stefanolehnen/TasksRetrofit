package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.PersonModel
import com.example.tasks.service.repository.PersonRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {


    private val personRepository = PersonRepository()
    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {

        personRepository.login(email,password, object : APIListener<PersonModel> {
            override fun onSuccess(result: PersonModel) {

            }

            override fun onFailure(message: String) {

            }
        })

        

    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}