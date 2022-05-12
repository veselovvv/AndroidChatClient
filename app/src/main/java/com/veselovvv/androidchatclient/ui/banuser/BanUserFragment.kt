package com.veselovvv.androidchatclient.ui.banuser

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
import com.veselovvv.androidchatclient.ui.addmember.AddMemberViewModel
import com.veselovvv.androidchatclient.ui.login.FieldType
import com.veselovvv.androidchatclient.ui.login.Validator
import com.veselovvv.androidchatclient.ui.user.HandleUserInfo

class BanUserFragment : Fragment() {
    private lateinit var viewModel: BanUserViewModel
    private lateinit var validator: Validator
    private lateinit var toolbar: Toolbar
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var banButton: MaterialButton
    private lateinit var unbanButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ChatApp).banUserViewModel
        validator = (requireActivity().application as ChatApp).validator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_ban_user, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar_ban_user)
        toolbar.title = getString(R.string.ban_user)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_ban_user)
        emailEditText = emailTextInputLayout.editText as TextInputEditText
        banButton = view.findViewById(R.id.button_ban_user)
        unbanButton = view.findViewById(R.id.button_unban_user)

        banButton.setOnClickListener { banUser(true) }
        unbanButton.setOnClickListener { banUser(false) }
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
                        //viewModel.banUser(setBanned) //TODO
                        Snackbar.make(requireView(), getString(R.string.saved), Snackbar.LENGTH_SHORT).show()
                    }
                })
                it.map(requireView())
            }
            viewModel.fetchUserByEmail(email)
        }
    }
}