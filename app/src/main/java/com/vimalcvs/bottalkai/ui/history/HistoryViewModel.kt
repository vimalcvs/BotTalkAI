package com.vimalcvs.bottalkai.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimalcvs.bottalkai.data.model.ConversationModel
import com.vimalcvs.bottalkai.domain.use_case.conversation.DeleteAllConversationUseCase
import com.vimalcvs.bottalkai.domain.use_case.conversation.DeleteConversationUseCase
import com.vimalcvs.bottalkai.domain.use_case.conversation.GetConversationsUseCase
import com.vimalcvs.bottalkai.domain.use_case.profile.GetDarkModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getConversationsUseCase: GetConversationsUseCase,
    private val deleteConversationUseCase: DeleteConversationUseCase,
    private val deleteAllConversationUseCase: DeleteAllConversationUseCase,
    private val getDarkModeUseCase: GetDarkModeUseCase
) : ViewModel() {

    private val _conversations: MutableStateFlow<MutableList<ConversationModel>> = MutableStateFlow(
        mutableListOf()
    )
    private val _isFetching: MutableStateFlow<Boolean> = MutableStateFlow(
        true
    )

    val conversationsState: StateFlow<MutableList<ConversationModel>> = _conversations.asStateFlow()
    val isFetching: StateFlow<Boolean> = _isFetching.asStateFlow()


    private val _darkMode = MutableStateFlow(true)
    val darkMode
        get() = _darkMode.asStateFlow()

    fun getDarkMode() = viewModelScope.launch {
        _darkMode.value = getDarkModeUseCase()
    }

    fun getConversations() = viewModelScope.launch {
        _isFetching.value = true
        _conversations.value = getConversationsUseCase()
        _isFetching.value = false
    }

    fun deleteConversation(conversationId: String) = viewModelScope.launch {
        deleteConversationUseCase(conversationId)
        _conversations.value =
            _conversations.value.filter { it.id != conversationId }.toMutableList()

    }

    fun deleteAllConversation() = viewModelScope.launch {
        deleteAllConversationUseCase()
        _conversations.value = mutableListOf()

    }

}