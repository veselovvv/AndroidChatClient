package com.veselovvv.androidchatclient.core

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

interface ImageLoader {
    fun load(view: View, pathToFile: String, token: String, imageView: ImageView)

    class Base : ImageLoader {
        override fun load(view: View, pathToFile: String, token: String, imageView: ImageView) {
            Glide.with(view)
                .load(
                    GlideUrl(URL + pathToFile.substringAfter(DELIMITER),
                        LazyHeaders.Builder().addHeader("Authorization", token).build())
                ).into(imageView)
        }

        private companion object {
            const val URL = "http://10.0.2.2:8081/getFile/?path="
            const val DELIMITER = "chat-server/"
        }
    }
}