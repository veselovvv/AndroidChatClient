package com.veselovvv.androidchatclient.sl

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.veselovvv.androidchatclient.core.ImageLoader
import com.veselovvv.androidchatclient.core.ResourceProvider
import com.veselovvv.androidchatclient.data.user.SessionManager
import com.veselovvv.androidchatclient.domain.fileuploading.FileProvider
import com.veselovvv.androidchatclient.ui.chats.ChatCache
import com.veselovvv.androidchatclient.ui.login.Validator
import com.veselovvv.androidchatclient.ui.main.MainViewModel
import com.veselovvv.androidchatclient.ui.main.NavigationCommunication
import com.veselovvv.androidchatclient.ui.main.Navigator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class CoreModule : BaseModule<MainViewModel> {
    private companion object {
        const val BASE_URL = "http://10.0.2.2:8081/"
    }

    lateinit var gson: Gson
    lateinit var validator: Validator
    lateinit var resourceProvider: ResourceProvider
    lateinit var sessionManager: SessionManager
    lateinit var imageLoader: ImageLoader
    lateinit var chatCache: ChatCache
    lateinit var navigator: Navigator
    lateinit var navigationCommunication: NavigationCommunication
    lateinit var fileProvider: FileProvider
    private lateinit var retrofit: Retrofit

    fun init(context: Context) {
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()

        gson = GsonBuilder().setLenient().create()
        validator = Validator.Base()
        resourceProvider = ResourceProvider.Base(context)
        sessionManager = SessionManager.Base(context)
        imageLoader = ImageLoader.Base()
        chatCache = ChatCache.Base(context)
        navigator = Navigator.Base(context)
        navigationCommunication = NavigationCommunication.Base()
        fileProvider = FileProvider.Base(context)
    }

    fun <T> makeService(clazz: Class<T>): T = retrofit.create(clazz)
    override fun getViewModel() = MainViewModel(navigator, navigationCommunication)
}