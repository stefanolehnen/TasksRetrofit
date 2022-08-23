package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.PersonModel
import com.example.tasks.service.repository.PersonRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {


    private val personRepository = PersonRepository(application.applicationContext)

    private val _login = MutableLiveData<PersonModel>()
    val login: LiveData<PersonModel> = _login

    private val _failure = MutableLiveData<String>()
    val failure: LiveData<String> = _failure

    fun doLogin(email: String, password: String) {

        personRepository.login(email,password, object : APIListener<PersonModel> {
            override fun onSuccess(result: PersonModel) {
                _login.value = result
            }

            override fun onFailure(message: String) {
                _failure.value = message
            }
        })

        

    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}