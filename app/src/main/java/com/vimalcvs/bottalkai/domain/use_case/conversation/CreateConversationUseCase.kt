package com.vimalcvs.bottalkai.domain.use_case.conversation

import com.vimalcvs.bottalkai.data.model.ConversationModel
import com.vimalcvs.bottalkai.domain.repository.ConversationRepository
import javax.inject.Inject

class CreateConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversation: ConversationModel) =
        conversationRepository.addConversation(conversation)
}