package com.veselovvv.androidchatclient.ui.core

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import com.veselovvv.androidchatclient.ui.login.Validator

abstract class BaseValidationViewModel(private val validator: Validator) : ViewModel() {
    fun validateUsername(username: String, textInputLayout: TextInputLayout, errorMessage: String) =
        validate(validator.isUsernameCorrect(username), textInputLayout, errorMessage)

    fun validateEmail(email: String, textInputLayout: TextInputLayout, errorMessage: String) =
        validate(validator.isEmailCorrect(email), textInputLayout, errorMessage)

    fun validatePassword(password: String, textInputLayout: TextInputLayout, errorMessage: String) =
        validate(validator.isPasswordCorrect(password), textInputLayout, errorMessage)

    private fun validate(isCorrect: Boolean, inputLayout: TextInputLayout, errorMessage: String): Boolean {
        var noError = true

        if (!isCorrect) {
            inputLayout.error = errorMessage
            noError = false
        } else inputLayout.error = null
        return noError
    }
}