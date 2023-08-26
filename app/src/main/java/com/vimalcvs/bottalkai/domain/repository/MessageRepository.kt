package com.vimalcvs.bottalkai.domain.repository

import com.vimalcvs.bottalkai.data.model.MessageModel

interface MessageRepository {
    suspend fun getMessages(conversationId: String): List<MessageModel>
    suspend fun addMessage(message: MessageModel)
    suspend fun deleteMessages(conversationId: String)
}