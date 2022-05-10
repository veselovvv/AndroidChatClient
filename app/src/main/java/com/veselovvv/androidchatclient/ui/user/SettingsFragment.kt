package com.veselovvv.androidchatclient.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.androidchatclient.R
import com.veselovvv.androidchatclient.core.ChatApp
import de.hdodenhof.circleimageview.CircleImageView

class SettingsFragment : Fragment() {
    private lateinit var viewModel: SettingsViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var avatarCircleImageView: CircleImageView
    private lateinit var usernameTextInputLayout: TextInputLayout
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ChatApp).settingsViewModel
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

        viewModel.observeUser(this) {
            it.map(object : HandleUserInfo {
                override fun handle(
                    id: String, name: String, email: String, password: String, photoPathToFile: String
                ) {
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
    }
}