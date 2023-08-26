package com.vimalcvs.bottalkai.domain.use_case.conversation

import com.vimalcvs.bottalkai.domain.repository.ConversationRepository
import javax.inject.Inject

class DeleteAllConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke() =
        conversationRepository.deleteAllConversation()
}