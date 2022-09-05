package com.example.tasks.service.model

class ValidationModel(var message: String = "") {

    private var status: Boolean = true
    private var validationMessage = ""

    init {
        if(message != "") {
            validationMessage = message
            status = false
        }
    }

    fun status() = status
    fun message() = validationMessage
}