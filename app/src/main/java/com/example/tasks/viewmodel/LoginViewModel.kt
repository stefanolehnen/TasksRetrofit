package com.example.tasks.viewmodel

import android.app.Application
import android.app.TaskStackBuilder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.PersonModel
import com.example.tasks.service.model.ValidationModel
import com.example.tasks.service.repository.PersonRepository
import com.example.tasks.service.repository.SecurityPreferences
import com.example.tasks.service.repository.remote.RetrofitClient

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val securityPreferences = SecurityPreferences(application.applicationContext)
    private val personRepository = PersonRepository(application.applicationContext)

    private val _login = MutableLiveData<ValidationModel>()
    val login: LiveData<ValidationModel> = _login

    private val _loggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = _loggedUser

    fun doLogin(email: String, password: String) {

        personRepository.login(email,password, object : APIListener<PersonModel> {
            override fun onSuccess(result: PersonModel) {

                securityPreferences.store(TaskConstants.SHARED.TOKEN_KEY, result.token)
                securityPreferences.store(TaskConstants.SHARED.PERSON_KEY, result.personKey)
                securityPreferences.store(TaskConstants.SHARED.PERSON_NAME, result.name)

                RetrofitClient.addHeaders(result.token, result.personKey)

                _login.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _login.value = ValidationModel(message)
            }
        })

        

    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {

        var token = securityPreferences.get(TaskConstants.HEADER.TOKEN_KEY)
        var personKey = securityPreferences.get(TaskConstants.HEADER.PERSON_KEY)

        RetrofitClient.addHeaders(token, personKey)

        _loggedUser.value = (token != "" && personKey != "")


    }

}