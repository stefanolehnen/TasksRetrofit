package com.example.tasks.service.model

class ValidationModel(var message: String = "") {

    private val status: Boolean = true
    private var validationMessage = ""

    init {
        validationMessage = message
    }

    fun status() = status
    fun message() = validationMessage
}