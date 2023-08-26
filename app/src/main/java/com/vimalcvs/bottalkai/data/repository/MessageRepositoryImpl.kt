package com.vimalcvs.bottalkai.data.repository

import com.vimalcvs.bottalkai.data.model.MessageModel
import com.vimalcvs.bottalkai.data.source.local.BotTalkAIDao
import com.vimalcvs.bottalkai.domain.repository.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val BotTalkAIDao: BotTalkAIDao,
) : MessageRepository {
    override suspend fun getMessages(conversationId: String): List<MessageModel> =
        BotTalkAIDao.getMessages(conversationId)

    override suspend fun addMessage(message: MessageModel) =
        BotTalkAIDao.addMessage(message)

    override suspend fun deleteMessages(conversationId: String) =
        BotTalkAIDao.deleteMessages(conversationId)
}