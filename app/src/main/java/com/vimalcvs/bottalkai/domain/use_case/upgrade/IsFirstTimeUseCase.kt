package com.vimalcvs.bottalkai.domain.use_case.upgrade

import com.vimalcvs.bottalkai.domain.repository.PreferenceRepository
import javax.inject.Inject

class IsFirstTimeUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke() = preferenceRepository.isFirstTime()
}