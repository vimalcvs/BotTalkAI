package com.vimalcvs.bottalkai.domain.use_case.message

import com.vimalcvs.bottalkai.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetFreeMessageCountUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(count: Int) = preferenceRepository.setFreeMessageCount(count)
}