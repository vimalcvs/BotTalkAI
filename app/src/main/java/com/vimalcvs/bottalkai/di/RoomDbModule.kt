package com.vimalcvs.bottalkai.di

import android.content.Context
import androidx.room.Room
import com.vimalcvs.bottalkai.data.source.local.BotTalkAIDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDbModule {


    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext appContext: Context): BotTalkAIDatabase =
        Room.databaseBuilder(
            appContext,
            BotTalkAIDatabase::class.java,
            "BotTalkAIdb.db"
        ).build()

    @Provides
    @Singleton
    fun provideBotTalkAIDao(BotTalkAIDatabase: BotTalkAIDatabase) = BotTalkAIDatabase.BotTalkAIDao()
}