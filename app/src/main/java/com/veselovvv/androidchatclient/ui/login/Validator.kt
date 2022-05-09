package com.veselovvv.androidchatclient.ui.login

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout

interface Validator {
    fun validate(
        value: String, type: FieldType, textInputLayout: TextInputLayout, errorMessage: String
    ): Boolean
    fun isCorrect(value: String?, type: FieldType): Boolean

    class Base : Validator {
        override fun validate(
            value: String, type: FieldType, textInputLayout: TextInputLayout, errorMessage: String
        ) = if (isCorrect(value, type)) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error = errorMessage
            false
        }

        override fun isCorrect(value: String?, type: FieldType) = when (type) {
            FieldType.USERNAME -> value != null && value.length > 2
            FieldType.EMAIL -> Patterns.EMAIL_ADDRESS.matcher(value.toString()).matches()
            FieldType.PASSWORD -> value != null && value.length > 7
        }
    }
}

enum class FieldType {
    USERNAME,
    EMAIL,
    PASSWORD
}