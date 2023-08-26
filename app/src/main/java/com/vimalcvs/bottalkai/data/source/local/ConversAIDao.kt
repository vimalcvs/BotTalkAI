package com.vimalcvs.bottalkai.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vimalcvs.bottalkai.common.Constants
import com.vimalcvs.bottalkai.data.model.ConversationModel
import com.vimalcvs.bottalkai.data.model.MessageModel

@Dao
interface BotTalkAIDao {

    @Query(Constants.Queries.GET_CONVERSATIONS)
    suspend fun getConversations(): MutableList<ConversationModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addConversation(conversationModel: ConversationModel)

    @Query(Constants.Queries.DELETE_CONVERSATION)
    suspend fun deleteConversation(id: String)

    @Query(Constants.Queries.DELETE_ALL_CONVERSATION)
    suspend fun deleteAllConversation()

    @Query(Constants.Queries.GET_MESSAGES)
    suspend fun getMessages(conversationId: String): List<MessageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(messageModel: MessageModel)

    @Query(Constants.Queries.DELETE_MESSAGES)
    suspend fun deleteMessages(conversationId: String)

}