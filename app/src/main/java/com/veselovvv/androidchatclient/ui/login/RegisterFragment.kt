package com.veselovvv.androidchatclient.ui.login

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.ui.core.BaseFileUploadFragment
import com.veselovvv.androidchatclient.ui.fileuploading.SetPathToFile
import de.hdodenhof.circleimageview.CircleImageView

class RegisterFragment : BaseFileUploadFragment(R.layout.fragment_register) {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var validator: Validator
    private lateinit var avatarCircleImageView: CircleImageView
    private lateinit var usernameTextInputLayout: TextInputLayout
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = app.getRegisterViewModel()
        validator = app.getValidator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameTextInputLayout = view.findViewById(R.id.username_text_input_layout_register)
        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_register)
        passwordTextInputLayout = view.findViewById(R.id.password_text_input_layout_register)
        usernameEditText = usernameTextInputLayout.editText as TextInputEditText
        emailEditText = emailTextInputLayout.editText as TextInputEditText
        passwordEditText = passwordTextInputLayout.editText as TextInputEditText
        avatarCircleImageView = view.findViewById(R.id.avatar_imageview_register)

        view.findViewById<MaterialTextView>(R.id.select_avatar_textview_register).setOnClickListener {
            getPermission()
        }

        view.findViewById<MaterialButton>(R.id.create_account_button_register).setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            var allFieldsAreCorrect = true

            if (!validator.validate(
                    username, FieldType.USERNAME, usernameTextInputLayout, getString(R.string.username_error)
                )) allFieldsAreCorrect = false
            if (!validator.validate(
                    email, FieldType.EMAIL, emailTextInputLayout, getString(R.string.email_error)
                )) allFieldsAreCorrect = false
            if (!validator.validate(
                    password, FieldType.PASSWORD, passwordTextInputLayout, getString(R.string.password_error)
                )) allFieldsAreCorrect = false

            if (allFieldsAreCorrect) {
                viewModel.register(
                    username, email, password, "dd8f927d-a2af-415d-a88a-16d6142c568a", //TODO?
                    viewModel.getPathToFile()
                )
                viewModel.observe(this) {
                    it.map(requireView(), object : Navigate {
                        override fun navigate() = requireActivity().supportFragmentManager.popBackStack()
                    })
                    it.map(requireView())
                }
            }
        }
    }

    override fun doOnActivityResult(data: Uri) {
        avatarCircleImageView.setImageURI(data)
        viewModel.observeFileUploading(this) {
            it.map(object : SetPathToFile {
                override fun setPath(filePath: String) = viewModel.setPathToFile(filePath)
            })
            it.map(requireView())
        }
        viewModel.uploadFile(data)
    }
}