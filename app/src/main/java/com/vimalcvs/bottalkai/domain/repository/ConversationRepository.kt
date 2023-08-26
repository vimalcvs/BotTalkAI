package com.vimalcvs.bottalkai.domain.repository

import com.vimalcvs.bottalkai.data.model.ConversationModel

interface ConversationRepository {
    suspend fun getConversations(): MutableList<ConversationModel>
    suspend fun addConversation(conversation: ConversationModel)
    suspend fun deleteConversation(conversationId: String)
    suspend fun deleteAllConversation()
}