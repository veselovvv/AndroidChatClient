package com.veselovvv.androidchatclient.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.ui.core.BaseFragment
import com.veselovvv.androidchatclient.ui.main.MainActivity

class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private lateinit var viewModel: LoginViewModel
    private lateinit var validator: Validator
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var errorTextView: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = app.getLoginViewModel()
        validator = app.getValidator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_login)
        passwordTextInputLayout = view.findViewById(R.id.password_text_input_layout_login)
        emailEditText = emailTextInputLayout.editText as TextInputEditText
        passwordEditText = passwordTextInputLayout.editText as TextInputEditText
        errorTextView = view.findViewById(R.id.error_textview_login)

        view.findViewById<MaterialButton>(R.id.button_login).setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            var allFieldsAreCorrect = true

            if (!validator.validate(
                    email, FieldType.EMAIL, emailTextInputLayout, getString(R.string.email_error)
                )) allFieldsAreCorrect = false
            if (!validator.validate(
                    password, FieldType.PASSWORD, passwordTextInputLayout, getString(R.string.password_error)
                )) allFieldsAreCorrect = false

            if (allFieldsAreCorrect) {
                viewModel.login(email, password)
                viewModel.observe(this) {
                    it.map(object : Navigate {
                        override fun navigate() {
                            startActivity(Intent(requireActivity(), MainActivity::class.java))
                            requireActivity().finish()
                        }
                    })
                    it.map(errorTextView)
                }
            }
        }

        view.findViewById<MaterialTextView>(R.id.create_account_textview_login).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_login, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}