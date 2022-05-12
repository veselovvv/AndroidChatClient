package com.veselovvv.androidchatclient.ui.newchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.ui.login.FieldType
import com.veselovvv.androidchatclient.ui.login.Validator
import com.veselovvv.androidchatclient.ui.user.HandleUserInfo

class NewChatFragment : Fragment() {
    private lateinit var viewModel: NewChatViewModel
    private lateinit var validator: Validator
    private lateinit var toolbar: Toolbar
    private lateinit var titleTextInputLayout: TextInputLayout
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var titleEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var startButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ChatApp).newChatViewModel
        validator = (requireActivity().application as ChatApp).validator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_new_chat, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar_new_chat)
        toolbar.title = getString(R.string.new_chat)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        startButton = view.findViewById(R.id.button_new_chat)
        titleTextInputLayout = view.findViewById(R.id.title_text_input_layout_new_chat)
        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_new_chat)
        titleEditText = titleTextInputLayout.editText as TextInputEditText
        emailEditText = emailTextInputLayout.editText as TextInputEditText

        startButton.setOnClickListener {
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
                            Snackbar.make(requireView(), getString(R.string.created), Snackbar.LENGTH_SHORT).show()
                        }
                    })
                    it.map(requireView())
                }
                viewModel.fetchUserByEmail(email)
            }
        }
    }
}