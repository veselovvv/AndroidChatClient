package com.veselovvv.androidchatclient.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ImageLoader
import com.veselovvv.androidchatclient.ui.core.BaseFileUploadFragment
import com.veselovvv.androidchatclient.ui.fileuploading.SetPathToFile
import com.veselovvv.androidchatclient.ui.login.FieldType
import com.veselovvv.androidchatclient.ui.login.LoginActivity
import com.veselovvv.androidchatclient.ui.login.Validator
import de.hdodenhof.circleimageview.CircleImageView

class SettingsFragment : BaseFileUploadFragment(R.layout.fragment_settings) {
    private lateinit var viewModel: SettingsViewModel
    private lateinit var imageLoader: ImageLoader
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = app.getSettingsViewModel()
        validator = app.getValidator()
        imageLoader = app.getImageLoader()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar_settings)
        setupToolbar(toolbar, R.string.settings)
        toolbar.inflateMenu(R.menu.settings_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_log_out -> {
                    viewModel.cleanToken()
                    requireActivity().startActivity(Intent(requireActivity(), LoginActivity::class.java))
                    requireActivity().finish()
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
                    viewModel.setPathToFile(photoPathToFile)
                    if (photoPathToFile != "") imageLoader.load(
                        requireView(), photoPathToFile, viewModel.getUserToken(), avatarCircleImageView
                    )
                    usernameTextInputLayout.editText?.setText(name)
                    emailTextInputLayout.editText?.setText(email)
                    passwordTextInputLayout.editText?.setText(password)
                }
            })
            it.map(requireView())
        }
        viewModel.fetchUser(viewModel.getUserId())

        selectAvatarMaterialTextView.setOnClickListener { getPermission() }
        view.findViewById<MaterialButton>(R.id.save_button_settings).setOnClickListener {
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
                viewModel.editUser(viewModel.getUserId(), username, email, password, viewModel.getPathToFile())
                showSnackBar(R.string.saved)
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