package com.veselovvv.androidchatclient.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.ui.main.MainActivity
import de.hdodenhof.circleimageview.CircleImageView

class RegisterFragment : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var avatarCircleImageView: CircleImageView
    private lateinit var selectAvatarMaterialTextView: MaterialTextView
    private lateinit var usernameTextInputLayout: TextInputLayout
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var createAccountButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ChatApp).registerViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameTextInputLayout = view.findViewById(R.id.username_text_input_layout_register)
        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_register)
        passwordTextInputLayout = view.findViewById(R.id.password_text_input_layout_register)
        usernameEditText = usernameTextInputLayout.editText as TextInputEditText
        emailEditText = emailTextInputLayout.editText as TextInputEditText
        passwordEditText = passwordTextInputLayout.editText as TextInputEditText

        selectAvatarMaterialTextView = view.findViewById(R.id.select_avatar_textview_register)
        selectAvatarMaterialTextView.setOnClickListener {
            //TODO
        }

        createAccountButton = view.findViewById(R.id.create_account_button_register)
        createAccountButton.setOnClickListener {
            var allFieldsAreCorrect = true

            if (!viewModel.validateUsername(usernameEditText.text.toString(), usernameTextInputLayout, getString(R.string.username_error)))
                allFieldsAreCorrect = false

            if (!viewModel.validateEmail(emailEditText.text.toString(), emailTextInputLayout, getString(R.string.email_error)))
                allFieldsAreCorrect = false

            if (!viewModel.validatePassword(passwordEditText.text.toString(), passwordTextInputLayout, getString(R.string.password_error)))
                allFieldsAreCorrect = false

            if (allFieldsAreCorrect) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}