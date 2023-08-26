package com.vimalcvs.bottalkai.data.repository

import com.vimalcvs.bottalkai.data.model.ConversationModel
import com.vimalcvs.bottalkai.data.source.local.BotTalkAIDao
import com.vimalcvs.bottalkai.domain.repository.ConversationRepository
import javax.inject.Inject


class ConversationRepositoryImpl @Inject constructor(
    private val BotTalkAIDao: BotTalkAIDao

) : ConversationRepository {
    override suspend fun getConversations(): MutableList<ConversationModel> =
        BotTalkAIDao.getConversations()

    override suspend fun addConversation(conversation: ConversationModel) =
        BotTalkAIDao.addConversation(conversation)

    override suspend fun deleteConversation(conversationId: String) {
        BotTalkAIDao.deleteConversation(conversationId)
        BotTalkAIDao.deleteMessages(conversationId)
    }


    override suspend fun deleteAllConversation() = BotTalkAIDao.deleteAllConversation()

}