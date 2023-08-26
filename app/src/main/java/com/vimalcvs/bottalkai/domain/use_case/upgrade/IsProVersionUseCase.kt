package com.vimalcvs.bottalkai.domain.use_case.upgrade

import com.vimalcvs.bottalkai.domain.repository.PreferenceRepository
import javax.inject.Inject

class IsProVersionUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke() = preferenceRepository.isProVersion()
}