package com.vimalcvs.bottalkai.domain.repository

import com.vimalcvs.bottalkai.data.model.TextCompletionsParam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun textCompletionsWithStream(scope: CoroutineScope, params: TextCompletionsParam): Flow<String>
}