package com.veselovvv.androidchatclient.core

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.veselovvv.androidchatclient.data.chats.ChatsCloudDataSource
import com.veselovvv.androidchatclient.data.chats.ChatsCloudMapper
import com.veselovvv.androidchatclient.data.chats.ChatsRepository
import com.veselovvv.androidchatclient.data.chats.ToChatMapper
import com.veselovvv.androidchatclient.data.chats.net.ChatService
import com.veselovvv.androidchatclient.data.chatwithmessages.*
import com.veselovvv.androidchatclient.data.fileuploading.UploadFileCloudDataSource
import com.veselovvv.androidchatclient.data.fileuploading.UploadFileRepository
import com.veselovvv.androidchatclient.data.fileuploading.UploadFileService
import com.veselovvv.androidchatclient.data.login.LoginCloudDataSource
import com.veselovvv.androidchatclient.data.login.LoginRepository
import com.veselovvv.androidchatclient.data.login.ToLoginMapper
import com.veselovvv.androidchatclient.data.message.MessageCloudDataSource
import com.veselovvv.androidchatclient.data.message.MessageRepository
import com.veselovvv.androidchatclient.data.message.ToMessageDTOMapper
import com.veselovvv.androidchatclient.data.user.*
import com.veselovvv.androidchatclient.data.users.net.UserService
import com.veselovvv.androidchatclient.domain.chats.BaseChatDataToDomainMapper
import com.veselovvv.androidchatclient.domain.chats.BaseChatsDataToDomainMapper
import com.veselovvv.androidchatclient.domain.chats.ChatsInteractor
import com.veselovvv.androidchatclient.domain.chatwithmessages.BaseChatWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.BaseChatsWithMessagesDataToDomainMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesDomainToUiMapper
import com.veselovvv.androidchatclient.domain.chatwithmessages.ChatsWithMessagesInteractor
import com.veselovvv.androidchatclient.domain.fileuploading.BaseUploadFileDataToDomainMapper
import com.veselovvv.androidchatclient.domain.fileuploading.FileProvider
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileDomainToUiMapper
import com.veselovvv.androidchatclient.domain.fileuploading.UploadFileInteractor
import com.veselovvv.androidchatclient.domain.login.BaseLoginDataToDomainMapper
import com.veselovvv.androidchatclient.domain.login.LoginInteractor
import com.veselovvv.androidchatclient.domain.message.BaseMessageDataToDomainMapper
import com.veselovvv.androidchatclient.domain.message.MessageInteractor
import com.veselovvv.androidchatclient.domain.user.BaseUserDataToDomainMapper
import com.veselovvv.androidchatclient.domain.user.BaseUsersDataToDomainMapper
import com.veselovvv.androidchatclient.domain.user.UserInteractor
import com.veselovvv.androidchatclient.domain.user.UsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.chats.*
import com.veselovvv.androidchatclient.ui.chatwithmessages.*
import com.veselovvv.androidchatclient.ui.fileuploading.BaseUploadFileDomainToUiMapper
import com.veselovvv.androidchatclient.ui.fileuploading.UploadFileCommunication
import com.veselovvv.androidchatclient.ui.login.*
import com.veselovvv.androidchatclient.ui.main.MainViewModel
import com.veselovvv.androidchatclient.ui.main.NavigationCommunication
import com.veselovvv.androidchatclient.ui.main.Navigator
import com.veselovvv.androidchatclient.ui.message.BaseMessageDomainToUiMapper
import com.veselovvv.androidchatclient.ui.message.MessageCommunication
import com.veselovvv.androidchatclient.ui.newchat.NewChatViewModel
import com.veselovvv.androidchatclient.ui.user.BaseUserDomainToUiMapper
import com.veselovvv.androidchatclient.ui.user.BaseUsersDomainToUiMapper
import com.veselovvv.androidchatclient.ui.user.SettingsViewModel
import com.veselovvv.androidchatclient.ui.user.UserCommunication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ChatApp : Application() { //TODO to modules
    private companion object {
        const val BASE_URL = "http://10.0.2.2:8081/"
    }

    lateinit var validator: Validator
    lateinit var navigationCommunication: NavigationCommunication
    lateinit var mainViewModel: MainViewModel
    lateinit var loginViewModel: LoginViewModel
    lateinit var registerViewModel: RegisterViewModel
    lateinit var chatsViewModel: ChatsViewModel
    lateinit var chatsWithMessagesViewModel: ChatsWithMessagesViewModel
    lateinit var gson: Gson
    lateinit var loginCloudDataSource: LoginCloudDataSource
    private lateinit var retrofit: Retrofit
    lateinit var userService: UserService
    lateinit var chatService: ChatService
    lateinit var resourceProvider: ResourceProvider
    lateinit var sessionManager: SessionManager
    lateinit var chatWithMessagesCloudDataSource: ChatWithMessagesCloudDataSource
    lateinit var userCloudDataSource: UserCloudDataSource
    lateinit var uploadFileInteractor: UploadFileInteractor
    lateinit var uploadFileDomainToUiMapper: UploadFileDomainToUiMapper
    lateinit var uploadFileCommunication: UploadFileCommunication
    lateinit var userCommunication: UserCommunication
    lateinit var userInteractor: UserInteractor
    lateinit var usersDomainToUiMapper: UsersDomainToUiMapper
    lateinit var settingsViewModel: SettingsViewModel
    lateinit var newChatViewModel: NewChatViewModel
    lateinit var chatsWithMessagesInteractor: ChatsWithMessagesInteractor
    lateinit var chatsWithMessagesCommunication: ChatsWithMessagesCommunication
    lateinit var chatsWithMessagesDomainToUiMapper: ChatsWithMessagesDomainToUiMapper

    override fun onCreate() {
        super.onCreate()

        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()

        gson = GsonBuilder()
            .setLenient()
            .create()

        resourceProvider = ResourceProvider.Base(this)

        validator = Validator.Base()
        sessionManager = SessionManager.Base(this)

        userService = retrofit.create(UserService::class.java)
        chatService = retrofit.create(ChatService::class.java)
        loginCloudDataSource = LoginCloudDataSource.Base(userService, gson)
        userCloudDataSource = UserCloudDataSource.Base(userService, gson)
        navigationCommunication = NavigationCommunication.Base()
        chatWithMessagesCloudDataSource = ChatWithMessagesCloudDataSource.Base(chatService, gson)
        uploadFileDomainToUiMapper = BaseUploadFileDomainToUiMapper(resourceProvider)
        uploadFileCommunication = UploadFileCommunication.Base()
        userCommunication = UserCommunication.Base()
        chatsWithMessagesCommunication = ChatsWithMessagesCommunication.Base()
        usersDomainToUiMapper = BaseUsersDomainToUiMapper(resourceProvider, BaseUserDomainToUiMapper())
        chatsWithMessagesDomainToUiMapper = BaseChatsWithMessagesDomainToUiMapper(
            resourceProvider,
            BaseChatWithMessagesDomainToUiMapper()
        )

        uploadFileInteractor = UploadFileInteractor.Base(
            UploadFileRepository.Base(
                UploadFileCloudDataSource.Base(retrofit.create(UploadFileService::class.java)),
                sessionManager
            ), BaseUploadFileDataToDomainMapper(),
            FileProvider.Base(this)
        )

        userInteractor = UserInteractor.Base(
            UserRepository.Base(
                userCloudDataSource,
                UserCloudMapper.Base(ToUserMapper.Base()),
                ToUserDTOMapper.Base(),
                ToEditUserDTOMapper.Base(),
                sessionManager
            ),
            BaseUsersDataToDomainMapper(BaseUserDataToDomainMapper())
        )

        chatsWithMessagesInteractor = ChatsWithMessagesInteractor.Base(
            ChatsWithMessagesRepository.Base(
                chatWithMessagesCloudDataSource,
                ChatWithMessagesCloudMapper.Base(ToChatWithMessagesMapper.Base()),
                ToEditChatSettingsDtoMapper.Base(),
                ToCreateChatDtoMapper.Base(),
                sessionManager
            ),
            BaseChatsWithMessagesDataToDomainMapper(BaseChatWithMessagesDataToDomainMapper())
        )

        mainViewModel = MainViewModel(Navigator.Base(this), navigationCommunication)
        loginViewModel = LoginViewModel(
            LoginInteractor.Base(
                LoginRepository.Base(
                    loginCloudDataSource,
                    ToLoginMapper.Base(),
                    sessionManager
                ),
                BaseLoginDataToDomainMapper()
            ),
            BaseLoginDomainToUiMapper(resourceProvider),
            LoginCommunication.Base()
        )
        registerViewModel = RegisterViewModel(
            userInteractor,
            uploadFileInteractor,
            usersDomainToUiMapper,
            uploadFileDomainToUiMapper,
            userCommunication,
            uploadFileCommunication
        )
        chatsViewModel = ChatsViewModel(
            ChatsInteractor.Base(
                ChatsRepository.Base(
                    ChatsCloudDataSource.Base(userService, gson),
                    chatWithMessagesCloudDataSource,
                    userCloudDataSource,
                    ChatsCloudMapper.Base(ToChatMapper.Base()),
                    sessionManager
                ),
                BaseChatsDataToDomainMapper(BaseChatDataToDomainMapper())
            ),
            userInteractor,
            BaseChatsDomainToUiMapper(resourceProvider, BaseChatDomainToUiMapper()),
            usersDomainToUiMapper,
            ChatsCommunication.Base(),
            userCommunication,
            ChatCache.Base(this),
            Navigator.Base(this),
            navigationCommunication
        )

        chatsWithMessagesViewModel = ChatsWithMessagesViewModel(
            chatsWithMessagesInteractor,
            MessageInteractor.Base(
                MessageRepository.Base(
                    MessageCloudDataSource.Base(chatService),
                    ToMessageDTOMapper.Base(),
                    sessionManager
                ), BaseMessageDataToDomainMapper()
            ),
            uploadFileInteractor,
            chatsWithMessagesCommunication,
            MessagesCommunication.Base(),
            MessageCommunication.Base(),
            uploadFileCommunication,
            chatsWithMessagesDomainToUiMapper,
            BaseMessageDomainToUiMapper(resourceProvider),
            uploadFileDomainToUiMapper,
            ChatCache.Base(this)
        )

        settingsViewModel = SettingsViewModel(
            userInteractor,
            uploadFileInteractor,
            usersDomainToUiMapper,
            uploadFileDomainToUiMapper,
            userCommunication,
            uploadFileCommunication
        )

        newChatViewModel = NewChatViewModel(
            userInteractor,
            usersDomainToUiMapper,
            userCommunication,
            chatsWithMessagesInteractor,
            chatsWithMessagesCommunication,
            chatsWithMessagesDomainToUiMapper
        )
    }
}