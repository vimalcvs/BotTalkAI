package com.vimalcvs.bottalkai.domain.use_case.profile

import com.vimalcvs.bottalkai.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetDarkModeUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke() = preferenceRepository.getDarkMode()
}