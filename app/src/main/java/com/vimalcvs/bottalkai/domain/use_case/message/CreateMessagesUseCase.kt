package com.vimalcvs.bottalkai.domain.use_case.message

import com.vimalcvs.bottalkai.data.model.MessageModel
import com.vimalcvs.bottalkai.domain.repository.MessageRepository
import javax.inject.Inject

class CreateMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(message: MessageModel) =
        messageRepository.addMessage(message)
}