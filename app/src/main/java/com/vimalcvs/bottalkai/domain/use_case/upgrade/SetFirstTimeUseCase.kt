package com.vimalcvs.bottalkai.domain.use_case.upgrade

import com.vimalcvs.bottalkai.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetFirstTimeUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(isFirstTime: Boolean) = preferenceRepository.setFirstTime(isFirstTime)
}
