package com.veselovvv.androidchatclient.ui.banuser

import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.ui.core.BaseFragment
import com.veselovvv.androidchatclient.ui.login.FieldType
import com.veselovvv.androidchatclient.ui.login.Validator
import com.veselovvv.androidchatclient.ui.user.HandleUserInfo

class BanUserFragment : BaseFragment(R.layout.fragment_ban_user) {
    private lateinit var viewModel: BanUserViewModel
    private lateinit var validator: Validator
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ChatApp).banUserViewModel
        validator = (requireActivity().application as ChatApp).validator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(view.findViewById(R.id.toolbar_ban_user), R.string.ban_user)

        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_ban_user)
        emailEditText = emailTextInputLayout.editText as TextInputEditText

        view.findViewById<MaterialButton>(R.id.button_ban_user).setOnClickListener {
            banUser(true)
        }
        view.findViewById<MaterialButton>(R.id.button_unban_user).setOnClickListener {
            banUser(false)
        }
    }

    private fun banUser(setBanned: Boolean) {
        val email = emailEditText.text.toString()

        if (validator.validate(email, FieldType.EMAIL, emailTextInputLayout, getString(R.string.email_error))) {
            viewModel.observeUser(this) {
                it.map(object : HandleUserInfo {
                    override fun handle(
                        id: String,
                        name: String,
                        email: String,
                        password: String,
                        photoPathToFile: String,
                        role: String
                    ) {
                        viewModel.banUser(id, setBanned)
                        showSnackBar(R.string.saved)
                    }
                })
                it.map(requireView())
            }
            viewModel.fetchUserByEmail(email)
        }
    }
}