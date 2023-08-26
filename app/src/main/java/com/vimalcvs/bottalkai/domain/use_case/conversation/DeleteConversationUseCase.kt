package com.vimalcvs.bottalkai.domain.use_case.conversation

import com.vimalcvs.bottalkai.domain.repository.ConversationRepository
import javax.inject.Inject

class DeleteConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String) =
        conversationRepository.deleteConversation(conversationId)
}