package com.veselovvv.androidchatclient.ui.chatwithmessages

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

class AddMemberFragment : BaseFragment(R.layout.fragment_add_member) {
    private lateinit var viewModel: AddMemberViewModel
    private lateinit var validator: Validator
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = app.addMemberViewModel
        validator = app.validator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(view.findViewById(R.id.toolbar_add_member), R.string.add_member)

        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_add_member)
        emailEditText = emailTextInputLayout.editText as TextInputEditText

        view.findViewById<MaterialButton>(R.id.button_add_member).setOnClickListener {
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
                            viewModel.addMember(viewModel.getChatId(), id, "false") //TODO?
                            showSnackBar(R.string.added)
                        }
                    })
                    it.map(requireView())
                }
                viewModel.fetchUserByEmail(email)
            }
        }
    }
}