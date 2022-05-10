package com.veselovvv.androidchatclient.ui.user

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import com.veselovvv.androidchatclient.ui.core.BaseFileUploadFragment
import com.veselovvv.androidchatclient.ui.fileuploading.SetPathToFile
import com.veselovvv.androidchatclient.ui.login.FieldType
import com.veselovvv.androidchatclient.ui.login.Validator
import de.hdodenhof.circleimageview.CircleImageView

class SettingsFragment : BaseFileUploadFragment() {
    private lateinit var viewModel: SettingsViewModel
    private lateinit var validator: Validator
    private lateinit var toolbar: Toolbar
    private lateinit var avatarCircleImageView: CircleImageView
    private lateinit var usernameTextInputLayout: TextInputLayout
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var selectAvatarMaterialTextView: MaterialTextView
    private lateinit var saveSettingsButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ChatApp).settingsViewModel
        validator = (requireActivity().application as ChatApp).validator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar_settings)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        toolbar.inflateMenu(R.menu.settings_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_log_out -> {
                    //TODO
                    true
                }
                else -> false
            }
        }

        avatarCircleImageView = view.findViewById(R.id.avatar_imageview_settings)
        usernameTextInputLayout = view.findViewById(R.id.username_text_input_layout_settings)
        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout_settings)
        passwordTextInputLayout = view.findViewById(R.id.password_text_input_layout_settings)
        usernameEditText = usernameTextInputLayout.editText as TextInputEditText
        emailEditText = emailTextInputLayout.editText as TextInputEditText
        passwordEditText = passwordTextInputLayout.editText as TextInputEditText
        selectAvatarMaterialTextView = view.findViewById(R.id.select_avatar_textview_settings)
        saveSettingsButton = view.findViewById(R.id.save_button_settings)

        viewModel.observeUser(this) {
            it.map(object : HandleUserInfo {
                override fun handle(
                    id: String, name: String, email: String, password: String, photoPathToFile: String
                ) {
                    viewModel.setPathToFile(photoPathToFile)
                    //TODO path? + dry
                    Glide.with(requireView())
                        .load(
                            GlideUrl("http://10.0.2.2:8081/getFile/?path=" +
                                    photoPathToFile.substringAfter("chat-server/"),
                                LazyHeaders.Builder().addHeader("Authorization", viewModel.getUserToken()
                                ).build())
                        ).into(avatarCircleImageView)
                    usernameTextInputLayout.editText?.setText(name)
                    emailTextInputLayout.editText?.setText(email)
                    passwordTextInputLayout.editText?.setText(password)
                }
            })
            it.map(requireView())
        }
        viewModel.fetchUser(viewModel.getUserId())

        selectAvatarMaterialTextView.setOnClickListener { getPermission() }
        saveSettingsButton.setOnClickListener {
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
                viewModel.editUser(
                    viewModel.getUserId(), username, email, password, viewModel.getPathToFile()
                )
                Snackbar.make(requireView(), getString(R.string.saved), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    //TODO dry
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