package com.vimalcvs.bottalkai.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimalcvs.bottalkai.common.Constants
import com.vimalcvs.bottalkai.common.Constants.DEFAULT_AI
import com.vimalcvs.bottalkai.data.model.ConversationModel
import com.vimalcvs.bottalkai.data.model.MessageModel
import com.vimalcvs.bottalkai.data.model.MessageTurbo
import com.vimalcvs.bottalkai.data.model.TextCompletionsParam
import com.vimalcvs.bottalkai.data.model.TurboRole
import com.vimalcvs.bottalkai.domain.use_case.conversation.CreateConversationUseCase
import com.vimalcvs.bottalkai.domain.use_case.message.CreateMessagesUseCase
import com.vimalcvs.bottalkai.domain.use_case.message.GetFreeMessageCountUseCase
import com.vimalcvs.bottalkai.domain.use_case.message.GetMessagesUseCase
import com.vimalcvs.bottalkai.domain.use_case.message.SetFreeMessageCountUseCase
import com.vimalcvs.bottalkai.domain.use_case.message.TextCompletionsWithStreamUseCase
import com.vimalcvs.bottalkai.domain.use_case.upgrade.IsProVersionUseCase
import com.vimalcvs.bottalkai.domain.use_case.upgrade.SetProVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val textCompletionsWithStreamUseCase: TextCompletionsWithStreamUseCase,
    private val createMessagesUseCase: CreateMessagesUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val createConversationUseCase: CreateConversationUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val isProVersionUseCase: IsProVersionUseCase,
    private val getFreeMessageCountUseCase: GetFreeMessageCountUseCase,
    private val setFreeMessageCountUseCase: SetFreeMessageCountUseCase,
    private val setProVersionUseCase: SetProVersionUseCase
) : ViewModel() {

    private var answerFromGPT = ""
    private var newMessageModel = MessageModel()

    private val cScope = CoroutineScope(Dispatchers.IO)
    var job: Job? = null

    private val _currentConversation: MutableStateFlow<String> =
        MutableStateFlow(Date().time.toString())

    private val _messages: MutableStateFlow<HashMap<String, MutableList<MessageModel>>> =
        MutableStateFlow(HashMap())

    private val _isGenerating: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val currentConversationState: StateFlow<String> = _currentConversation.asStateFlow()
    val messagesState: StateFlow<HashMap<String, MutableList<MessageModel>>> =
        _messages.asStateFlow()
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()


    private val _isProVersion = MutableStateFlow(false)
    val isProVersion get() = _isProVersion.asStateFlow()

    private val _freeMessageCount = MutableStateFlow(3)
    val freeMessageCount get() = _freeMessageCount.asStateFlow()

    val showAdsAndProVersion = mutableStateOf(false)

    init {
        _currentConversation.value = savedStateHandle.get<String>("id")
            ?: Date().time.toString()
        viewModelScope.launch { fetchMessages() }
    }

    fun setProVersion(isPro: Boolean) {
        setProVersionUseCase(isPro)
        _isProVersion.value = isPro
        showAdsAndProVersion.value = false
    }

    fun getProVersion() = viewModelScope.launch {
        _isProVersion.value = isProVersionUseCase()
        if (_isProVersion.value) {
            showAdsAndProVersion.value = false
        }
    }

    fun getFreeMessageCount() = viewModelScope.launch {
        _freeMessageCount.value = getFreeMessageCountUseCase()
        Log.e("freeMessageCount", _freeMessageCount.value.toString())
    }

    fun decreaseFreeMessageCount() {
        viewModelScope.launch {
            _freeMessageCount.value = _freeMessageCount.value - 1
            setFreeMessageCountUseCase(_freeMessageCount.value)
        }
    }

    fun increaseFreeMessageCount() {
        viewModelScope.launch {
            _freeMessageCount.value =
                _freeMessageCount.value + Constants.Preferences.INCREASE_MESSAGE_COUNT
            setFreeMessageCountUseCase(_freeMessageCount.value)
        }
    }

    fun sendMessage(message: String) = viewModelScope.launch {
        if (getMessagesByConversation(_currentConversation.value).isEmpty()) {
            createConversationRemote(message)
        }

        newMessageModel = MessageModel(
            question = message,
            answer = "...",
            conversationId = _currentConversation.value,
        )

        val currentListMessage: MutableList<MessageModel> =
            getMessagesByConversation(_currentConversation.value).toMutableList()

        // Insert message to list
        currentListMessage.add(0, newMessageModel)
        setMessages(currentListMessage)


        // Execute API OpenAI
        val flow: Flow<String> = textCompletionsWithStreamUseCase(
            scope = cScope,
            TextCompletionsParam(
                promptText = getPrompt(_currentConversation.value),
                messagesTurbo = getMessagesParamsTurbo(_currentConversation.value)
            )
        )


        answerFromGPT = ""

        job = cScope.launch {
            _isGenerating.value = true
            flow.collect {
                answerFromGPT += it
                updateLocalAnswer(answerFromGPT.trim())
            }
            // Save to Firestore
            createMessagesUseCase(newMessageModel.copy(answer = answerFromGPT))
            _isGenerating.value = false
        }


    }

    fun stopGenerate() = viewModelScope.launch {
        job?.cancel()
        _isGenerating.value = false
        createMessagesUseCase(newMessageModel.copy(answer = answerFromGPT))
    }

    private fun createConversationRemote(title: String) = viewModelScope.launch {
        val newConversation = ConversationModel(
            id = _currentConversation.value,
            title = title,
            createdAt = Calendar.getInstance().time.toString()
        )

        createConversationUseCase(newConversation)
    }

    private fun getMessagesByConversation(conversationId: String): MutableList<MessageModel> {
        if (_messages.value[conversationId] == null) return mutableListOf()

        val messagesMap: HashMap<String, MutableList<MessageModel>> =
            _messages.value.clone() as HashMap<String, MutableList<MessageModel>>

        return messagesMap[conversationId]!!
    }

    private fun getPrompt(conversationId: String): String {
        if (_messages.value[conversationId] == null) return ""

        val messagesMap: HashMap<String, MutableList<MessageModel>> =
            _messages.value.clone() as HashMap<String, MutableList<MessageModel>>

        var response: String = ""

        for (message in messagesMap[conversationId]!!.reversed()) {
            response += """
            Human:${message.question.trim()}
            Bot:${if (message.answer == "...") "" else message.answer.trim()}"""
        }

        return response
    }

    private fun getMessagesParamsTurbo(conversationId: String): List<MessageTurbo> {
        if (_messages.value[conversationId] == null) return listOf()

        val messagesMap: HashMap<String, MutableList<MessageModel>> =
            _messages.value.clone() as HashMap<String, MutableList<MessageModel>>

        val role: String = checkNotNull(savedStateHandle["role"])


        val response: MutableList<MessageTurbo> = mutableListOf(
            MessageTurbo(
                role = TurboRole.system,
                content = "$DEFAULT_AI $role"
            )
        )

        for (message in messagesMap[conversationId]!!.reversed()) {
            response.add(MessageTurbo(content = message.question))

            if (message.answer != "...") {
                response.add(MessageTurbo(content = message.answer, role = TurboRole.user))
            }
        }

        return response.toList()
    }

    private suspend fun fetchMessages() {
        if (_currentConversation.value.isEmpty() || _messages.value[_currentConversation.value] != null) return

        val list: List<MessageModel> = getMessagesUseCase(_currentConversation.value)

        setMessages(list.toMutableList())

    }

    private fun updateLocalAnswer(answer: String) {
        val currentListMessage: MutableList<MessageModel> =
            getMessagesByConversation(_currentConversation.value).toMutableList()

        currentListMessage[0] = currentListMessage[0].copy(answer = answer)

        setMessages(currentListMessage)
    }

    private fun setMessages(messages: MutableList<MessageModel>) {
        val messagesMap: HashMap<String, MutableList<MessageModel>> =
            _messages.value.clone() as HashMap<String, MutableList<MessageModel>>

        messagesMap[_currentConversation.value] = messages

        _messages.value = messagesMap
    }
}