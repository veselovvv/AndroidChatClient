package com.veselovvv.androidchatclient.ui.addmember

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

class AddMemberFragment : Fragment() {
    private lateinit var viewModel: AddMemberViewModel
    private lateinit var validator: Validator
    private lateinit var toolbar: Toolbar
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var addButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ChatApp).addMemberViewModel
        validator = (requireActivity().application as ChatApp).validator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_member, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar_add_member)
        toolbar.title = getString(R.string.add_member)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_add_member)
        emailEditText = emailTextInputLayout.editText as TextInputEditText
        addButton = view.findViewById(R.id.button_add_member)

        addButton.setOnClickListener {
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
                            Snackbar.make(requireView(), getString(R.string.added), Snackbar.LENGTH_SHORT).show()
                        }
                    })
                    it.map(requireView())
                }
                viewModel.fetchUserByEmail(email)
            }
        }
    }
}