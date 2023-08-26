package com.vimalcvs.bottalkai.domain.use_case.message

import com.vimalcvs.bottalkai.data.model.TextCompletionsParam
import com.vimalcvs.bottalkai.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class TextCompletionsWithStreamUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(scope: CoroutineScope, params: TextCompletionsParam) =
        chatRepository.textCompletionsWithStream(scope, params)
}