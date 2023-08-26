package com.vimalcvs.bottalkai.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vimalcvs.bottalkai.data.model.ConversationModel
import com.vimalcvs.bottalkai.data.model.MessageModel

@Database(
    entities = [ConversationModel::class, MessageModel::class],
    version = 1,
    exportSchema = false
)
abstract class BotTalkAIDatabase : RoomDatabase() {
    abstract fun BotTalkAIDao(): BotTalkAIDao
}