package com.vimalcvs.bottalkai.domain.use_case.app

import com.vimalcvs.bottalkai.domain.repository.FirebaseRepository
import javax.inject.Inject

class IsThereUpdateUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator suspend fun invoke(): Boolean {
        return firebaseRepository.isThereUpdate()
    }
}