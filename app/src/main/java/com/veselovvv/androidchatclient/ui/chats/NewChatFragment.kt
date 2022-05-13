package com.veselovvv.androidchatclient.ui.chats

import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.ui.core.BaseFragment
import com.veselovvv.androidchatclient.ui.login.FieldType
import com.veselovvv.androidchatclient.ui.login.Validator
import com.veselovvv.androidchatclient.ui.user.HandleUserInfo

class NewChatFragment : BaseFragment(R.layout.fragment_new_chat) {
    private lateinit var viewModel: NewChatViewModel
    private lateinit var validator: Validator
    private lateinit var titleTextInputLayout: TextInputLayout
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var titleEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = app.newChatViewModel
        validator = app.validator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(view.findViewById(R.id.toolbar_new_chat), R.string.new_chat)

        titleTextInputLayout = view.findViewById(R.id.title_text_input_layout_new_chat)
        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_new_chat)
        titleEditText = titleTextInputLayout.editText as TextInputEditText
        emailEditText = emailTextInputLayout.editText as TextInputEditText

        view.findViewById<MaterialButton>(R.id.button_new_chat).setOnClickListener {
            val title = titleEditText.text.toString()
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
                            viewModel.createChat(title, viewModel.getUserId(), listOf(viewModel.getUserId(), id))
                            showSnackBar(R.string.created)
                        }
                    })
                    it.map(requireView())
                }
                viewModel.fetchUserByEmail(email)
            }
        }
    }
}